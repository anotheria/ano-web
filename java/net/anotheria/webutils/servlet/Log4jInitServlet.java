package net.anotheria.webutils.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.xml.DOMConfigurator;

/** 
 * Taken from the log4j manual
 * Created on 14.07.2004 
 */
public class Log4jInitServlet extends HttpServlet {

  public void init(ServletConfig config) throws ServletException {
  	System.out.println("Initializing Log4jInit-Servlet.");
  	String prefix =  config.getServletContext().getRealPath("/");
  	String file = config.getInitParameter("log4j-init-file");
  	// if the log4j-init-file is not set, then no point in trying
		if(file != null) {
			DOMConfigurator.configureAndWatch(prefix+file);
		} 
		else 
			throw new ServletException("Could not access log4j-init-file at "+file);
  }

}
