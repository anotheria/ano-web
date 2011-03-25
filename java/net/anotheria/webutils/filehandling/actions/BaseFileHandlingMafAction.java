package net.anotheria.webutils.filehandling.actions;

import javax.servlet.http.HttpServletRequest;

import net.anotheria.webutils.actions.BaseMafAction;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class BaseFileHandlingMafAction extends BaseMafAction{

	//temporaer in die session, dann auf filesystem

	/**
	 * Stores a file temporarly in the session.
	 */
	protected void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder){
		FileStorage.storeTemporaryFile(req, fileHolder);
	}
	/**
	 * Stores a file temporarly in the session.
	 */
	protected void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder, String key){
		FileStorage.storeTemporaryFile(req, fileHolder, key);
	}
	
	/**
	 * Returns the temporarly file from the session.
	 * @param req HttpServletRequest to obtain the session from.
	 * @return previously stored tmp file (if any).
	 */
	protected TemporaryFileHolder getTemporaryFile(HttpServletRequest req){
		return FileStorage.getTemporaryFile(req);
	}
	
	/**
	 * Returns the temporarly file from the session.
	 * @param req HttpServletRequest to obtain the session from.
	 * @param key attribute name suffix to be used for storing in session
	 * @return previously stored tmp file (if any).
	 */
	protected TemporaryFileHolder getTemporaryFile(HttpServletRequest req, String key){
		return FileStorage.getTemporaryFile(req, key);
	}
	
	/**
	 * Removes the temporary saved file from the session.
	 * @param req servlet request.
	 */
	protected void removeTemporaryFile(HttpServletRequest req){
		FileStorage.removeTemporaryFile(req);
	}
	
	/**
	 * Removes the temporary saved file from the session.
	 * @param key attribute name suffix to be used for removing in session
	 * @param req servlet request.
	 */
	protected void removeTemporaryFile(HttpServletRequest req, String key){
		FileStorage.removeTemporaryFile(req, key);
	}

}
