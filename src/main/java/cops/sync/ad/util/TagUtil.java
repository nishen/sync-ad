package cops.sync.ad.util;

import cops.sync.ad.entity.snowflake.SnowflakePatron;
import org.jboss.logging.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class TagUtil
{
	private static final Logger log = Logger.getLogger(TagUtil.class);

	public static Set<String> validStatCats(Set<String> required, Set<String> provided)
	{
		if (required == null || required.isEmpty())
			return Collections.emptySet();

		Set<String> validStatCats = new TreeSet<>();
		for (String req : required)
		{
			String r = req.toLowerCase();
			log.debugv("required: {0}", r);
			String[] parts = r.split("¦");
			Set<String> criteriaNormal = Set.of(parts).stream().filter(s -> !s.startsWith("r:")).collect(toSet());
			Map<String, Boolean> criteriaRegex = Set.of(parts)
			                                        .stream()
			                                        .filter(s -> s.startsWith("r:"))
			                                        .collect(toMap(p -> p.replace("r:", ""), p -> Boolean.FALSE));
			log.debugv("partsN: {0}", criteriaNormal);
			log.debugv("partsR: {0}", criteriaRegex);

			for (String regex : criteriaRegex.keySet())
				for (String statCat : provided)
					if (Pattern.matches(regex, statCat))
						criteriaRegex.put(regex, Boolean.TRUE);

			boolean normalCriteriaMet = provided.containsAll(criteriaNormal);
			boolean regexpCriteriaMet = criteriaRegex.values().stream().allMatch(val -> val == Boolean.TRUE);

			if (normalCriteriaMet && regexpCriteriaMet)
				validStatCats.add(r);
		}

		return validStatCats;
	}

	public static Set<String> coalesceStatCats(Map<String, SnowflakePatron> patron)
	{
		return patron.values().stream().flatMap(p -> p.getStatCats().stream()).collect(toSet());
	}
}
