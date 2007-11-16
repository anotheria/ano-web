package net.anotheria.webutils.filehandling.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.filehandling.beans.UploadFileBean;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.oreilly.servlet.MultipartRequest;

/**
 * Uploads a file and saves it to the session temporary.
 */
public class UploadFile extends BaseFileHandlingAction{

	//private static int MAX_FILE = 254*1024;
	private static int MAX_FILE = 20000*1024;
	private static String TEMP_DIR = "/tmp/";
	private static String FILE = "file";
	
	
	/**
	 * @see biz.beaglesoft.rb.gmw.shared.actions.BaseAction#doExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws Exception {

		UploadFileBean fileBean = new UploadFileBean ();
		ActionErrors err = null;

		try{
			err = upload(mapping,form,req,res,fileBean); 
		} catch (IOException e) {
			log.debug("upload",e);
			res.sendError(1);
			return null;
		}
		addBeanToSession(req, IFilesConstants.BEAN_FILE, fileBean);
		addErrors(req, err);

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
	private ActionErrors upload(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res, UploadFileBean fileBean) throws IOException{
		ActionErrors err = new ActionErrors();
		log.debug("trying uploading file....");
		MultipartRequest mpreq = new MultipartRequest(req, TEMP_DIR, MAX_FILE);
				
		try{	
			log.debug("have the request...");
			File file = mpreq.getFile(FILE);
			String name = mpreq.getFilesystemName(FILE);
			if(file==null){
				err.add("ds.uploadbadge.error.upload",new ActionError("ds.uploadbadge.error.upload"));			
				return err;
			}
			if(!validateFile(FILE,mpreq)){
				err.add("ds.uploadbadge.error.mime",new ActionError("ds.uploadbadge.error.mime",name));		
				return err;
			}
			
			byte[] data = new byte[(int)file.length()];
			
			log.debug("file laenge: " + data.length);
			new FileInputStream(file).read(data);
			
			//hier ein temporaeres file speichern
			TemporaryFileHolder fileHolder = new TemporaryFileHolder();
			fileHolder.setData(data);
			fileHolder.setMimeType(mpreq.getContentType(FILE));
			fileHolder.setFileName(name);			
			
			//now store it
			storeTemporaryFile(req, fileHolder);

//			image.setHeight("32");
//			image.setWidth("32");
			fileBean.setName(name);
			fileBean.setSize(makeSizeString(data.length));
			fileBean.setMessage("Erfolgreich gespeichert.");
			fileBean.setFilePresent(true); 
//			image.setId(fileId);
			
			fileBean.setValid(true);
			return err;

		} catch(Exception ex){
			log.error("upload",ex);
			err.add("ds.error.system",new ActionError("ds.error.system"));		
			return err;
		}
	}

	private boolean validateFile(String filename, MultipartRequest req) {
		return true;
	}
}
