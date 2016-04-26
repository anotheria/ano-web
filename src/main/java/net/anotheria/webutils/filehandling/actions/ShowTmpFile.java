package net.anotheria.webutils.filehandling.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;




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
	public ActionForward execute(
		ActionMapping mapping,
		FormBean af,
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
