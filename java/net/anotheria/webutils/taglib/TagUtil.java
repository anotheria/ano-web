package net.anotheria.webutils.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class TagUtil {
	protected static void write(PageContext pageContext, String s) throws JspException{
		//ResponseUtils.write(pageContext, s);
		throw new AssertionError("Not supported anymore!");		

	}
	
	protected static void writeLn(PageContext pageContext, String s) throws JspException{
		write(pageContext, s+"\n");		
	}

	protected static String quote(String s){
		return "\""+s+"\"";
	}

}
