package net.anotheria.webutils.stats;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * This is a standalone utility for counting active sessions in the 
 * engine context. It's loaded via web.xml.
 * @author another
 */
public class SessionCounter implements HttpSessionListener{
	
	private static long sessionCount;
	private static long maxSessionCount;
	
	static Logger log;
	
	static{
		sessionCount = 0;
		maxSessionCount = 0;
		log = Logger.getLogger(SessionCounter.class);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		sessionCount++;
		if (sessionCount>maxSessionCount)
			maxSessionCount = sessionCount;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		if (sessionCount>0)
			sessionCount--;
		else
			log.error("Number of session is less then zero! "+sessionCount);
	}
	
	public static final long getSessionCount(){
		return sessionCount;
	}

	public static final long getMaxSessionCount(){
		return maxSessionCount;
	}

}
