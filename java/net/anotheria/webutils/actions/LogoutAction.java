package net.anotheria.webutils.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends AccessControlAction{

	@Override
	public ActionForward doExecute(ActionMapping mapping, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {
		removeBeanFromSession(req, BEAN_USER_ID);
		res.addCookie(createAuthCookie(req, "CAFE", "BABE"));
		res.sendRedirect(req.getContextPath());
		return null;
		//return mapping.findForward("success");
	}

}
 