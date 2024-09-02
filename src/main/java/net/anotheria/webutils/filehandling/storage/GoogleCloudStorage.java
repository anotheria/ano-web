package net.anotheria.webutils.filehandling.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;
import com.google.cloud.storage.StorageOptions;
import net.anotheria.util.IOUtils;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Google cloud storage.
 *
 * @author asamoilich.
 */
public class GoogleCloudStorage implements IStorage {
    /**
     * {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCloudStorage.class);
    private final String bucketName;
    /**
     * {@link Storage} instance.
     */
    private final Storage cloudStorage;

    public GoogleCloudStorage(String bucketName, String credentialsPath, String projectId) {
        this.bucketName = bucketName;
        try {
            URL url = getClass().getClassLoader().getResource(credentialsPath);
            cloudStorage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(Objects.requireNonNull(url).openStream()))
                    .setProjectId(projectId)
                    .build()
                    .getService();

            initializeBucket();
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize google storage. ", e);
        }
    }

    private void initializeBucket() {
        Bucket bucket = cloudStorage.get(bucketName, Storage.BucketGetOption.fields(Storage.BucketField.NAME));
        if (bucket == null) {
            //create bucket
            bucket = cloudStorage.create(BucketInfo.newBuilder(bucketName)
                    .setStorageClass(StorageClass.STANDARD)
                    .setLocation("EU")
                    .build());
            LOGGER.info("Bucket created: " + bucket.toString());
        }
    }

    @Override
    public void storeFile(byte[] fileContent, String filePath) throws Exception {
        BlobId blobId = BlobId.of(bucketName, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        cloudStorage.create(blobInfo, fileContent);
    }

    @Override
    public boolean isFileExists(String filePath) {
        return cloudStorage.get(bucketName, filePath) != null;
    }

    @Override
    public void cloneFile(String sourceFilePath, String destinationFilePath) throws Exception {
        byte[] data = cloudStorage.readAllBytes(bucketName, sourceFilePath);
        BlobId blobId = BlobId.of(bucketName, destinationFilePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        cloudStorage.create(blobInfo, data);
    }

    @Override
    public void removeFile(String filePath) throws Exception {
        cloudStorage.delete(bucketName, filePath);
    }

    @Override
    public TemporaryFileHolder loadFile(String fileStorageDir, String fileName) throws Exception {
        FileOutputStream fos = null;
        try {
            byte[] data = cloudStorage.readAllBytes(bucketName, fileStorageDir + File.separator + fileName);
            File tempFile = File.createTempFile(fileName, null);
            fos = new FileOutputStream(tempFile);
            fos.write(data);
            long lastModified = tempFile.lastModified();

            TemporaryFileHolder f = new TemporaryFileHolder();
            f.setData(data);
            f.setFileName(fileName);
            f.setLastModified(lastModified);
            return f;
        } finally {
            IOUtils.closeIgnoringException(fos);
        }
    }

    @Override
    public File getFile(String fileStorageDir, String fileName) throws FileNotFoundException {
        byte[] data = cloudStorage.readAllBytes(bucketName, fileStorageDir + File.separator + fileName);
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(data);
            if (file.isFile()) {
                return file;
            }
            throw new FileNotFoundException(fileName);
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        } finally {
            IOUtils.closeIgnoringException(fos);
        }
    }
}
