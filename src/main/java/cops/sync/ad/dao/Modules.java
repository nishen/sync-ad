package cops.sync.ad.dao;

import cops.sync.ad.util.BlindSSLSocketFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

@ApplicationScoped
public class Modules
{
	private static final Logger log = Logger.getLogger(Modules.class);

	private LdapContext ldapContext;

	@ConfigProperty(name = "ldap.providerUrl")
	String url;

	@ConfigProperty(name = "ldap.securityPrincipal")
	String usr;

	@ConfigProperty(name = "ldap.securityCredentials")
	String pwd;

	@Default
	@Produces
	LdapContext provideLdapContext()
	{
		if (ldapContext != null)
			return ldapContext;

		Hashtable<String, String> env = getLdapContextConfig(url, usr, pwd);

		try
		{
			ldapContext = new InitialLdapContext(env, null);
			log.infov("connected as {0} to ldap server: {1}", usr, url);
		}
		catch (NamingException ne)
		{
			log.errorv("unable to connect to ldap server: {0}", url);
			log.error("", ne);
		}

		return ldapContext;
	}

	private Hashtable<String, String> getLdapContextConfig(String url, String usr, String pwd)
	{
		Hashtable<String, String> env = new Hashtable<>();

		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put("java.naming.ldap.factory.socket", BlindSSLSocketFactory.class.getName());
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PROTOCOL, "TLSv1.2");

		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PRINCIPAL, usr);
		env.put(Context.SECURITY_CREDENTIALS, pwd);
		return env;
	}
}
