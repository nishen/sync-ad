package cops.sync.ad;

import cops.sync.ad.dao.ActiveDirectoryDAO;
import cops.sync.ad.entity.adinfo.ActiveDirectoryGroup;
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

@QuarkusMain
@ActivateRequestContext
public class App2 implements QuarkusApplication
{
	static final Logger log = Logger.getLogger(App2.class);

	static final DateFormat addf = new SimpleDateFormat("yyyyMMddHHmmss'.0Z'"); // 20230607015447.0Z

	@Inject
	@PersistenceUnit("adinfo")
	EntityManager em;

	@Inject
	ActiveDirectoryDAO ad;

	@Override
	public int run(String... args)
	{
//		updateGroups(args);
		updateUsers(args);
		return 0;
	}

	public void updateUsers(String... args)
	{
		try
		{
			log.info("fetching users");
			Map<String, Map<String, String>> users =
					ad.getUsers("OU=LIB,OU=Staff,OU=Active,OU=MQ-Users,DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
					             Set.of("distinguishedName", "cn", "lastLogonTimestamp", "whenChanged", "whenCreated"));
			users.forEach((key, value) -> log.infov("user: {0}: {1}", key, value));
			log.infov("fetched users: {0}", users.size());
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
		}
	}

	@Transactional
	public void updateGroups(String... args)
	{
		try
		{
			log.info("fetching groups");
			List<ActiveDirectoryGroup> groups = ad.getGroups("DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			                                                 Set.of("distinguishedName", "cn", "whenChanged",
			                                                        "whenCreated"))
			                                      .values()
			                                      .stream()
			                                      .map(this::makeActiveDirectoryGroup)
			                                      .toList();
			log.infov("fetched groups: {0}", groups.size());

			groups.forEach(em::merge);
			log.infov("saved: {0}", groups.size());
		}
		catch (Exception e)
		{
			log.error(e);
			log.errorv("error: {0}", e.getMessage(), e);
		}
	}

	private ActiveDirectoryGroup makeActiveDirectoryGroup(Map<String, String> data)
	{
		String name = data.get("cn");
		String[] dnparts = data.get("distinguishedName")
		                       .split(",");
		String normalised_dn = String.join(",", Arrays.asList(dnparts)
		                                              .reversed());

		ActiveDirectoryGroup group = new ActiveDirectoryGroup();
		group.setName(name);
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
}
