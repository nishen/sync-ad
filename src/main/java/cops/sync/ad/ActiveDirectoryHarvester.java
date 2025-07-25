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

	static final List<String> OU_GROUP_BASES = List.of("DC=nd,DC=edu,DC=au");

	static final List<String> OU_USER_BASES = List.of("DC=nd,DC=edu,DC=au");

	static final Set<String> LDAP_ATTRIBUTES_USER =
			Set.of("samAccountName", "name", "distinguishedName", "cn", "description", "whenCreated", "whenChanged",
			       "pwdLastSet", "accountExpires", "lastLogonTimestamp", "displayName", "userPrincipalName", "mail",
			       "mailNickname", "division", "employeeType", "flags", "extensionAttribute1", "extensionAttribute2",
			       "extensionAttribute3", "extensionAttribute4", "extensionAttribute5", "extensionAttribute6",
			       "extensionAttribute7", "extensionAttribute8", "extensionAttribute9", "extensionAttribute10",
			       "extensionAttribute11", "extensionAttribute12", "extensionAttribute13", "extensionAttribute14",
			       "extensionAttribute15", "extensionAttribute16");

	static final Set<String> LDAP_ATTRIBUTES_GROUP =
			Set.of("sAMAccountName", "distinguishedName", "cn", "whenChanged", "whenCreated");

	@Inject
	@PersistenceUnit("adinfo")
	EntityManager em;

	@Inject
	ActiveDirectoryDAO ad;

	@Override
	public int run(String... args)
	{
		updateGroups();
		updateUsers();
		updateGroupMembership();

		return 0;
	}

	public void updateGroups()
	{
		try
		{
			log.info("processing ad groups");

			List<ActiveDirectoryGroup> groups;
			groups = OU_GROUP_BASES.stream()
			                       .peek(g -> log.infov("fetching group: {0}", g))
			                       .map(this::getGroupsFromAd)
			                       .peek(g -> log.infov("found groups: {0}", g.size()))
			                       .flatMap(Collection::stream)
			                       .toList();
			log.infov("fetched groups from ad: {0}", groups.size());

			save(groups, 1000, "groups");
			log.infov("saved groups to db: {0}", groups.size());
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
		}
	}

	public void updateUsers()
	{
		try
		{
			log.info("fetching users from ad");
			List<ActiveDirectoryUser> users = OU_USER_BASES.stream()
			                                               .peek(g -> log.infov("fetching users from: {0}", g))
			                                               .map(this::getUsersFromAd)
			                                               .peek(g -> log.infov("found users: {0}", g.size()))
			                                               .flatMap(Collection::stream)
			                                               .toList();

			List<ActiveDirectoryUser> noId = users.stream()
			                                      .filter(u -> u.getSamAccountName() == null)
			                                      .toList();

			noId.forEach(u -> log.infov("missing samAccountName: {0}", u.getName()));
			List<ActiveDirectoryUser> usersWithId = users.stream()
			                                             .filter(u -> u.getSamAccountName() != null)
			                                             .toList();

			save(usersWithId, 2000, "ad users");
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
		}
	}

	public void updateGroupMembership()
	{
		try
		{
			log.info("fetching groups from ad");
			List<ActiveDirectoryGroup> groups;
			groups = OU_GROUP_BASES.stream()
			                       .peek(g -> log.infov("fetching group: {0}", g))
			                       .map(this::getGroupsFromAd)
			                       .peek(g -> log.infov("found groups: {0}", g.size()))
			                       .flatMap(Collection::stream)
			                       .toList();
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
		}
	}

	private List<ActiveDirectoryUser> getUsersFromAd(String dn)
	{

		log.infov("getting users from ad: {0}", dn);

		return ad.getUsers(dn, LDAP_ATTRIBUTES_USER)
		         .values()
		         .stream()
		         .map(this::makeActiveDirectoryUser)
		         .toList();
	}

	private List<ActiveDirectoryGroup> getGroupsFromAd(String dn)
	{
		return ad.getGroups(dn, LDAP_ATTRIBUTES_GROUP)
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
		group.setSamAccountName(data.get("sAMAccountName"));
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

		user.setSamAccountName(data.get("sAMAccountName"));
		user.setName(data.get("cn"));
		user.setDn(data.get("distinguishedName"));
		user.setNormalisedDn(normalised_dn);
		user.setCn(data.get("cn"));
		user.setDisplayName(data.get("displayName"));
		user.setLastLogonTimestamp(ActiveDirectoryDAO.getDateFromTimestamp(data.get("lastLogonTimestamp")));
		user.setAccountExpires(ActiveDirectoryDAO.getDateFromTimestamp(data.get("accountExpires")));
		user.setPasswordLastSet(ActiveDirectoryDAO.getDateFromTimestamp(data.get("pwdLastSet")));
		user.setDescription(data.get("description"));
		user.setUserPrincipalName(data.get("userPrincipalName"));
		user.setMail(data.get("mail"));
		user.setMailNickname(data.get("mailNickname"));
		user.setDivision(data.get("division"));
		user.setEmployeeType(data.get("employeeType"));
		user.setFlags(data.get("flags"));
		user.setExtensionAttribute01(data.get("extensionAttribute1"));
		user.setExtensionAttribute02(data.get("extensionAttribute2"));
		user.setExtensionAttribute03(data.get("extensionAttribute3"));
		user.setExtensionAttribute04(data.get("extensionAttribute4"));
		user.setExtensionAttribute05(data.get("extensionAttribute5"));
		user.setExtensionAttribute06(data.get("extensionAttribute6"));
		user.setExtensionAttribute07(data.get("extensionAttribute7"));
		user.setExtensionAttribute08(data.get("extensionAttribute8"));
		user.setExtensionAttribute09(data.get("extensionAttribute9"));
		user.setExtensionAttribute10(data.get("extensionAttribute10"));
		user.setExtensionAttribute11(data.get("extensionAttribute11"));
		user.setExtensionAttribute12(data.get("extensionAttribute12"));
		user.setExtensionAttribute13(data.get("extensionAttribute13"));
		user.setExtensionAttribute14(data.get("extensionAttribute14"));
		user.setExtensionAttribute15(data.get("extensionAttribute15"));
		user.setExtensionAttribute16(data.get("extensionAttribute16"));

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
					                     out.write(
							                     String.format("%s,%s\n", g.getSamAccountName(), m.getSamAccountName())
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
		}
	}
}
