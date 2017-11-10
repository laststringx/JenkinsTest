package com.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.facade.TestingFacade;

public class TestLocalClient {
	
	public static void main(String[] args) {
		greetFromRemote("Gourab");
	}

	private static void greetFromRemote(String name){
		TestingFacade remote;
		try {
			long start1  = System.nanoTime();
			
			Properties p = new Properties();
			p.put("remote.connections", "node1");
			p.put("remote.connection.node1.port", "4447");  // the default remoting port, replace if necessary
			p.put("remote.connection.node1.host", "localhost");
			p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
			p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			p.put("org.jboss.ejb.client.scoped.context", true); // enable scoping here
			
			Context context = new InitialContext(p);
			Context ejbRootNamingContext = (Context) context.lookup("ejb:");
			
			/*Properties prop = new Properties();
			
			prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			prop.put("jboss.naming.client.ejb.context", true);
			prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			prop.put(Context.PROVIDER_URL, "remote://localhost:4447");
			
			InitialContext ejbRootNamingContext = new InitialContext(prop);*/
			
			
			remote = (TestingFacade)ejbRootNamingContext.lookup("TestEar/TestEjb/TestingBean!com.facade.TestingFacade");
			System.out.println(remote.sayHello(name));
			System.out.println(System.nanoTime()-start1);
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
