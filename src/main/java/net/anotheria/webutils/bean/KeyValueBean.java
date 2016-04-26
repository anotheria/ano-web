package net.anotheria.webutils.bean;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KeyValueBean {
	private String key;
	private String value;
	/**
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param string
	 */
	public void setKey(String string) {
		key = string;
	}
	
	public KeyValueBean(){
		
	}
	
	public KeyValueBean(String aKey, String aValue){
		this.key = aKey;
		this.value = aValue;
	}

	public KeyValueBean(String aKey, Object aValue){
		this(aKey, ""+aValue);
	}
	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

}
