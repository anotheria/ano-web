package net.anotheria.webutils.filehandling.actions;

import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;
import net.anotheria.util.io.CopyDirContents;
import net.anotheria.webutils.filehandling.FileStorageConfig;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileStorage {
    /**
     * FileStorage 'log'.
     */
    static Logger log;
    /**
     * FileStorage 'fileStorageDir'.
     */
    public static String fileStorageDir;

    /**
     * FileStorage config.
     * Actually config as instance of {@link FileStorageConfig}.
     */
    private static final FileStorageConfig config = FileStorageConfig.getInstance();

    /**
     * Static initialization block.
     */
    static {
        log = LoggerFactory.getLogger(FileStorage.class);
        fileStorageDir = config.getDirectory();
    }

    public static void setFileStorageDir(String dir) {
        fileStorageDir = dir;
    }

    /**
     * Stores a file temporarily in the session.
     */
    public static void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder) {
        req.getSession().setAttribute(IFilesConstants.BEAN_TMP_FILE, fileHolder);
    }

    /**
     * Stores a file temporarily in the session under the specified key.
     * Use of this method allows to store multiple temporary files in session.
     *
     * @param key attribute suffix to use for storing in session
     */
    public static void storeTemporaryFile(HttpServletRequest req, TemporaryFileHolder fileHolder, String key) {
        req.getSession().setAttribute(IFilesConstants.BEAN_TMP_FILE + "." + key, fileHolder);
    }

    /**
     * Returns the temporary file from the session.
     *
     * @param req HttpServletRequest to obtain the session from.
     * @return previously stored tmp file (if any).
     */
    public static TemporaryFileHolder getTemporaryFile(HttpServletRequest req) {
        return (TemporaryFileHolder) req.getSession().getAttribute(IFilesConstants.BEAN_TMP_FILE);
    }

    /**
     * Returns the temporary file from the session.
     *
     * @param req HttpServletRequest to obtain the session from.
     * @param key attribute suffix that was used to store attribute in session
     * @return previously stored tmp file (if any).
     */
    public static TemporaryFileHolder getTemporaryFile(HttpServletRequest req, String key) {
        return (TemporaryFileHolder) req.getSession().getAttribute(IFilesConstants.BEAN_TMP_FILE + "." + key);
    }

    /**
     * Removes the temporary saved file from the session.
     *
     * @param req servlet request.
     */
    public static void removeTemporaryFile(HttpServletRequest req) {
        req.getSession().removeAttribute(IFilesConstants.BEAN_TMP_FILE);
    }

    /**
     * Removes the temporary saved file from the session.
     *
     * @param req servlet request.
     * @param key attribute suffix
     */
    public static void removeTemporaryFile(HttpServletRequest req, String key) {
        req.getSession().removeAttribute(IFilesConstants.BEAN_TMP_FILE + "." + key);
    }

    public static void storeFilePermanently(HttpServletRequest req, String name) {
        storeFilePermanently(req, name, null);
    }

    public static void storeFilePermanently(HttpServletRequest req, String name, String key) {
        FileOutputStream fOut = null;
        try {
            TemporaryFileHolder storage = (key == null) ? getTemporaryFile(req) : getTemporaryFile(req, key);
            fOut = new FileOutputStream(fileStorageDir + File.separator + name);
            log.debug("trying to store(): " + fileStorageDir + File.separator + name);
            fOut.write(storage.getData());
        } catch (Exception e) {
            log.error("storeFilePermanently", e);
            throw new RuntimeException("FileStorageFailed: " + e.getMessage());
        } finally {
            IOUtils.closeIgnoringException(fOut);
        }
    }

    /**
     * Return true if file with given name is exists, false otherwise.
     * @param name file name
     * @return boolean value
     */
    private static boolean isFileExists(String name) {
        return new File(fileStorageDir + File.separator + name).exists();
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
    public static void removeFilePermanently(String name) {
        if (StringUtils.isEmpty(name))
            return;

        try {
            log.debug("trying to remove: " + fileStorageDir + File.separator + name);
            File file = new File(fileStorageDir + File.separator + name);

            file.delete();
        } catch (Exception e) {
            log.error("removeFilePermanently()", e);
            throw new RuntimeException("FileStorageFailed: " + e.getMessage());
        }
    }

    public static void loadTemporaryFile(HttpServletRequest req, String name) {
        storeTemporaryFile(req, loadFile(name));
    }

    public static TemporaryFileHolder loadFile(String name) {
        if (StringUtils.isEmpty(name))
            return null;

        File file;
        FileInputStream fIn = null;
        try {
            file = new File(fileStorageDir + File.separator + name);
            fIn = new FileInputStream(file);
            byte[] data = new byte[fIn.available()];
            fIn.read(data);
            TemporaryFileHolder f = new TemporaryFileHolder();
            f.setData(data);
            f.setFileName(name);
            f.setLastModified(file.lastModified());
            return f;
        } catch (Exception e) {
            log.warn("loadFile()", e);
        } finally {
            IOUtils.closeIgnoringException(fIn);
        }
        return null;
    }

    /**
     * Return {@link File} with selected  name if such exists.
     *
     * @param name file name
     * @return {@link File}
     * @throws java.io.FileNotFoundException if  file does not exists
     */
    public static File getFile(String name) throws FileNotFoundException {
        if (StringUtils.isEmpty(name))
            return null;
        File file = new File(fileStorageDir + File.separator + name);
        if (file.exists() && !file.isDirectory())
            return file;
        throw new FileNotFoundException(name);
    }

    /**
     * Generates file name and appends it with given file extension.
     *
     * @param fileName original file name
     * @return generated file name
     */
    public static String generateFileName(String fileName) {
        int extIndex = fileName.lastIndexOf(".");

        String filePrefix = fileName.substring(0, extIndex);
        String ext = fileName.substring(extIndex);

        // substitute special characters with underscore
        filePrefix = StringUtils.normalize(filePrefix);

        fileName = filePrefix + ext;

        // if file with such name already exist - append index
        for (int i = 1; isFileExists(fileName); i++)
            fileName = filePrefix + i + ext;

        return fileName;
    }

    public static void main(String[] args) {
        System.out.println(isFileExists("test.txt"));
        System.out.println(generateFileName("test.jpg"));
        System.out.println(generateFileName("test.txt"));
        System.out.println(generateFileName("test.1.txt"));
    }
}

