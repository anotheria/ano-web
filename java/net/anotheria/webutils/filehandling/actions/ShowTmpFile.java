/*
 * Created on 03.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.webutils.filehandling.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowTmpFile extends BaseFileHandlingAction{

	/* (non-Javadoc)
	 * @see net.anotheria.webutils.actions.BaseAction#doExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doExecute(
		ActionMapping mapping,
		ActionForm af,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {

		TemporaryFileHolder h = getTemporaryFile(req);
		byte data[] = h.getData();
		
		res.getOutputStream().write(data);
		res.getOutputStream().flush();

		return null;
	}

}
