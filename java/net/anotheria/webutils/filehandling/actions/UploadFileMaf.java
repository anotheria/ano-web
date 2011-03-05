package net.anotheria.webutils.filehandling.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.IOUtils;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.filehandling.beans.UploadFileBean;



import com.oreilly.servlet.MultipartRequest;

/**
 * Uploads a file and saves it to the session temporary.
 */
public class UploadFileMaf extends BaseFileHandlingMafAction{

	//private static int MAX_FILE = 254*1024;
	private static int MAX_FILE = 20000*1024;
	private static String TEMP_DIR = "/tmp/";
	private static String FILE = "file";
	
	
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
		MultipartRequest mpreq = new MultipartRequest(req, TEMP_DIR, MAX_FILE);
				
		try{	
			log.debug("have the request...");
			File file = mpreq.getFile(FILE);
			String name = mpreq.getFilesystemName(FILE);
			if(file==null)
				throw new RuntimeException("Uploaded empty file!");
			if(!validateFile(FILE,mpreq)){
				throw new RuntimeException("Uploaded file is not valid!");
			}
			
			byte[] data = new byte[(int)file.length()];
			
			log.debug("file laenge: " + data.length);
			InputStream fIn = null;
			
			try{
				fIn = new FileInputStream(file);
				fIn.read(data);
			}finally{
				IOUtils.closeIgnoringException(fIn);
			}
			
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

		} catch(Exception ex){
			log.error("Error while file upploading: ",ex);
			throw new RuntimeException("Error while file upploading. Check logs for details!");
		}
	}

	private boolean validateFile(String filename, MultipartRequest req) {
		return true;
	}
}
