package net.anotheria.webutils.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.anotheria.webutils.service.XMLUserManager;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.struts.action.ActionServlet;


/**
 * @author Leon Rosenberg
 */
public class ControllerServlet extends ActionServlet {

	private static Logger log;
	
	static {
		log = Logger.getLogger(ControllerServlet.class);
		log.debug("Logger initialized.");
	}
	
	/**
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init(ServletConfig config) throws ServletException {
		if (log.isEnabledFor(Priority.INFO)) {
			log.info("initializing ServletController");
		}

		super.init(config);

		String path = config.getServletContext().getRealPath("WEB-INF");
		File f = new File(path+"/appdata/"+"users.xml");
		XMLUserManager.init(f);

		if (log.isEnabledFor(Priority.INFO)) {
			log.info("ready initializing ServletController");
		}
	}

	/**
	 * @see javax.servlet.http.HttpServlet#getLastModified(javax.servlet.http.HttpServletRequest)
	 */
	protected long getLastModified(HttpServletRequest arg0) {
		return System.currentTimeMillis();
	}
}
