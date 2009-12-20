package net.anotheria.webutils.filehandling.actions;

import javax.servlet.http.HttpServletRequest;

import net.anotheria.webutils.actions.BaseAction;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class BaseFileHandlingAction extends BaseAction{

	//temporaer in die session, dann auf filesystem

	/**
	 * Stores a file temporarly in the session.
	 */
	protected void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder){
		FileStorage.storeTemporaryFile(req, fileHolder);
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
	 * Removes the temporary saved file from the session.
	 * @param req servlet request.
	 */
	protected void removeTemporaryFile(HttpServletRequest req){
		FileStorage.removeTemporaryFile(req);
	}

}
