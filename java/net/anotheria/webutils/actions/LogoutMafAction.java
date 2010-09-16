package net.anotheria.webutils.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;



public class LogoutMafAction extends AccessControlMafAction{

	@Override
	public ActionForward execute (ActionMapping mapping, FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {
		removeBeanFromSession(req, BEAN_USER_ID);
		res.addCookie(createAuthCookie(req, "CAFE", "BABE"));
		// res.sendRedirect(req.getContextPath());
		// return null;
		return mapping.findForward("success");
	}


}
 