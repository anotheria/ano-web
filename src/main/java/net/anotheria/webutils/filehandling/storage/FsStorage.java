package net.anotheria.webutils.filehandling.storage;

import net.anotheria.util.IOUtils;
import net.anotheria.util.io.CopyDirContents;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * File system storage.
 *
 * @author asamoilich.
 */
public class FsStorage implements IStorage {

    /**
     * FileStorage 'fileStorageDir'.
     */
    public String fileStorageDir;

    /**
     * Directory for file storage.
     *
     * @param fileStorageDir
     */
    public FsStorage(String fileStorageDir) {
        this.fileStorageDir = fileStorageDir;
    }

    @Override
    public void storeFile(byte[] fileContent, String fileName) throws Exception {
        String filePath = fileStorageDir + File.separator + fileName;
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(filePath);
            fOut.write(fileContent);
        } finally {
            IOUtils.closeIgnoringException(fOut);
        }
    }

    @Override
    public boolean isFileExists(String fileName) {
        String filePath = fileStorageDir + File.separator + fileName;
        return new File(filePath).exists();
    }

    @Override
    public void cloneFile(String sourceFileName, String destinationFileName) throws Exception {
        File sourceFile = new File(fileStorageDir + File.separator +sourceFileName);
        File destinationFile = new File(fileStorageDir + File.separator +destinationFileName);

        if (sourceFile.exists())
            CopyDirContents.copy(sourceFile, destinationFile);
    }

    @Override
    public void removeFile(String fileName) throws Exception {
        File file = new File(fileStorageDir + File.separator + fileName);
        file.delete();
    }

    @Override
    public TemporaryFileHolder loadFile(String fileName) throws Exception {
        FileInputStream fIn = null;
        try {
            File file = new File(fileStorageDir + File.separator + fileName);
            fIn = new FileInputStream(file);
            byte[] data = new byte[fIn.available()];
            fIn.read(data);
            TemporaryFileHolder f = new TemporaryFileHolder();
            f.setData(data);
            f.setFileName(fileName);
            f.setLastModified(file.lastModified());
            return f;
        } finally {
            IOUtils.closeIgnoringException(fIn);
        }
    }

    @Override
    public File getFile(String fileName) throws FileNotFoundException {
        File file = new File(fileStorageDir + File.separator + fileName);
        if (file.exists() && !file.isDirectory())
            return file;
        throw new FileNotFoundException(fileName);
    }
}
