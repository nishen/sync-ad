package cops.sync.ad;

import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.runtime.QuarkusApplication;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

import java.util.Map;
import java.util.stream.Stream;

@ActivateRequestContext
public class App implements QuarkusApplication
{
	static final Logger log = Logger.getLogger(App.class);

	@Inject
	@PersistenceUnit("patron")
	EntityManager em;

	@Override
	public int run(String... args)
	{
		final String query = "select count(p) from %s p where p.isDeleted = 'N' and p.baseIsDeleted = 'N'";
		Stream.of("SnowflakePatronStaff", "SnowflakePatronStudent", "SnowflakePatronSponsored", "SnowflakePatronELC")
		      .map(s -> Map.entry(s, em.createQuery(String.format(query, s), Long.class)
		                               .getSingleResult()))
		      .forEach(e -> log.infov("{0}: {1}", e.getKey(), e.getValue()));

		return 0;
	}
}
