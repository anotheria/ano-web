/*
 * Created on 02.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.webutils.filehandling.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.filehandling.beans.UploadFileBean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowFile extends BaseFileHandlingAction{

	/* (non-Javadoc)
	 * @see net.anotheria.webutils.actions.BaseAction#doExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doExecute(
		ActionMapping mapping,
		ActionForm af,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
		
		UploadFileBean bean = new UploadFileBean();

		TemporaryFileHolder holder = getTemporaryFile(req);
		if (holder==null){
			bean.setName("Noch kein File.");
			bean.setSize(makeSizeString(0));
			bean.setMessage("Warte aufs File.");
			bean.setFilePresent(false);
		}else{
			bean.setName(holder.getFileName());
			bean.setSize(makeSizeString(holder.getSize()));
			bean.setMessage("File hochgeladen");
			bean.setFilePresent(true);
		}
		
		
		addBeanToSession(req, IFilesConstants.BEAN_FILE, bean);
		
		return mapping.findForward("success");
	}

}
