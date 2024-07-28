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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@QuarkusMain
@ActivateRequestContext
public class App2 implements QuarkusApplication
{
	static final Logger log = Logger.getLogger(App2.class);

	static final SimpleDateFormat addf = new SimpleDateFormat("yyyyMMddHHmmss'.0Z'");

	@Inject
	@PersistenceUnit("adinfo")
	EntityManager em;

	@Inject
	ActiveDirectoryDAO ad;

	@Override
	@Transactional
	public int run(String... args)
	{
		Map<String, Map<String, String>> objects;
		try
		{
			objects = ad.getGroups("DC=mqauth,DC=uni,DC=mq,DC=edu,DC=au",
			                       Set.of("distinguishedName", "cn", "whenChanged", "whenCreated"));
			objects.forEach((key, value) -> log.infov("{0} -> {1}", key, value));
			log.infov("size: {0}", objects.size());
			objects.values()
			       .stream()
			       .map(this::makeActiveDirectoryGroup)
			       .forEach(em::merge);
		}
		catch (Exception e)
		{
			log.error(e);
		}

		return 0;
	}

	private ActiveDirectoryGroup makeActiveDirectoryGroup(Map<String, String> data)
	{
		ActiveDirectoryGroup group = new ActiveDirectoryGroup();
		String name = data.get("cn");
		group.setName(name);
		group.setDn(data.get("distinguishedName"));
		group.setCn(data.get("cn"));

		String[] dnparts = data.get("distinguishedName").split(",");
		String normalised_dn = String.join(",", Arrays.asList(dnparts).reversed());

		log.infov("normalised_dn: {0}", normalised_dn);
		group.setNormalisedDn(normalised_dn);
		// 20230607015447.0Z
		try
		{
			group.setCreated(addf.parse(data.get("whenCreated")));
		}
		catch (ParseException pe)
		{
			log.warnv("unable to parse active directory date [whenCreated]: {0}", data.get("whenCreated"));
		}

		try
		{
			group.setUpdated(addf.parse(data.get("whenChanged")));
		}
		catch (ParseException pe)
		{
			log.warnv("unable to parse active directory date [whenChanged]: {0}", data.get("whenChanged"));
		}

		return group;
	}
}
