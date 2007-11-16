package net.anotheria.webutils.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class BaseBodyTagSupport extends BodyTagSupport{
	private String scope;
	
	/**
	 * @return
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param string
	 */
	public void setScope(String string) {
		scope = string;
	}

	protected void write(String s) throws JspException{
		TagUtil.write(pageContext, s);		
	}
	
	protected void writeLn(String s) throws JspException{
		TagUtil.write(pageContext, s+"\n");		
	}

	protected static String quote(String s){
		return TagUtil.quote(s);
	}

}
