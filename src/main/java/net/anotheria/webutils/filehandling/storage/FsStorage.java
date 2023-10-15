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

    @Override
    public void storeFile(byte[] fileContent, String filePath) throws Exception {
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(filePath);
            fOut.write(fileContent);
        } finally {
            IOUtils.closeIgnoringException(fOut);
        }
    }

    @Override
    public boolean isFileExists(String filePath) {
        return new File(filePath).exists();
    }

    @Override
    public void cloneFile(String sourceFilePath, String destinationFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);

        if (sourceFile.exists())
            CopyDirContents.copy(sourceFile, destinationFile);
    }

    @Override
    public void removeFile(String filePath) throws Exception {
        File file = new File(filePath);
        file.delete();
    }

    @Override
    public TemporaryFileHolder loadFile(String fileStorageDir, String fileName) throws Exception {
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
    public File getFile(String fileStorageDir, String fileName) throws FileNotFoundException {
        File file = new File(fileStorageDir + File.separator + fileName);
        if (file.exists() && !file.isDirectory())
            return file;
        throw new FileNotFoundException(fileName);
    }
}
