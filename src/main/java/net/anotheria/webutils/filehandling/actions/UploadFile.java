package net.anotheria.webutils.filehandling.actions;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.filehandling.beans.UploadFileBean;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Uploads a file and saves it to the session temporary.
 */
@MultipartConfig
public class UploadFile extends BaseFileHandlingAction{

	//private static int MAX_FILE = 254*1024;
	private static int MAX_FILE = 20000*1024;
	private static String TEMP_DIR = "/tmp/";
	private static String FILE = "file";
	
	
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		UploadFileBean fileBean = new UploadFileBean ();

		upload(mapping,req,res,fileBean);
		addBeanToSession(req, IFilesConstants.BEAN_FILE, fileBean);

		return mapping.success();

	}

	/**
	 * Uploads the file.
	 * @param mapping
	 * @param req
	 * @param res
	 * @param fileBean
	 * @return
	 * @throws IOException
	 */
	private void upload(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res, UploadFileBean fileBean) throws IOException{
		log.debug("trying uploading file....");
		try{
			log.debug("have the request...");
			Part filePart = req.getPart(FILE);
			String fileName = filePart.getSubmittedFileName();
			if(fileName == null || fileName.isEmpty()) {
				throw new RuntimeException("Uploaded empty file!");
			}
			if(!validateFile(FILE, filePart)){
				throw new RuntimeException("Uploaded file is not valid!");
			}

			InputStream fileContent = filePart.getInputStream();
			byte[] data = IOUtils.toByteArray(fileContent);

			//hier ein temporaeres file speichern
			fileName = FileStorage.generateFileName(fileName);

			TemporaryFileHolder fileHolder = new TemporaryFileHolder();
			fileHolder.setData(data);
			fileHolder.setMimeType(filePart.getContentType());
			fileHolder.setFileName(fileName);
			//now store it
			storeTemporaryFile(req, fileHolder);

			fileBean.setName(fileName);
			fileBean.setSize(makeSizeString(data.length));
			fileBean.setMessage("Erfolgreich gespeichert.");
			fileBean.setFilePresent(true);
			fileBean.setValid(true);

		} catch(Exception ex){
			log.error("Error while file uploading: ",ex);
			throw new RuntimeException("Error while file uploading. Check logs for details!");
		}
	}

	private boolean validateFile(String filename, Part filePart) {
		return true;
	}

}
