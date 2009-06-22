package net.anotheria.webutils.service;

import static junit.framework.Assert.*;

import java.util.List;

import org.junit.Test;

public class XMLUserTest {
	@Test public void testRole(){
		XMLUser u = new XMLUser();
		assertFalse(u.isUserInRole("foo"));
		
		u.addRole("foo");
		assertTrue(u.isUserInRole("foo"));
		
		u = new XMLUser("a","a","1,2,3");
		assertTrue(u.isUserInRole("1"));
		assertTrue(u.isUserInRole("2"));
		assertTrue(u.isUserInRole("3"));
		assertTrue("roles are part of toString method", u.toString().indexOf("1")>-1);
		assertTrue("roles are part of toString method", u.toString().indexOf("2")>-1);
		assertTrue("roles are part of toString method", u.toString().indexOf("3")>-1);
		
		u = new XMLUser("a", "a", (String)null);
		assertFalse(u.isUserInRole("foo"));
		
		u = new XMLUser("a", "a", (List<String>)null);
		assertFalse(u.isUserInRole("foo"));
	}
}
