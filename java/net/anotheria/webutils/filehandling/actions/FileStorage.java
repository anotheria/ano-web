/*
 * Created on 03.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.webutils.filehandling.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.Set;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FileStorage {
	
	static Logger log;
	public static String fileStorageDir;
	
	public static final String DEF_FILE_STORAGE_DIR = "/work/data/files/";

	static{
		log = Logger.getLogger(FileStorage.class);
		fileStorageDir = DEF_FILE_STORAGE_DIR;
		try{
			fileStorageDir = ConfigurationManager.INSTANCE.getConfiguration("filestorage").getAttribute("directory");
		}catch(Exception e){
			log.error("reading fileStorageDir", e);
		}
	}
	
	public static void setFileStorageDir(String dir){
		fileStorageDir = dir;
	}
	
	/**
	 * Stores a file temporarly in the session.
	 */
	public static void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder){
		req.getSession().setAttribute(IFilesConstants.BEAN_TMP_FILE, fileHolder);
	}
	
	/**
	 * Returns the temporarly file from the session.
	 * @param req HttpServletRequest to obtain the session from.
	 * @return previously stored tmp file (if any).
	 */
	public static TemporaryFileHolder getTemporaryFile(HttpServletRequest req){
		return (TemporaryFileHolder)req.getSession().getAttribute(IFilesConstants.BEAN_TMP_FILE);
	}
	
	/**
	 * Removes the temporary saved file from the session.
	 * @param req servlet request.
	 */
	public static void removeTemporaryFile(HttpServletRequest req){
		req.getSession().removeAttribute(IFilesConstants.BEAN_TMP_FILE);
	}
	
	public static void storeFilePermanently(HttpServletRequest req, String name){
		try{
			TemporaryFileHolder storage = getTemporaryFile(req);
			FileOutputStream fOut = new FileOutputStream(fileStorageDir+"/"+name);
			System.out.println("trying to store: "+fileStorageDir+"/"+name);
			fOut.write(storage.getData());
			fOut.close(); 
		}catch(Exception e){
			log.error("storeFilePermanently", e);
			throw new RuntimeException("FileStorageFailed: "+e.getMessage());
		}
	}
	
	public static void loadTemporaryFile(HttpServletRequest req, String name){
		storeTemporaryFile(req, loadFile(name));
	}

	public static TemporaryFileHolder loadFile(String name){
		try{
			File file = new File(fileStorageDir+"/"+name);
			FileInputStream fIn = new FileInputStream(file);
			byte[] data = new byte[fIn.available()];
			fIn.read(data); 
			fIn.close();
			TemporaryFileHolder f = new TemporaryFileHolder();
			f.setData(data);
			f.setFileName(name);
			f.setLastModified(file.lastModified());
			return f;
		}catch(Exception e){
			log.error("getImage", e);
		}
		return null;
	}
	
}

@ConfigureMe(name="filestorage")
class FileStorageConfigurable{


	@Set("directory") public void setDirectory(String value){
		FileStorage.setFileStorageDir(value);		
	}
}
