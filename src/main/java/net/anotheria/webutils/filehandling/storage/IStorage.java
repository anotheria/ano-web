package net.anotheria.webutils.filehandling.storage;

import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Storage interface.
 *
 * @author asamoilich.
 */
public interface IStorage {
    /**
     * Store file.
     *
     * @param fileContent file content
     * @param filePath    file path
     * @throws Exception
     */
    void storeFile(byte[] fileContent, String filePath) throws Exception;

    /**
     * Return {@code true} if file exists, {@code false} otherwise.
     *
     * @param filePath file path
     * @return boolean flag
     */
    boolean isFileExists(String filePath);

    /**
     * Clone file.
     *
     * @param sourceFilePath      source file path
     * @param destinationFilePath destination file path
     * @throws Exception
     */
    void cloneFile(String sourceFilePath, String destinationFilePath) throws Exception;

    /**
     * Remove file.
     *
     * @param filePath file path
     * @throws Exception
     */
    void removeFile(String filePath) throws Exception;

    /**
     * Load file.
     *
     * @param fileStorageDir file storage dir
     * @param fileName       file name
     * @return {@link TemporaryFileHolder}
     * @throws Exception
     */
    TemporaryFileHolder loadFile(String fileStorageDir, String fileName) throws Exception;

    /**
     * Get file.
     *
     * @param fileStorageDir file storage dir
     * @param fileName       file name
     * @return {@link File}
     * @throws FileNotFoundException
     */
    File getFile(String fileStorageDir, String fileName) throws FileNotFoundException;
}
