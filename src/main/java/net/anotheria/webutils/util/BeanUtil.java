package net.anotheria.webutils.util;

import org.slf4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.PageContext;


@Deprecated
public class BeanUtil {
	
	private static Logger logger;
	
	public static void setLogger(Logger aLogger){
		logger = aLogger;
	}
	
	public static Logger getLogger(){
		return logger;
	}
	
	public static void addBeanToSessionUnsafe(
			HttpServletRequest request,
			String key,
			Object value) {

		addBean(request, PageContext.SESSION_SCOPE, key, value);
	}

	public static void addBeanToApplication(
		HttpServletRequest request,
		String key,
		Object value) {
		addBean(request, PageContext.APPLICATION_SCOPE, key, value);
	}

	public static void addBeanToRequest(
		HttpServletRequest request,
		String key,
		Object value) {
		addBean(request, PageContext.REQUEST_SCOPE, key, value);
	}

	public static Object getBeanFromSessionUnsafe(
		HttpServletRequest request,
		String key) {
		return getBean(request, PageContext.SESSION_SCOPE, key);
	}

	public static Object getBeanFromApplication(
		HttpServletRequest request,
		String key) {
		return getBean(request, PageContext.APPLICATION_SCOPE, key);
	}

	public static Object getBeanFromRequest(
		HttpServletRequest request,
		String key) {
		return getBean(request, PageContext.REQUEST_SCOPE, key);
	}

	public static void removeBeanFromSessionUnsafe(
		HttpServletRequest request,
		String key) {
		removeBean(request, PageContext.SESSION_SCOPE, key);
	}

	public static void removeBeanFromApplication(
		HttpServletRequest request,
		String key) {
		removeBean(request, PageContext.APPLICATION_SCOPE, key);
	}

	public static void removeBeanFromRequest(
		HttpServletRequest request,
		String key) {
		removeBean(request, PageContext.REQUEST_SCOPE, key);
	}

	public static void addBean(
		HttpServletRequest request,
		int scope,
		String key,
		Object value) {

		switch (scope) {
			case PageContext.APPLICATION_SCOPE :
				if (logger!=null)
					logger.debug("addBean " + key + " to APPLICATION_SCOPE, value=" + value);
				request.getSession().getServletContext().setAttribute(key, value);
			break;
			case PageContext.SESSION_SCOPE :
				if (logger!=null)
					logger.debug("addBean " + key + " to SESSION_SCOPE, value=" + value);
				request.getSession().setAttribute(key, value);
				break;
			case PageContext.REQUEST_SCOPE :
				if (logger!=null)
					logger.debug("addBean " + key + " to REQUEST_SCOPE, value=" + value);
				request.setAttribute(key, value);
				break;

			default :
				throw new RuntimeException("Unknown scope:" + scope);
		}
	}

	public static Object getBean(HttpServletRequest request,	int scope, String key) {
		switch (scope) {
			case PageContext.APPLICATION_SCOPE :
					return request.getSession().getServletContext().getAttribute(key);
			case PageContext.SESSION_SCOPE :
				return request.getSession().getAttribute(key);
			case PageContext.REQUEST_SCOPE :
				return request.getAttribute(key);
			default :
				throw new RuntimeException("Unknown scope:" + scope);
		}
	}

	public static void removeBean(HttpServletRequest request, int scope, String key) {
		switch (scope) {
			case PageContext.APPLICATION_SCOPE :
				request.getSession().getServletContext().removeAttribute(key);
				break;

			case PageContext.SESSION_SCOPE :
				request.getSession().removeAttribute(key);
				break;

			case PageContext.REQUEST_SCOPE :
				request.removeAttribute(key);
				break;

			default :
				throw new RuntimeException("Unknown scope:" + scope);
		}
	}

	public static void addBeanToSessionSafe(HttpServletRequest request, String key, Object value) {
		addBeanToSessionUnsafe(request, key, value);
		/*
		HttpSession session = request.getSession();
		if (logger!=null)
			logger.debug("addBean " + key + " to SESSION_SCOPE, value=" + value);
		synchronized(session){
			session.setAttribute(key, value);
		}*/
	}
	
	public static void removeBeanFromSessionSafe(HttpServletRequest request, String key){
		removeBeanFromSessionUnsafe(request, key);
		/*
		HttpSession session = request.getSession();
		if (logger!=null)
			logger.debug("removeBean " + key + " from SESSION_SCOPE");
		synchronized(session){
			session.removeAttribute(key);
		}*/
	}

	public static Object getBeanFromSessionSafe(HttpServletRequest request,	String key) {
		return getBeanFromSessionUnsafe(request, key);
		/*
		HttpSession session = request.getSession();
		Object value = null;
		synchronized(session){
			value = session.getAttribute(key);
		}

		if (logger!=null)
			logger.debug("getBean " + key + " from SESSION_SCOPE: "+value);
		return value;
		*/
	}

	
}
