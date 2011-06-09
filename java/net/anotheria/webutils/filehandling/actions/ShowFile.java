package net.anotheria.webutils.filehandling.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.filehandling.beans.UploadFileBean;




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
	public ActionForward execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		UploadFileBean filebean = new UploadFileBean();

		TemporaryFileHolder holder = getTemporaryFile(req);
		if (holder==null){
			filebean.setName("Noch kein File.");
			filebean.setSize(makeSizeString(0));
			filebean.setMessage("Warte aufs File.");
			filebean.setFilePresent(false);
		}else{
			filebean.setName(holder.getFileName());
			filebean.setSize(makeSizeString(holder.getSize()));
			filebean.setMessage("File hochgeladen");
			filebean.setFilePresent(true);
		}
		
		
		addBeanToSession(req, IFilesConstants.BEAN_FILE, filebean);
		
		return mapping.findForward("success");
	}

}
