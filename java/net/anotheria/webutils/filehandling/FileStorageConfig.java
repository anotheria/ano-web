package net.anotheria.webutils.filehandling;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.slf4j.LoggerFactory;


/**
 * File storage configuration.
 *
 * @author h3ll
 */
@ConfigureMe(name = "filestorage")
public class FileStorageConfig {

	/**
	 * Default file storage directory.
	 */
	private static final String DEF_FILE_STORAGE_DIR = "/work/data/files/";

	/**
	 * FileStorageConfig instance.
	 */
	private static FileStorageConfig instance;
	/**
	 * Actually storage directory.
	 */
	@Configure
	private String directory;

	/**
	 * Get instance method.
	 *
	 * @return {@link FileStorageConfig}
	 */
	public static synchronized FileStorageConfig getInstance() {
		if (instance == null) {
			instance = new FileStorageConfig();
			try {
				ConfigurationManager.INSTANCE.configure(instance);
			} catch (Exception e) {
                LoggerFactory.getLogger(FileStorageConfig.class).warn("FileStorage configuration failed! Relying on defaults!", e);
			}
		}
		return instance;
	}

	/**
	 * Constructor.
	 */
	private FileStorageConfig() {
		this.directory = DEF_FILE_STORAGE_DIR;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String aFileStorageDir) {
		this.directory = aFileStorageDir;
	}


	@Override
	public String toString() {
		return "FileStorageConfig{" +
				"directory='" + directory + '\'' +
				'}';
	}
}
