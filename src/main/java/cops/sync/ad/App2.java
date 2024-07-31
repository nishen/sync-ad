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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

@QuarkusMain
@ActivateRequestContext
public class App2 implements QuarkusApplication
{
	static final Logger log = Logger.getLogger(App2.class);

	//	static final DateFormat addf = new SimpleDateFormat("yyyyMMddHHmmss'.0Z'"); // 20230607015447.0Z

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

	@Transactional
	public void updateGroups()
	{
		try
		{
			log.info("fetching groups from ad");
			List<ActiveDirectoryGroup> groups = ad.getGroups("DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			                                                 Set.of("distinguishedName", "cn", "whenChanged",
			                                                        "whenCreated"))
			                                      .values()
			                                      .parallelStream()
			                                      .map(this::makeActiveDirectoryGroup)
			                                      .toList();
			log.infov("fetched groups from ad: {0}", groups.size());

			groups.forEach(em::merge);
			log.infov("saved groups to db: {0}", groups.size());
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
		}
	}

	@Transactional
	public void updateUsers()
	{
		try
		{
			log.info("fetching users from ad");
			List<ActiveDirectoryUser> users =
					ad.getUsers("OU=Staff,OU=Active,OU=MQ-Users,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
					            Set.of("distinguishedName", "cn", "sAMAccountName", "displayName", "lastLogonTimestamp",
					                   "accountExpires", "pwdLastSet", "whenChanged", "whenCreated"))
					  .values()
					  .parallelStream()
					  .map(this::makeActiveDirectoryUser)
					  .toList();
			log.infov("fetched users from ad: {0}", users.size());

			users.forEach(em::merge);

			log.infov("saved users to db: {0}", users.size());
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Transactional
	public void updateGroupMembership()
	{
		try
		{
			log.info("fetching groups from db");
			Map<String, ActiveDirectoryGroup> groups =
					em.createQuery("select g from ActiveDirectoryGroup g", ActiveDirectoryGroup.class)
					  .getResultStream()
					  .collect(toMap(ActiveDirectoryGroup::getDn, g -> g, (x, y) -> x));
			log.infov("fetched groups from db: {0}", groups.size());

			log.info("fetching users from db");
			Map<String, ActiveDirectoryUser> users =
					em.createQuery("select u from ActiveDirectoryUser u", ActiveDirectoryUser.class)
					  .getResultStream()
					  .collect(toMap(ActiveDirectoryUser::getDn, u -> u, (x, y) -> x));
			log.infov("fetched users from db: {0}", users.size());

			//TODO: Create group -> members rather than members -> groups
			groups.entrySet()
			      .stream()
			      .peek(e -> log.infov("group: {0}", e.getKey()))
			      .forEach(e -> e.getValue()
			                     .getMembers()
			                     .addAll(ad.getGroupMembers(e.getKey())
			                               .stream()
			                               .map(users::get)
			                               .collect(toSet())));

			//			log.info("mapping users to groups - phase 1");
			//			Map<String, Set<ActiveDirectoryUser>> userGroupMapping;
			//			userGroupMapping = users.values()
			//			                        .stream()
			//			                        .flatMap(u -> ad.getUserGroups(u.getDn())
			//			                                        .stream()
			//			                                        .map(g -> Map.entry(g, users.get(u.getDn()))))
			//			                        .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toSet())));
			//
			//			log.info("mapping users to groups - phase 2");
			//			userGroupMapping.entrySet()
			//			                .stream()
			//			                .filter(e -> groups.get(e.getKey()) == null)
			//			                .forEach(e -> log.warnv("missing group for mapping: {0}", e.getKey()));
			//
			//			log.info("mapping users to groups - phase 3");
			//			userGroupMapping.entrySet()
			//			                .stream()
			//			                .filter(e -> groups.get(e.getKey()) != null)
			//			                .forEach(e -> groups.get(e.getKey())
			//			                                    .getMembers()
			//			                                    .addAll(e.getValue()));

			log.info("updating db with group/user mappings");
			groups.values()
			      .forEach(em::merge);

			log.infov("saved: {0}", groups.values()
			                              .size());
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
			e.printStackTrace();
		}
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

		try
		{
			group.setCreated(addf.parse(data.get("whenCreated")));
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

		try
		{
			user.setCreated(addf.parse(data.get("whenCreated")));
			user.setUpdated(addf.parse(data.get("whenChanged")));
		}
		catch (ParseException pe)
		{
			log.warnv("unable to parse active directory date: {0}/{1}", data.get("whenCreated"),
			          data.get("whenChanged"));
		}

		return user;
	}
}
