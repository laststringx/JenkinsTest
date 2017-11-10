package com.test.ejb;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.remote.TestEjbRemote;
@ManagedBean
@SessionScoped
public class EjbTestOnline implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name = "";
	private String greet;

	public String getGreet() {
		return greet;
	}

	public void setGreet(String greet) {
		this.greet = greet;
	}

	public String getName() {
		TestEjbRemote remote = null;
		if(name != null || !name.equals("")){
		
		long start1  = System.nanoTime();
		Properties p = new Properties();
		p.put("remote.connections", "node1");
		p.put("remote.connection.node1.port", "4447");  // the default remoting port, replace if necessary
		p.put("remote.connection.node1.host", "localhost");
		p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		p.put("org.jboss.ejb.client.scoped.context", true); // enable scoping here
		
		
/*Properties prop = new Properties();
		
		prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		prop.put("jboss.naming.client.ejb.context", true);
		prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		prop.put(Context.PROVIDER_URL, "remote://localhost:4447");
*/		
		

			
		try {
			Context context = new InitialContext(p);
			Context ejbRootNamingContext = (Context) context.lookup("ejb:");
			//InitialContext context = new InitialContext(prop);
			remote = (TestEjbRemote)ejbRootNamingContext.lookup("EjbTestingEar/EjbTesting/TestEjbBean!com.remote.TestEjbRemote");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(remote.sayHello(name));
		System.out.println(System.nanoTime()-start1);
		return remote.sayHello(name);
		}
		else
			return name;
	}

	@PostConstruct
	public void setNameToNull(){
		this.name = "";
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
}
