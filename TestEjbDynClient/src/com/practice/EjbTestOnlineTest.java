package com.practice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import com.test.ejb.EjbTestOnline;

public class EjbTestOnlineTest {
	String name = "Gourab";
	
	EjbTestOnline etol = new EjbTestOnline();
	@Test
	public void testGetName(){
		System.out.println("Starting testing the EJB");
		etol.setName(name);
		assertEquals("Hiii "+name, etol.getName());
		System.out.println("Testing done");
		
	}
}
