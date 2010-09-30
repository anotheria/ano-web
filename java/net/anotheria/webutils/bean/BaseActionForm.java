package net.anotheria.webutils.bean;

import org.apache.struts.action.ActionForm;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public abstract class BaseActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Is true if the form has been submitted (posted). Needed to prevent F5 hitting.
	 */
	private boolean formSubmittedFlag;

	public void setFormSubmittedFlag(boolean formSubmittedFlag ){
		this.formSubmittedFlag = formSubmittedFlag;
	}

	public boolean isFormSubmittedFlag(){
		return formSubmittedFlag;
	}

}
