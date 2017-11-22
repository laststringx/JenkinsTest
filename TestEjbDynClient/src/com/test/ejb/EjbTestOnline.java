package com.test.ejb;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.facade.TestingFacade;
@ManagedBean
@SessionScoped
public class EjbTestOnline implements Serializable {

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
		TestingFacade remote = null;
		if(name != null || !name.equals("")){

			long start1  = System.nanoTime();
			Properties p = new Properties();
			p.put("remote.connections", "node1");
			p.put("remote.connection.node1.port", "4447");  // the default remoting port, replace if necessary
			p.put("remote.connection.node1.host", "localhost");
			p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
			p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			p.put("org.jboss.ejb.client.scoped.context", true); // enable scoping


			try {
				Context context = new InitialContext(p);
				Context ejbRootNamingContext = (Context) context.lookup("ejb:");
				remote = (TestingFacade)ejbRootNamingContext.lookup("TestEar/TestEjb/TestingBean!com.facade.TestingFacade");
			} catch (NamingException e) {
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
