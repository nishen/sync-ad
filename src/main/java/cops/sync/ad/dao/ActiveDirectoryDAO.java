package cops.sync.ad.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import java.util.*;

import static java.util.stream.Collectors.joining;

@ApplicationScoped
public class ActiveDirectoryDAO
{
	private static final Logger log = Logger.getLogger(ActiveDirectoryDAO.class);

	private static final String USER_FILTER = "(&(objectClass=User)(!(userAccountControl=66050)))";

	private static final String GROUP_FILTER = "(objectClass=Group)";

	private static final Set<String> LDAP_TIMESTAMP_ATTRIBUTES =
			Set.of("pwdlastset", "accountexpires", "lastlogon", "lastlogontimestamp", "badpasswordtime");

	@ConfigProperty(name = "ldap.basedn.group")
	private String groupBaseDn;

	@ConfigProperty(name = "ldap.basedn.user")
	private String userBaseDn;

	@Inject
	private LdapContext ctx;

	public Map<String, Map<String, String>> getGroups(String baseDn, Set<String> attrs)
	{
		Map<String, Map<String, String>> result;

		try
		{
			result = getLdapObjects(baseDn, GROUP_FILTER, attrs);
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			log.infov("error: {0}", e.getMessage(), e);
			result = Collections.emptyMap();
		}

		return result;
	}

	public Map<String, Map<String, String>> getUsers(String baseDn, Set<String> attrs)
	{
		Map<String, Map<String, String>> result;

		try
		{
			result = getLdapObjects(baseDn, USER_FILTER, attrs);
		}
		catch (Exception e)
		{
			log.error(e.getMessage());
			log.infov("error: {0}", e.getMessage(), e);
			result = Collections.emptyMap();
		}

		return result;
	}

	public Map<String, Map<String, String>> getLdapObjects(String baseDn, String filter, Set<String> attrs)
			throws Exception
	{
		Map<String, Map<String, String>> result = new HashMap<>();
		log.debugv("searching for ldap objects: {0}", filter);

		try
		{
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctls.setReturningAttributes(attrs.toArray(String[]::new));
			ctls.setReturningObjFlag(false);
			ctls.setCountLimit(0);

			int pageSize = 1000;
			byte[] cookie = null;
			ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });

			do
			{
				NamingEnumeration<SearchResult> resultList = ctx.search(baseDn, filter, ctls);
				while (resultList != null && resultList.hasMoreElements())
				{
					SearchResult searchResult = resultList.nextElement();
					Attributes attributes = searchResult.getAttributes();
					Map<String, String> attrResult = new HashMap<>();
					result.put(searchResult.getName(), attrResult);

					if (attributes == null || attributes.size() == 0)
					{
						log.warnv("no attributes found for: {0}", searchResult.getName());
						continue;
					}

					for (Iterator<? extends Attribute> it = attributes.getAll()
					                                                  .asIterator(); it.hasNext(); )
					{
						Attribute attr = it.next();
						String key = attr.getID();
						String val = attr.get()
						                 .toString();
						if (LDAP_TIMESTAMP_ATTRIBUTES.contains(key.toLowerCase()))
							val = getDateFromTimestamp(val).toString();

						attrResult.put(key, val);
					}
				}

				// Examine the paged results control response
				Control[] controls = ctx.getResponseControls();
				if (controls != null)
					for (Control control : controls)
						if (control instanceof PagedResultsResponseControl prrc)
							cookie = prrc.getCookie();

				ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
			}
			while (cookie != null);
		}
		catch (Exception ne)
		{
			log.errorv("unable to obtain ldap objects: {0}", ne.getMessage());
			log.info("unable to connect to directory", ne);

			throw ne;
		}

		log.debugv("obtaining ldap objects complete: {0}", result.size());

		return result;
	}

	public Set<String> getUserCommonNames() throws Exception
	{
		Set<String> commonNames = new HashSet<>();
		log.debug("obtaining user CommonNames");

		try
		{
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctls.setReturningAttributes(new String[] { "cn" });
			ctls.setReturningObjFlag(false);
			ctls.setCountLimit(0);

			int pageSize = 1000;
			byte[] cookie = null;
			ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });

			do
			{
				NamingEnumeration<SearchResult> resultList = ctx.search(userBaseDn, USER_FILTER, ctls);
				while (resultList != null && resultList.hasMoreElements())
				{
					SearchResult searchResult = resultList.nextElement();
					Attributes attributes = searchResult.getAttributes();
					if (attributes == null || attributes.get("cn") == null)
					{
						log.warnv("no attributes found for: {0}", searchResult.getName());
						continue;
					}

					String cn = (String) attributes.get("cn")
					                               .get();
					commonNames.add(cn);
				}

				// Examine the paged results control response
				Control[] controls = ctx.getResponseControls();
				if (controls != null)
					for (Control control : controls)
						if (control instanceof PagedResultsResponseControl prrc)
							cookie = prrc.getCookie();

				ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
			}
			while (cookie != null);
		}
		catch (Exception ne)
		{
			log.errorv("unable to obtain Common Names: {0}", ne.getMessage());
			log.info("unable to connect to directory", ne);

			throw ne;
		}

		log.debugv("obtaining common names complete: {0}", commonNames.size());

		return commonNames;
	}

	public Set<String> getGroupMembers(String groupName)
	{
		Set<String> groupMembers = new HashSet<>();

		log.debugv("obtaining group information: {0}", groupName);

		try
		{
			String dn = "cn=" + groupName + "," + groupBaseDn;

			int rangeStep = 1500;
			int rangeStart = 0;
			int rangeEnd = rangeStart + rangeStep - 1;
			boolean finished = false;
			while (!finished)
			{
				String attr = "member";
				String retAttr = attr + ";range=" + rangeStart + "-" + rangeEnd;

				SearchControls ctls = new SearchControls();
				ctls.setSearchScope(SearchControls.OBJECT_SCOPE);
				ctls.setReturningAttributes(new String[] { retAttr });
				ctls.setReturningObjFlag(false);
				ctls.setCountLimit(0);

				NamingEnumeration<SearchResult> resultList = ctx.search(dn, "(objectclass=group)", ctls);
				if (resultList == null || !resultList.hasMoreElements())
				{
					log.infov("no members found in group: {0}", dn);
					return groupMembers;
				}

				SearchResult searchResult = resultList.nextElement();
				Attributes attributes = searchResult.getAttributes();
				if (attributes == null)
					return groupMembers;

				@SuppressWarnings("unchecked")
				NamingEnumeration<Attribute> attributeList = (NamingEnumeration<Attribute>) attributes.getAll();
				if (!attributeList.hasMoreElements())
					return groupMembers;

				while (attributeList.hasMoreElements())
				{
					Attribute attribute = attributeList.nextElement();
					log.debugv("attribute: {0}", attribute.getID());
					if (attribute.getID()
					             .startsWith(attr))
					{
						if (attribute.getID()
						             .endsWith("*"))
							finished = true;

						@SuppressWarnings("unchecked")
						NamingEnumeration<String> values = (NamingEnumeration<String>) attribute.getAll();
						while (values.hasMoreElements())
						{
							String member = values.nextElement();
							groupMembers.add(member);
							log.debugv("group: {0}", member);
						}
					}
				}

				rangeStart += rangeStep;
				rangeEnd = rangeStart + rangeStep - 1;
			}
		}
		catch (Exception ne)
		{
			log.errorv("unable to obtain group: {0}", groupName);
			log.info("unable to connect to directory", ne);

			return groupMembers;
		}

		log.debug("obtaining group information complete");

		return groupMembers;
	}

	public boolean groupExists(String groupName)
	{
		String dn = "cn=" + groupName + "," + groupBaseDn;

		try
		{
			LdapContext group = (LdapContext) ctx.lookup(dn);
			if (group == null)
				log.info("null group!");
		}
		catch (NameNotFoundException nnfe)
		{
			log.infov("group does not exist: {0}", dn);
			return false;
		}
		catch (NamingException ne)
		{
			log.errorv("error checking group: {0}", dn, ne);
		}

		return true;
	}

	public String getUserDN(String oneId)
	{
		log.infov("searching ad for dn: {0}", oneId);
		try
		{
			SearchControls ctls = new SearchControls();
			ctls.setReturningAttributes(new String[] { "dn" });
			ctls.setReturningObjFlag(false);
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			Name name = new LdapName(userBaseDn);
			String filter = "(sAMAccountName=" + oneId + ")";
			NamingEnumeration<SearchResult> results = ctx.search(name, filter, ctls);

			SearchResult result = null;
			if (results.hasMoreElements())
				result = results.nextElement();

			if (result == null)
				return null;

			return result.getNameInNamespace();
		}
		catch (NamingException ne)
		{
			log.errorv("error finding user: {0}", oneId);
		}

		return null;
	}

	public String getGroupDn(String groupName)
	{
		return "cn=" + groupName + "," + groupBaseDn;
	}

	public void addUserToGroup(String dn, String groupName)
	{
		try
		{
			String groupDn = "cn=" + groupName + "," + groupBaseDn;
			ModificationItem mod = new ModificationItem(LdapContext.ADD_ATTRIBUTE, new BasicAttribute("member", dn));
			ctx.modifyAttributes(groupDn, new ModificationItem[] { mod });
		}
		catch (NamingException ne)
		{
			log.errorv("unable to add user to group: {0} -> {1}", dn, groupName);
			log.info("unable to add user to group:", ne);
		}
	}

	public void delUserFromGroup(String dn, String groupName)
	{
		try
		{
			String groupDn = "cn=" + groupName + "," + groupBaseDn;
			ModificationItem mod = new ModificationItem(LdapContext.REMOVE_ATTRIBUTE, new BasicAttribute("member", dn));
			ctx.modifyAttributes(groupDn, new ModificationItem[] { mod });
		}
		catch (NamingException ne)
		{
			log.errorv("unable to remove user from group: {0} -> {1}", dn, groupName);
			log.info("unable to remove user from group:", ne);
		}
	}

	public void addGroup(String groupName)
	{
		Attribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("top");
		objectClass.add("group");

		String category = "CN=Group,CN=Schema,CN=Configuration,DC=uni,DC=mq,DC=edu,DC=au";
		Attribute objectCategory = new BasicAttribute("objectCategory", category);
		Attribute name = new BasicAttribute("name", groupName);
		Attribute sAMAccountName = new BasicAttribute("sAMAccountName", groupName);

		Attributes container = new BasicAttributes();
		container.put(objectClass);
		container.put(objectCategory);
		container.put(name);
		container.put(sAMAccountName);

		String dn = "cn=" + groupName + "," + groupBaseDn;

		try
		{
			ctx.createSubcontext(dn, container);
		}
		catch (NamingException ne)
		{
			log.errorv("unable to add group: {0}", dn, ne);
		}
	}

	public Map<String, Map<String, String>> getUserAttributes(Set<String> attrsList) throws Exception
	{
		Set<String> attrsToFind = new HashSet<>(attrsList);
		attrsToFind.add("cn");

		Map<String, Map<String, String>> users = new HashMap<>(100000);

		log.debugv("obtaining user attribute information: {0}", String.join(", ", attrsToFind));
		try
		{
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctls.setReturningAttributes(attrsToFind.toArray(new String[0]));
			ctls.setReturningObjFlag(false);
			ctls.setCountLimit(0);

			int page = 0;
			int pageSize = 1000;
			byte[] cookie = null;
			ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });

			do
			{
				NamingEnumeration<SearchResult> resultList = ctx.search(userBaseDn, "(objectclass=User)", ctls);
				while (resultList != null && resultList.hasMoreElements())
				{
					SearchResult searchResult = resultList.nextElement();
					Attributes attributes = searchResult.getAttributes();
					if (attributes == null)
					{
						log.warnv("no attributes found for: {0}", searchResult.getName());
						continue;
					}

					Map<String, String> attrs = new HashMap<>();
					for (String a : attrsToFind)
					{
						Attribute attribute = attributes.get(a);
						if (attribute == null)
							continue;

						String v = (String) attribute.get();
						if (v == null)
							continue;

						attrs.put(a, v);
					}

					if (!attrs.containsKey("dn"))
						attrs.put("dn", searchResult.getNameInNamespace());

					String cn = (String) attributes.get("cn")
					                               .get();

					users.put(cn.toLowerCase(), attrs);
				}

				// Examine the paged results control response
				Control[] controls = ctx.getResponseControls();
				if (controls != null)
					for (Control control : controls)
						if (control instanceof PagedResultsResponseControl prrc)
							cookie = prrc.getCookie();

				ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
				if (++page % 10 == 0)
					log.debugv("processed page {0}", page);
			}
			while (cookie != null);
		}
		catch (Exception e)
		{
			log.errorv("unable to obtain Common Names: {0}", e.getMessage());
			log.info("unable to connect to directory", e);

			throw e;
		}

		log.debugv("obtaining user attributes complete: {0}", users.size());

		return users;
	}

	public Map<String, Map<String, Set<String>>> getUserAttributesAsSets(Set<String> attrsList, Set<String> groups)
			throws Exception
	{
		Set<String> attrsToFind = new HashSet<>(attrsList);
		attrsToFind.add("cn");

		Map<String, Map<String, Set<String>>> users = new HashMap<>(100000);

		log.debugv("obtaining user attribute information: {0}", String.join(", ", attrsToFind));

		try
		{
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctls.setReturningAttributes(attrsToFind.toArray(new String[0]));
			ctls.setReturningObjFlag(false);
			ctls.setCountLimit(0);

			int page = 0;
			int pageSize = 1000;
			byte[] cookie = null;
			ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });

			do
			{
				String groupFilter = groups.stream()
				                           .map(s -> "(memberOf=CN=" + s + "," + groupBaseDn + ")")
				                           .collect(joining());
				String filter = "(&(objectclass=User)(|" + groupFilter + "))";
				NamingEnumeration<SearchResult> resultList = ctx.search(userBaseDn, filter, ctls);
				while (resultList != null && resultList.hasMoreElements())
				{
					SearchResult searchResult = resultList.nextElement();
					Attributes attributes = searchResult.getAttributes();
					if (attributes == null)
					{
						log.warnv("no attributes found for: {0}", searchResult.getName());
						continue;
					}

					Map<String, Set<String>> attrs = new HashMap<>();
					for (String a : attrsToFind)
					{
						Attribute attribute = attributes.get(a);
						if (attribute == null)
							continue;

						Set<String> valueSet = new HashSet<>();
						NamingEnumeration<?> values = attribute.getAll();
						while (values.hasMore())
							valueSet.add((String) values.next());

						attrs.put(a.toLowerCase(), valueSet);
					}

					if (!attrs.containsKey("dn"))
						attrs.put("dn", Set.of(searchResult.getNameInNamespace()));

					String cn = (String) attributes.get("cn")
					                               .get();

					users.put(cn.toLowerCase(), attrs);
				}

				// Examine the paged results control response
				Control[] controls = ctx.getResponseControls();
				if (controls != null)
					for (Control control : controls)
						if (control instanceof PagedResultsResponseControl prrc)
							cookie = prrc.getCookie();

				ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });

				if (++page % 10 == 0)
					log.infov("fetched ad users: {0}", page * 1000);
			}
			while (cookie != null);
		}
		catch (Exception e)
		{
			log.errorv("unable to obtain Common Names: {0}", e.getMessage());
			log.info("unable to connect to directory", e);

			throw e;
		}

		log.debugv("obtaining user attributes complete: {0}", users.size());

		return users;
	}

	public Map<String, Set<String>> getUserAttributes(String oneId, Set<String> attrsList)
	{
		Set<String> attrsToFind = new HashSet<>(attrsList);
		attrsToFind.add("cn");

		try
		{
			SearchControls ctls = new SearchControls();
			ctls.setReturningAttributes(attrsToFind.toArray(new String[0]));
			ctls.setReturningObjFlag(false);
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			Name name = new LdapName(userBaseDn);
			String filter = "(sAMAccountName=" + oneId + ")";
			NamingEnumeration<SearchResult> results = ctx.search(name, filter, ctls);

			SearchResult result = null;
			if (results.hasMoreElements())
				result = results.nextElement();

			if (result == null)
				return null;

			Attributes attributes = result.getAttributes();
			if (attributes == null)
				return Collections.emptyMap();

			Map<String, Set<String>> userAttributes = new HashMap<>();

			for (String attrToFind : attrsToFind)
			{
				Attribute attribute = attributes.get(attrToFind);
				if (attribute == null)
					continue;

				Set<String> valueSet = new HashSet<>();
				NamingEnumeration<?> values = attribute.getAll();
				while (values.hasMore())
					valueSet.add((String) values.next());

				userAttributes.put(attrToFind.toLowerCase(), valueSet);
				log.debugv("{0}={1}", attrToFind, valueSet);
			}

			if (!userAttributes.containsKey("dn"))
				userAttributes.put("dn", Set.of(result.getNameInNamespace()));

			return userAttributes;
		}
		catch (NamingException ne)
		{
			log.errorv("error finding user: {0}", oneId);
		}

		return null;
	}

	public Date getDateFromTimestamp(String timestamp)
	{
		long time = (Long.parseLong(timestamp) / 10000L) - +11644473600000L;
		return new Date(time);
	}
}
