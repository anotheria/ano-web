/*
 * Created on 14.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.webutils.actions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.anotheria.webutils.service.XMLUserManager;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 
 * @author another
 */
public class LoginAction extends AccessControlAction{
	
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
		
		
		// // // First try to read auth from cookie.
		try{
			String authString = null;
			String cookieName = getAuthCookieName(req);
			for (Cookie c : req.getCookies()){
				if (c!=null && c.getName().equals(cookieName)){
					authString = getCryptTool().decryptFromHex(c.getValue()).trim();
					break;
				}
			}
			
			if (authString!=null){
				int index = authString.indexOf(AUTH_DELIMITER);
				String userId = authString.substring(0, index);
				String password = authString.substring(index+1);
				
				if (userId!=null && password!=null){
					if (manager.canLoginUser(userId, password)){
						addBeanToSession(req, BEAN_USER_ID, userId);
						res.sendRedirect(getRedirectTarget(req));
						return null;
					}
				}
			}
		}catch(Exception e){
			log.warn("read auth from cookie", e);
		}
		
		/// END COOKIE AUTH
		
		String userId, password;	
		
		try{
			userId   = getStringParameter(req, P_USER_ID);
	 		password = getStringParameter(req, P_PASSWORD);
	 		if (!manager.canLoginUser(userId, password))
	 			throw new RuntimeException("Can't login.");
	 		
			res.addCookie(createAuthCookie(req, userId, password));
		}catch(Exception e){
			return mapping.findForward("success");
		}
		addBeanToSession(req, BEAN_USER_ID, userId);
		res.sendRedirect(getRedirectTarget(req));
		return null;
	}
	
	
	private String getRedirectTarget(HttpServletRequest req){
		String redT = (String)req.getSession().getAttribute(BEAN_TARGET_ACTION);
		if (redT==null)
			redT = req.getContextPath();
		if (!(redT.startsWith("/")))
			redT = "/"+redT;
		if (redT.indexOf('?')==-1)
			redT += "?";
		else
			redT += "&";
		redT += "auth=true";
		return redT;
	}
}
