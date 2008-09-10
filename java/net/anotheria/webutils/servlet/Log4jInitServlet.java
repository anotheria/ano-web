package net.anotheria.webutils.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.anotheria.util.StringUtils;

import org.apache.log4j.xml.DOMConfigurator;

/** 
 * Usage in web.xml:
 * <pre>
 * <servlet>
 *		<!-- das sollte mal bei gelegenheit in einen contextlistener rein! -->
 *	    <servlet-name>log4j-init</servlet-name>
 *	    <servlet-class>net.anotheria.webutils.servlet.Log4jInitServlet</servlet-class>
 *	
 *	    <init-param>
 *	            <param-name>log4j-init-file</param-name>
 *	            <param-value>WEB-INF/log4j.xml</param-value>
 *	    </init-param>
 *	
 *	    <load-on-startup>1</load-on-startup>
 *	</servlet>
 * </pre>
 * 
 * The param-value may be a comma seperated list of files. The
 * first file found will be used. If file-path starts with a '/'
 * it will be treated as an absolut path otherwise it is relative
 * to the webapp's docPath
 * 
 * Additionally you may specify multiple ${...} where ... is one system.property 
 *  
 */
public class Log4jInitServlet extends HttpServlet {

	public static final String INIT_PARAM = "log4j-init-file";
	
	public void init(ServletConfig config) throws ServletException {
		System.out.println("Initializing Log4jInit-Servlet.");
		
		String prefix =  config.getServletContext().getRealPath("/");
		String log4jInitFileString = config.getInitParameter(INIT_PARAM);
		log4jInitFileString = replaceWithSystemproperty(log4jInitFileString);
		String file = findFile(prefix, log4jInitFileString);
		
		// if the log4j-init-file is not set, then no point in trying
		if(file != null) {
			System.out.println("Log4jInit-Servlet: using file " + file);
			DOMConfigurator.configureAndWatch(prefix+file);
		} 
		else 
			throw new ServletException("Could not access log4j-init-file at "+file);
	}
	
	private String findFile(String prefix, String log4jInitFileString) {
		String[] files = StringUtils.tokenize(log4jInitFileString, ',');
		for(int i=0; i<files.length; i++) {
			String fileName = files[i];
			if(!fileName.startsWith("/")) {
				fileName = prefix + fileName;
			}
			File file = new File(fileName);
			if(file.exists()) {
				return fileName;
			}
		}
		return null;
	}
	
	private String replaceWithSystemproperty(String aString) {
		StringBuilder result = new StringBuilder();
		int dollarPos, endPos = 0;
		String after = aString;
		do {
			dollarPos = aString.indexOf("${", endPos);
			if(dollarPos >= 0) {
				endPos = aString.indexOf('}', dollarPos);
				String before = aString.substring(0,dollarPos);
				String name = aString.substring(dollarPos+2, endPos);
				after = aString.substring(endPos+1);
				result.append(before);
				String value = System.getProperty(name);
				if(value != null) {
					result.append(value);
				}
			} else {
				result.append(after);
			}
		} while(dollarPos >= 0);
		return result.toString();
	}
}
