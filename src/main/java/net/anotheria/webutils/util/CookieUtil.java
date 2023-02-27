package net.anotheria.webutils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Read and write cookies from requests and responses.
 * 
 * @author otoense
 */
public class CookieUtil {
	
	private static Logger log;
	
	static {
		log = LoggerFactory.getLogger(CookieUtil.class);
	}
	
	public static Cookie getCookieByName(HttpServletRequest req, String name) {
		Cookie result = null;
		Cookie[] cookies = req.getCookies();
		if (cookies != null){			
			for(int i=0; i<cookies.length; i++) {
				log.debug("Cookie[" + i +"]: " + cookies[i].getName() + "=" + cookies[i].getValue());
				if(name.equals(cookies[i].getName())) {
					return cookies[i];
				}	
			}
		}		
		return result;
	}
	
	public static String getCookieValue(HttpServletRequest req, String name) {
		Cookie cookie = getCookieByName(req, name);
		
		if(cookie != null)
			return cookie.getValue();
		return null;
	}
	
	public static void setCookie(HttpServletResponse res, String path, String name, String value, int expires) {
		Cookie cookie = new Cookie(name, value);
		cookie.setVersion(0);
		cookie.setMaxAge(expires);
		cookie.setPath(path);
		res.addCookie(cookie);
		log.debug("Add cookie " + name + " value=" + value + " to response.");
	}
	
	public static void setCookie(HttpServletResponse res, String name, String value, int expires) {
		setCookie(res, "/", name, value, expires);
	}
}
