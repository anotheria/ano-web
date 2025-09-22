package net.anotheria.webutils.filehandling.actions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;


/**
 * @author lrosenberg
 */
public class GetFile extends BaseFileHandlingAction{

	/* (non-Javadoc)
	 * @see net.anotheria.webutils.actions.BaseAction#doExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionCommand execute(
		ActionMapping mapping,
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
		if (data!=null){
			res.setContentLength(data.length);
			res.getOutputStream().write(data);
		}
		res.getOutputStream().flush();

		return null;
	}

}


