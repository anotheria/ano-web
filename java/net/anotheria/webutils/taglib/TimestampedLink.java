package net.anotheria.webutils.taglib;

import javax.servlet.jsp.JspException;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class TimestampedLink extends BaseBodyTagSupport{
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException {
		String body = getBodyContent().getString();
		char param = body.indexOf('?')==-1 ? 
			'?':'&';
		body += param+"ts"+"="+System.currentTimeMillis();
		write(body);
		return EVAL_BODY_TAG;
	}

}
