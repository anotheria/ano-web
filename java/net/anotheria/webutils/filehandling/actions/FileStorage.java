package net.anotheria.webutils.filehandling.actions;

import net.anotheria.util.IOUtils;
import net.anotheria.util.IdCodeGenerator;
import net.anotheria.util.StringUtils;
import net.anotheria.util.io.CopyDirContents;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.Set;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
	 * Stores a file temporarily in the session.
	 */
	public static void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder){
		req.getSession().setAttribute(IFilesConstants.BEAN_TMP_FILE, fileHolder);
	}
	
	/**
	 * Stores a file temporarily in the session under the specified key. 
	 * Use of this method allows to store multiple temporary files in session.
	 * @param key attribute suffix to use for storing in session
	 */
	public static void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder, String key){
		req.getSession().setAttribute(IFilesConstants.BEAN_TMP_FILE+"."+key, fileHolder);
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
	 * Returns the temporarly file from the session.
	 * @param req HttpServletRequest to obtain the session from.
	 * @param key attribute suffix that was used to store attribute in session
	 * @return previously stored tmp file (if any).
	 */
	public static TemporaryFileHolder getTemporaryFile(HttpServletRequest req, String key){
		return (TemporaryFileHolder)req.getSession().getAttribute(IFilesConstants.BEAN_TMP_FILE+"."+key);
	}
	
	/**
	 * Removes the temporary saved file from the session.
	 * @param req servlet request.
	 */
	public static void removeTemporaryFile(HttpServletRequest req){
		req.getSession().removeAttribute(IFilesConstants.BEAN_TMP_FILE);
	}
	
	/**
	 * Removes the temporary saved file from the session.
	 * @param req servlet request.
	 * @param key attribute suffix
	 */
	public static void removeTemporaryFile(HttpServletRequest req, String key){
		req.getSession().removeAttribute(IFilesConstants.BEAN_TMP_FILE+"."+key);
	}
	
	public static void storeFilePermanently(HttpServletRequest req, String name){
		storeFilePermanently(req, name, null);
	}
	
	public static void storeFilePermanently(HttpServletRequest req, String name, String key){
		FileOutputStream fOut = null;
		try{
			TemporaryFileHolder storage = (key == null) ? getTemporaryFile(req) : getTemporaryFile(req, key);
		 	fOut = new FileOutputStream(fileStorageDir + File.separator + name);
			log.debug("trying to store(): " + fileStorageDir + File.separator + name);
			fOut.write(storage.getData());
		}catch(Exception e){
			log.error("storeFilePermanently", e);
			throw new RuntimeException("FileStorageFailed: "+e.getMessage());
		}finally{
			IOUtils.closeIgnoringException(fOut);
		}
	}

	/**
	 * Makes copy of source file on file system, with new generated file name but same extension.
	 *
	 * @param fileName source file to clone
	 * @return new file name
	 */
	public static String cloneFilePermanently(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		try {
			File sourceFile = new File(fileStorageDir + File.separator + fileName);
			File destinationFile = new File(fileStorageDir + File.separator + generateFileName(fileName));

			if (sourceFile.exists())
				CopyDirContents.copy(sourceFile, destinationFile);

			return destinationFile.getName();
		} catch (Exception e) {
			log.error("cloneFilePermanently", e);
			throw new RuntimeException("FileStorageFailed: " + e.getMessage());
		}
	}

	/**
	 * Removes file from file system.
	 *
	 * @param name file name to remove
	 */
	public static void removeFilePermanently(String name){
		if (StringUtils.isEmpty(name))
			return;
		
		try{
			log.debug("trying to remove: " + fileStorageDir + File.separator + name);
			File file = new File(fileStorageDir + File.separator + name);

			file.delete();
		}catch(Exception e){
			log.error("removeFilePermanently()", e);
			throw new RuntimeException("FileStorageFailed: "+e.getMessage());
		}
	}
	
	public static void loadTemporaryFile(HttpServletRequest req, String name){
		storeTemporaryFile(req, loadFile(name));
	}

	public static TemporaryFileHolder loadFile(String name){
		if (StringUtils.isEmpty(name))
			return null;

		File file;
		FileInputStream fIn = null;
		try{
			file = new File(fileStorageDir + File.separator + name);
			fIn = new FileInputStream(file);
			byte[] data = new byte[fIn.available()];
			fIn.read(data); 
			TemporaryFileHolder f = new TemporaryFileHolder();
			f.setData(data);
			f.setFileName(name);
			f.setLastModified(file.lastModified());
			return f;
		}catch(Exception e){
			log.warn("loadFile()", e);
		}finally{
			IOUtils.closeIgnoringException(fIn);
		}
		return null;
	}

	/**
	 * Generates file name and appends it with given file extension.
	 *
	 * @param fileName original file name
	 * @return generated file name
	 */
	public static String generateFileName(String fileName){
		String res = IdCodeGenerator.generateCode();
		int index = fileName.lastIndexOf(".");

		if (index >= 0)
			res += fileName.substring(index);

		return res;
	}

}

@ConfigureMe(name="filestorage")
class FileStorageConfigurable{


	@Set("directory") public void setDirectory(String value){
		FileStorage.setFileStorageDir(value);		
	}
}
