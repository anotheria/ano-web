package net.anotheria.webutils.filehandling.actions;

import net.anotheria.util.StringUtils;
import net.anotheria.webutils.filehandling.FileStorageConfig;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.filehandling.storage.IStorage;
import net.anotheria.webutils.filehandling.storage.StorageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

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
     * {@link IStorage} instance.
     */
    private static final IStorage storage;

    /**
     * Static initialization block.
     */
    static {
        log = LoggerFactory.getLogger(FileStorage.class);
        fileStorageDir = config.getDirectory();
        storage = StorageFactory.createStorage(config.getStorageType(), config.getBucketName(), config.getCredentialsPath(),
                config.getProjectId(), config.getAccessKey(), config.getSecretKey());
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
        try {
            TemporaryFileHolder fileHolder = (key == null) ? getTemporaryFile(req) : getTemporaryFile(req, key);
            log.debug("trying to store(): " + fileStorageDir + File.separator + name);
            storage.storeFile(fileHolder.getData(), fileStorageDir + File.separator + name);
        } catch (Exception e) {
            log.error("storeFilePermanently", e);
            throw new RuntimeException("FileStorageFailed: " + e.getMessage());
        }
    }

    /**
     * Makes copy of source file on file system, with new generated file name but same extension.
     *
     * @param fileName source file to clone
     * @return new file name
     */
    public static String cloneFilePermanently(String fileName) {
        String sourceFilePath = fileStorageDir + File.separator + fileName;
        if (StringUtils.isEmpty(fileName) || storage.isFileExists(sourceFilePath))
            return "";

        try {
            String generateFileName = generateFileName(fileName);
            String destinationFilePath = fileStorageDir + File.separator + generateFileName;
            storage.cloneFile(sourceFilePath, destinationFilePath);
            return generateFileName;
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
            storage.removeFile(fileStorageDir + File.separator + name);
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

        try {
            return storage.loadFile(fileStorageDir, name);
        } catch (Exception e) {
            log.warn("loadFile()", e);
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
        return storage.getFile(fileStorageDir, name);
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
        for (int i = 1; storage.isFileExists(fileStorageDir + File.separator + fileName); i++)
            fileName = filePrefix + i + ext;

        return fileName;
    }

    public static void main(String[] args) {
        System.out.println(generateFileName("test.jpg"));
        System.out.println(generateFileName("test.txt"));
        System.out.println(generateFileName("test.1.txt"));
    }
}

