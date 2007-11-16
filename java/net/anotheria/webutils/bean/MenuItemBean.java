package net.anotheria.webutils.bean;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class MenuItemBean {
	private String caption;
	private String link;
	private boolean active;
	private String style;
	
	public String toString(){
		return caption+", "+link+", "+active;
	}
	/**
	 * @return
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @return
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * @param string
	 */
	public void setCaption(String string) {
		caption = string;
	}

	/**
	 * @param b
	 */
	public void setActive(boolean b) {
		active = b;
	}

	/**
	 * @param string
	 */
	public void setLink(String string) {
		link = string;
	}

	/**
	 * @param string
	 */
	public void setStyle(String string) {
		style = string;
	}

}
