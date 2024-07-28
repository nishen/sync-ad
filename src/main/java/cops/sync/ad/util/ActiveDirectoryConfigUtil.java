package cops.sync.ad.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;

public class ActiveDirectoryConfigUtil
{
	private static final Logger log = Logger.getLogger(ActiveDirectoryConfigUtil.class);

	private static final String AD_CONFIG_FILE = "active-directory-config.json";

	private static final ObjectMapper mapper = new ObjectMapper();

	public static Map<String, Set<String>> getConfig()
	{
		return getConfig(AD_CONFIG_FILE);
	}

	public static Map<String, Set<String>> getConfig(String configFile)
	{
		configFile = configFile == null ? AD_CONFIG_FILE : configFile;

		JsonNode root;
		try
		{
			File config = new File(configFile);
			root = mapper.readTree(config);
		}
		catch (IOException ioe)
		{
			log.errorv("{0}", ioe.getMessage(), ioe);
			return null;
		}

		Map<String, Set<String>> originalAliases = getAliases(root);
		log.debugv("aliases-pre :{0}", originalAliases);

		Map<String, Set<String>> aliases = originalAliases.entrySet()
		                                                  .stream()
		                                                  .map(e -> resolveAliases(e, originalAliases))
		                                                  .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
		log.debugv("aliases :{0}", aliases);

		Map<String, Set<String>> originalGroups = getGroups(root);
		log.debugv("groups-pre :{0}", originalGroups);

		Map<String, Set<String>> groups = originalGroups.entrySet()
		                                                .stream()
		                                                .map(e -> resolveAliases(e, aliases))
		                                                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
		log.debugv("groups-post:{0}", groups);

		return groups;
	}

	private static Map<String, Set<String>> getAliases(JsonNode root)
	{
		Map<String, Set<String>> aliases = new HashMap<>();

		JsonNode aliasesNode = root.get("aliases");
		aliasesNode.forEach(p -> p.fields().forEachRemaining(n -> {
			log.debugv("n.key: {0}", n.getKey());
			aliases.computeIfAbsent(n.getKey(), k -> new TreeSet<>());

			n.getValue().forEach(v -> {
				log.debugv("n.val: {0}", v.textValue());
				aliases.get(n.getKey()).add(v.textValue());
			});
		}));

		return aliases;
	}

	private static Map<String, Set<String>> getGroups(JsonNode root)
	{
		Map<String, Set<String>> groups = new HashMap<>();

		JsonNode groupNode = root.get("groups");
		groupNode.forEach(p -> p.fields().forEachRemaining(n -> {
			String groupName = n.getKey();
			log.debugv("n.key: {0}", groupName);
			groups.computeIfAbsent(groupName, k -> new TreeSet<>());

			n.getValue().forEach(v -> {
				log.debugv("n.val: {0}", v.textValue());
				groups.get(groupName).add(v.textValue());
			});
		}));

		return groups;
	}

	private static Map.Entry<String, Set<String>> resolveAliases(Map.Entry<String, Set<String>> alias,
	                                                             Map<String, Set<String>> originalAliases)
	{
		alias.setValue(alias.getValue().stream().map(s -> {
			if (s.startsWith("a:"))
				return originalAliases.get(s.substring(2));
			return Set.of(s);
		}).flatMap(Collection::stream).collect(toCollection(TreeSet::new)));

		return alias;
	}
}
