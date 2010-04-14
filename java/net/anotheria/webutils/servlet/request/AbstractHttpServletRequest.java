package net.anotheria.webutils.servlet.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author: h3llka
 */
public class AbstractHttpServletRequest implements HttpServletRequest {
	private String servletPath;
	private String pathInfo;
	private HttpSession session;

	public AbstractHttpServletRequest() {
		this.session = new HttpSession() {

			private Map<String, Object> sessionMap = new HashMap<String, Object>();

			@Override
			public void setMaxInactiveInterval(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setAttribute(String arg0, Object arg1) {
				sessionMap.put(arg0, arg1);
			}

			@Override
			public void removeValue(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void removeAttribute(String arg0) {
				sessionMap.remove(arg0);

			}

			@Override
			public void putValue(String arg0, Object arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isNew() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void invalidate() {
				// TODO Auto-generated method stub

			}

			@Override
			public String[] getValueNames() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getValue(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public HttpSessionContext getSessionContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getMaxInactiveInterval() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getLastAccessedTime() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return UUID.randomUUID().toString();
			}

			@Override
			public long getCreationTime() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Enumeration<String> getAttributeNames() {
				final Iterator<String> it = sessionMap.keySet().iterator();
				Enumeration<String> enumer = new Enumeration<String>() {
					@Override
					public boolean hasMoreElements() {
						return it.hasNext();
					}

					@Override
					public String nextElement() {
						return it.next();
					}

				};

				return enumer;
			}

			@Override
			public Object getAttribute(String arg0) {
				return sessionMap.get(arg0);
			}
		};
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getAttribute(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Enumeration getAttributeNames() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getCharacterEncoding() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public int getContentLength() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getContentType() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getParameter(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Enumeration getParameterNames() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String[] getParameterValues(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Map getParameterMap() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getProtocol() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getScheme() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getServerName() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public int getServerPort() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public BufferedReader getReader() throws IOException {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getRemoteAddr() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getRemoteHost() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public void setAttribute(String s, Object o) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public void removeAttribute(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Locale getLocale() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Enumeration getLocales() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public boolean isSecure() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getRealPath(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getAuthType() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Cookie[] getCookies() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public long getDateHeader(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getHeader(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Enumeration getHeaders(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Enumeration getHeaderNames() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public int getIntHeader(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getMethod() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getPathInfo() {
		return pathInfo;
	}

	@Override
	public String getPathTranslated() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getContextPath() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getQueryString() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getRemoteUser() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public boolean isUserInRole(String s) {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public Principal getUserPrincipal() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getRequestedSessionId() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getRequestURI() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public StringBuffer getRequestURL() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	@Override
	public HttpSession getSession(boolean b) {
		return session;
	}

	@Override
	public HttpSession getSession() {
		return session;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		throw new UnsupportedOperationException("Implement me please!");
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		throw new UnsupportedOperationException("Implement me please!");
	}
}