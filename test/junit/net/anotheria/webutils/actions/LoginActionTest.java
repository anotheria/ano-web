/**
 * 
 */
package net.anotheria.webutils.actions;

import static org.junit.Assert.fail;

import java.io.File;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;
import net.anotheria.webutils.service.XMLUserManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.junit.Before;
import org.junit.Test;

/**
 * LoginAction Test.
 *
 * @author vkazhdan
 */
public class LoginActionTest {

	/**
	 * Setup.
	 */
	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}

	/**
	 * Test that null cookies didn't causes NullPointerException on execute.
	 *
	 * @throws Exception 
	 */
	@Test
	public void testNullCookies() throws Exception {
		XMLUserManager.init(new File("test/junit/users.xml"));
		ActionMapping actionMapping = new ActionMapping();
		actionMapping.addForwardConfig(new ActionForward("success", "success", false));
		
		// Test non-null cookie, should pass without throwing an exception
		try {
			HttpServletRequest nonNullCookieRequestMock = MockFactory.createMock(HttpServletRequest.class, new HttpServletRequestNonNullCookieMock());
			new LoginAction().doExecute(actionMapping, null, nonNullCookieRequestMock, null);
			System.out.println("Exception shouldn't be throwed above for non-null cookies!");
		} catch (Throwable e) {
			fail("Exception shouldn't be throwed for non-null cookies: " + e);
		}

		// Test null cookie, should also pass without throwing an exception
		try {
			HttpServletRequest nullCookieRequestMock = MockFactory.createMock(HttpServletRequest.class, new HttpServletRequestNullCookieMock());
			new LoginAction().doExecute(actionMapping, null, nullCookieRequestMock, null);
			System.out.println("Exception shouldn't be throwed above for null cookies!");
		} catch (Throwable e) {
			fail("Exception shouldn't be throwed for null cookies: " + e);
		}
	}

	/**
	 * Request mock, that returns null on getCookies().
	 */
	public static class HttpServletRequestNullCookieMock implements Mocking {
		public String getContextPath() {
			return null;
		}

		public Cookie [] getCookies() {
			return null;
		}
	}

	/**
	 * Request mock, that returns non-null on getCookies().
	 */
	public static class HttpServletRequestNonNullCookieMock implements Mocking {
		public String getContextPath() {
			return null;
		}

		public Cookie [] getCookies() {
			return new Cookie [0];
		}
	}
}
