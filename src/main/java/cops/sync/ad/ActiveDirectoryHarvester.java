package cops.sync.ad;

import cops.sync.ad.dao.ActiveDirectoryDAO;
import cops.sync.ad.entity.adinfo.ActiveDirectoryGroup;
import cops.sync.ad.entity.adinfo.ActiveDirectoryUser;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toMap;

@QuarkusMain
@ActivateRequestContext
public class ActiveDirectoryHarvester implements QuarkusApplication
{
	static final Logger log = Logger.getLogger(ActiveDirectoryHarvester.class);

	static final List<String> OU_USER_BASE =
			List.of("CN=Users,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au", "OU=MQ-Admin,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=MQ-BusUnit-Res,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=MQ-Cohorts,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=MQ-Global,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=MQ-Kyndryl,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=MQ-Resources,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=Office365,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au", "OU=test,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=zz-HPC-Test2,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=Active,OU=MQ-Users,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=Inactive,OU=MQ-Users,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			        "OU=Suspended,OU=MQ-Users,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au");

	@Inject
	@PersistenceUnit("adinfo")
	EntityManager em;

	@Inject
	ActiveDirectoryDAO ad;

	@Override
	public int run(String... args)
	{
		//		updateGroups();
		//		updateUsers();
		updateGroupMembership();

		return 0;
	}

	public void updateGroups()
	{
		try
		{
			log.info("processing ad groups");

			List<ActiveDirectoryGroup> groups = getGroupsFromAd("DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au");
			log.infov("fetched groups from ad: {0}", groups.size());

			save(groups, 1000, "groups");
			log.infov("saved groups to db: {0}", groups.size());
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void updateUsers()
	{
		try
		{
			log.info("fetching users from ad");
			List<ActiveDirectoryUser> users = OU_USER_BASE.stream()
			                                              .peek(g -> log.infov("fetching users from: {0}", g))
			                                              .map(this::getUsersFromAd)
			                                              .peek(g -> log.infov("found users: {0}", g.size()))
			                                              .flatMap(Collection::stream)
			                                              .toList();

			save(users, 2000, "ad users");
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void updateGroupMembership()
	{
		try
		{
			log.info("fetching groups from ad");
			List<ActiveDirectoryGroup> groups = getGroupsFromAd("DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au");
			log.infov("fetched groups from ad: {0}", groups.size());

			log.info("fetching users from db");
			Map<String, ActiveDirectoryUser> users =
					em.createQuery("select u from ActiveDirectoryUser u", ActiveDirectoryUser.class)
					  .getResultStream()
					  .collect(toMap(ActiveDirectoryUser::getDn, u -> u, (x, y) -> x));
			log.infov("fetched users from db: {0}", users.size());

			log.info("fetching ad group/member mappings");
			groups.stream()
			      .peek(g -> log.infov("processing group: {0}", g.getCn()))
			      .forEach(group -> ad.getGroupMembers(group.getDn())
			                          .stream()
			                          .map(users::get)
			                          .filter(Objects::nonNull)
			                          .forEach(u -> group.getMembers()
			                                             .add(u)));

//			log.info("saving group/member mappings to db");
//			save(groups, 1, "group");
			log.info("saving group/member mappings to file");
			saveToFile(groups, "C:/Temp/group-member.csv");
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
			e.printStackTrace();
		}
	}

	private List<ActiveDirectoryUser> getUsersFromAd(String dn)
	{
		log.infov("getting users from ad: {0}", dn);

		return ad.getUsers(dn, Set.of("distinguishedName", "cn", "whenChanged", "whenCreated", "sAMAccountName",
		                              "displayName", "lastLogonTimestamp", "accountExpires", "pwdLastSet"))
		         .values()
		         .stream()
		         .map(this::makeActiveDirectoryUser)
		         .toList();
	}

	private List<ActiveDirectoryGroup> getGroupsFromAd(String dn)
	{
		return ad.getGroups(dn, Set.of("distinguishedName", "cn", "whenChanged", "whenCreated"))
		         .values()
		         .parallelStream()
		         .map(this::makeActiveDirectoryGroup)
		         .toList();
	}

	private ActiveDirectoryGroup makeActiveDirectoryGroup(Map<String, String> data)
	{
		final DateFormat addf = new SimpleDateFormat("yyyyMMddHHmmss'.0Z'"); // 20230607015447.0Z

		String[] dnparts = data.get("distinguishedName")
		                       .split(",");
		String normalised_dn = String.join(",", Arrays.asList(dnparts)
		                                              .reversed());

		ActiveDirectoryGroup group = new ActiveDirectoryGroup();
		group.setName(data.get("cn"));
		group.setDn(data.get("distinguishedName"));
		group.setCn(data.get("cn"));
		group.setNormalisedDn(normalised_dn);

		String whenCreated = data.get("whenCreated");
		String whenChanged = data.get("whenChanged");
		try
		{
			if (whenCreated != null && !whenCreated.isEmpty())
				group.setCreated(addf.parse(data.get("whenCreated")));

			if (whenChanged != null && !whenChanged.isEmpty())
				group.setUpdated(addf.parse(data.get("whenChanged")));
		}
		catch (ParseException pe)
		{
			log.warnv("unable to parse active directory date: {0}/{1}", data.get("whenCreated"),
			          data.get("whenChanged"));
		}

		return group;
	}

	private ActiveDirectoryUser makeActiveDirectoryUser(Map<String, String> data)
	{
		final DateFormat addf = new SimpleDateFormat("yyyyMMddHHmmss'.0Z'"); // 20230607015447.0Z

		ActiveDirectoryUser user = new ActiveDirectoryUser();
		String[] dnparts = data.get("distinguishedName")
		                       .split(",");
		String normalised_dn = String.join(",", Arrays.asList(dnparts)
		                                              .reversed());

		user.setName(data.get("cn"));
		user.setDn(data.get("distinguishedName"));
		user.setNormalisedDn(normalised_dn);
		user.setCn(data.get("cn"));
		user.setSamAccountName(data.get("sAMAccountName"));
		user.setDisplayName(data.get("displayName"));
		user.setLastLogon(ActiveDirectoryDAO.getDateFromTimestamp(data.get("lastLogonTimestamp")));
		user.setAccountExpires(ActiveDirectoryDAO.getDateFromTimestamp(data.get("accountExpires")));
		user.setPasswordLastSet(ActiveDirectoryDAO.getDateFromTimestamp(data.get("pwdLastSet")));

		String whenCreated = data.get("whenCreated");
		String whenChanged = data.get("whenChanged");
		try
		{
			if (whenCreated != null && !whenCreated.isEmpty())
				user.setCreated(addf.parse(data.get("whenCreated")));

			if (whenChanged != null && !whenChanged.isEmpty())
				user.setUpdated(addf.parse(data.get("whenChanged")));
		}
		catch (ParseException pe)
		{
			log.warnv("unable to parse active directory date: {0}/{1}", data.get("whenCreated"),
			          data.get("whenChanged"));
		}

		return user;
	}

	public <T> void save(Collection<T> objects, int batchSize, String name)
	{
		Set<T> batch = new HashSet<>();
		AtomicInteger count = new AtomicInteger(0);
		objects.forEach(y -> {
			try
			{
				batch.add(y);
				if (count.incrementAndGet() % batchSize == 0)
				{
					saveBatch(batch);
					batch.clear();
					log.infov("saving {0}: {1}/{2}", name, count.get(), objects.size());
				}
			}
			catch (Exception e)
			{
				log.error(e);
				log.errorv("error: {0}", e.getMessage(), e);
				e.printStackTrace();
			}
		});

		try
		{
			if (!batch.isEmpty())
			{
				saveBatch(batch);
				log.infov("saving {0}: {1}/{2}", name, count.get(), objects.size());
			}
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Transactional
	public <T> void saveBatch(Collection<T> objects)
	{
		for (T o : objects)
			try
			{
				em.merge(o);
			}
			catch (Exception e)
			{
				log.error(e);
				log.errorv("error: {0}", e.getMessage(), e);
				e.printStackTrace();
			}
	}

	public void saveToFile(List<ActiveDirectoryGroup> groups, String filename)
	{
		try
		{
			FileOutputStream out = new FileOutputStream(filename);
			groups.forEach(g -> g.getMembers()
			                     .forEach(m -> {
				                     try
				                     {
					                     out.write(String.format("%s,%s\n", g.getName(), m.getName())
					                                     .getBytes(StandardCharsets.UTF_8));
				                     }
				                     catch (IOException e)
				                     {
					                     throw new RuntimeException(e);
				                     }
			                     }));
			out.flush();
			out.close();
		}
		catch (IOException ioe)
		{
			log.error(ioe);
			log.errorv("error: {0}", ioe.getMessage(), ioe);
			ioe.printStackTrace();
		}
	}
}
