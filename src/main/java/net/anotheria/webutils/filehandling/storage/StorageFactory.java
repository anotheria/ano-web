package net.anotheria.webutils.filehandling.storage;

/**
 * Storage factory.
 *
 * @author asamoilich.
 */
public class StorageFactory {
    public static IStorage createStorage(String storageType, String bucketName, String credentials, String projectId, String accessKey, String secretKey) {
        switch (StorageType.getByTypeValue(storageType)) {
            case GCS:
                return new GoogleCloudStorage(bucketName, credentials, projectId);
            case S3:
                return new S3CloudStorage(bucketName, accessKey, secretKey, projectId);
            case FS:
            default:
                return new FsStorage();
        }
    }
}
