package net.anotheria.webutils.filehandling.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.IOUtils;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.filehandling.beans.UploadFileBean;

/**
 * Uploads a file and saves it to the session temporary.
 */
public class FileAjaxUpload extends BaseFileHandlingAction{

	public ActionForward execute(ActionMapping mapping, FormBean form, HttpServletRequest req, HttpServletResponse res) throws Exception {

		UploadFileBean fileBean = new UploadFileBean ();

		upload(mapping,form,req,res,fileBean); 
		addBeanToSession(req, IFilesConstants.BEAN_FILE, fileBean);

		return mapping.findForward("success");

	}

	/**
	 * Uploads the file.
	 * @param mapping
	 * @param form
	 * @param req
	 * @param res
	 * @param fileBean
	 * @return
	 * @throws IOException
	 */	
	private void upload(ActionMapping mapping, FormBean form, HttpServletRequest req, HttpServletResponse res, UploadFileBean fileBean) throws IOException{
		log.debug("trying uploading file....");
		
        PrintWriter writer = null;
        InputStream is = null;

        try {
            writer = res.getWriter();
        } catch (IOException ex) {
        	log.error("Error while file upploading: ",ex);
			throw new RuntimeException("Error while file upploading. Check logs for details!");
        }
				
		try{	
			log.debug("have the request...");
			
			String name = req.getHeader("X-File-Name");
			is = req.getInputStream();			
			if(is==null)
				throw new RuntimeException("Uploaded empty file!");
			
			
			byte[] data = IOUtils.readBytes(is);
			
			TemporaryFileHolder fileHolder = new TemporaryFileHolder();
			fileHolder.setData(data);
			fileHolder.setFileName(name);			
			
			//now store it
			String propertyName = req.getParameter("property");
			if(propertyName == null)
				storeTemporaryFile(req, fileHolder);
			else
				storeTemporaryFile(req, fileHolder, propertyName);
			fileBean.setName(name);
			fileBean.setSize(makeSizeString(data.length));
			fileBean.setMessage("Erfolgreich gespeichert.");
			fileBean.setFilePresent(true); 
			fileBean.setValid(true);
			
			writer.print("{success: true}");
		} catch(Exception ex){
			log.error("Error while file upploading: ",ex);
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.print("{success: false}");
		} finally {
			IOUtils.closeIgnoringException(is);
		}
	}

}
