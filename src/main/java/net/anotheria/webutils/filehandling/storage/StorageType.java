package net.anotheria.webutils.filehandling.storage;

/**
 * Storage type values.
 *
 * @author asamoilich.
 */
public enum StorageType {
    /**
     * File system.
     */
    FS("fs"),
    /**
     * Google cloud storage.
     */
    GCS("gcs"),
    /**
     * S3 cloud storage.
     */
    S3("s3");
    /**
     * Storage type value.
     */
    private final String typeValue;

    StorageType(String typeValue) {
        this.typeValue = typeValue;
    }

    public static StorageType getByTypeValue(String typeValue) {
        for (StorageType type : StorageType.values()) {
            if (type.getTypeValue().equalsIgnoreCase(typeValue)) {
                return type;
            }
        }
        return FS;
    }

    public String getTypeValue() {
        return typeValue;
    }
}
