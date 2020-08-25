package de.woody64k.services.ldap.adapter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.woody64k.services.ldap.data.User;

@Service
public class LdapAdapter {
    @Value("${spring.ldap.hostname}")
    private String hostname;
    @Value("${spring.ldap.principal}")
    private String principal;
    @Value("${spring.ldap.credentials}")
    private String credentials;
    @Value("${spring.ldap.lookup-root}")
    private String lookupRoot;

    public List<User> loadDataByEMail(String mail) {
	Hashtable<String, String> environment = new Hashtable<String, String>();

	environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	environment.put(Context.PROVIDER_URL, String.format("ldap://%s:389", hostname));
	environment.put(Context.SECURITY_AUTHENTICATION, "simple");
	if (principal != null && !principal.isEmpty()) {
	    environment.put(Context.SECURITY_PRINCIPAL, principal);
	    environment.put(Context.SECURITY_CREDENTIALS, credentials);
	}

	try {
	    DirContext context = new InitialDirContext(environment);
	    try {
		String[] attrIDs = { "sn", "cn", "mail" };
		Attributes matchAttrs = new BasicAttributes(true);
		matchAttrs.put("mail", mail);
		SearchControls ctrls = new SearchControls();
		ctrls.setReturningAttributes(attrIDs);
		ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		NamingEnumeration<SearchResult> answer = context.search(lookupRoot, String.format("(mail=%s)", mail),
			ctrls);
		List<User> userList = new ArrayList<>();
		while (answer.hasMore()) {
		    SearchResult sr = (SearchResult) answer.next();
		    Attributes attributes = sr.getAttributes();
		    userList.add(new User(attributes.get("cn").toString(), attributes.get("sn").toString()));
		}

		answer.close();
		return userList;
	    } finally {
		// Context is always closed after opening it.
		context.close();
	    }
	} catch (Exception exception) {
	    String message = "An unexpected Error occured";
	    if (exception instanceof AuthenticationNotSupportedException) {
		message = "The authentication is not supported by the server";
	    } else if (exception instanceof AuthenticationException) {
		message = "Incorrect password or username";
	    } else if (exception instanceof NamingException) {
		message = "Error when trying to create the context";
	    }
	    throw new RuntimeException(message, exception);
	}

    }
}
