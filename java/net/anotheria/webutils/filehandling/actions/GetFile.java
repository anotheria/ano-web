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
public class GetFile extends BaseFileHandlingAction{
	


	/* (non-Javadoc)
	 * @see net.anotheria.webutils.actions.BaseAction#doExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(
		ActionMapping mapping,
		FormBean af,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {

		String name = getStringParameter(req, "pName");
		
		if (name!=null && name.startsWith("../"))
			throw new RuntimeException("Not allowed!");

		TemporaryFileHolder h = FileStorage.loadFile(name);
		if(h == null){
//			throw new RuntimeException("Could not load file with name '" + name + "'!");
			log.warn("Could not load file with name '" + name + "'!");
			return null;
		}
		byte data[] = h.getData();
		
		String mimeType = h.getMimeType(); 
		if (mimeType!=null)
			res.setContentType(mimeType);
		if (data!=null)
			res.setContentLength(data.length);
		
		res.getOutputStream().write(data);
		res.getOutputStream().flush();

		return null;
	}

}


