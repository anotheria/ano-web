package net.anotheria.webutils.filehandling.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import net.anotheria.util.IOUtils;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * S3 cloud storage.
 *
 * @author asamoilich.
 */
public class S3CloudStorage implements IStorage {

    private final String bucketName;
    /**
     * {@link AmazonS3} instance.
     */
    private final AmazonS3 conn;

    public S3CloudStorage(String bucketName, String accessKey, String secretKey, String endPoint) {
        this.bucketName = bucketName;
        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            conn = new AmazonS3Client(credentials);
            conn.setEndpoint(endPoint);
            initializeBucket();
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize google storage. ", e);
        }
    }

    private void initializeBucket() {
        final List<Bucket> buckets = conn.listBuckets();
        for (Bucket bucket : buckets) {
            if (bucket.getName().equals(bucketName)) {
                return;
            }
        }
        conn.createBucket(bucketName);
    }

    @Override
    public void storeFile(byte[] fileContent, String filePath) throws Exception {
        ObjectMetadata metadata = new ObjectMetadata();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContent);
        metadata.setContentLength(inputStream.available());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, inputStream, metadata);
        conn.putObject(putObjectRequest);
    }

    @Override
    public boolean isFileExists(String filePath) {
        return conn.doesObjectExist(bucketName, filePath);
    }

    @Override
    public void cloneFile(String sourceFilePath, String destinationFilePath) throws Exception {
        S3Object object = conn.getObject(new GetObjectRequest(bucketName, sourceFilePath));
        conn.putObject(bucketName, destinationFilePath, new ByteArrayInputStream(IOUtils.readBytes(object.getObjectContent())), new ObjectMetadata());
    }

    @Override
    public void removeFile(String filePath) throws Exception {
        conn.deleteObject(bucketName, filePath);
    }

    @Override
    public TemporaryFileHolder loadFile(String fileStorageDir, String fileName) throws Exception {
        S3Object object = conn.getObject(new GetObjectRequest(bucketName, fileStorageDir + File.separator + fileName));
        TemporaryFileHolder f = new TemporaryFileHolder();
        f.setData(IOUtils.readBytes(object.getObjectContent()));
        f.setFileName(fileName);
        f.setLastModified(object.getObjectMetadata().getLastModified().getTime());
        return f;
    }

    @Override
    public File getFile(String fileStorageDir, String fileName) throws FileNotFoundException {
        S3Object object = conn.getObject(new GetObjectRequest(bucketName, fileStorageDir + File.separator + fileName));
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(IOUtils.readBytes(object.getObjectContent()));
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
