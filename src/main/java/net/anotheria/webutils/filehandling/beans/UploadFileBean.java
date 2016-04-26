package net.anotheria.webutils.filehandling.beans;

/**
 * The bean used for the FileUploadDialog.
 */
public class UploadFileBean {
	private String name;
	private String size;
	private boolean valid;
	private String message;
	private boolean filePresent;
	
	/**
	 * Returns the name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the size.
	 * @return String
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Sets the name.
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the size.
	 * @param size The size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	

	/**
	 * Returns the valid.
	 * @return boolean
	 */
	public boolean isValid() {
		return true;
	}

	/**
	 * Sets the valid.
	 * @param valid The valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

	/**
	 * @return
	 */
	public boolean isFilePresent() {
		return filePresent;
	}

	/**
	 * @param b
	 */
	public void setFilePresent(boolean b) {
		filePresent = b;
	}

}
