/*
 * Created on 14.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.webutils.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.anotheria.webutils.service.XMLUserManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoginAction extends BaseAction{
	
	public static final String P_USER_ID  = "pUserId";
	public static final String P_PASSWORD = "pPassword";

	private XMLUserManager manager;
	
	public LoginAction(){
		manager = XMLUserManager.getInstance();
	}

	/* (non-Javadoc)
	 * @see net.anotheria.webutils.actions.BaseAction#doExecute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doExecute(
		ActionMapping mapping,
		ActionForm af,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
		
		String userId, password;	
		
		try{
			userId   = getStringParameter(req, P_USER_ID);
	 		password = getStringParameter(req, P_PASSWORD);
	 		if (!manager.canLoginUser(userId, password))
	 			throw new RuntimeException("Can't login.");
		}catch(Exception e){
			return mapping.findForward("success");
		}
		addBeanToSession(req, BEAN_USER_ID, userId);
		String redT = (String)req.getSession().getAttribute(BEAN_TARGET_ACTION);
		if (redT==null)
			redT = req.getContextPath();
		if (!(redT.startsWith("/")))
			redT = "/"+redT;
		res.sendRedirect(redT);
		return null;
	}
}
