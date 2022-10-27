package net.anotheria.webutils.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;



public class LogoutAction extends AccessControlMafAction{

	@Override
	public ActionCommand execute (ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		removeBeanFromSession(req, BEAN_USER_ID);
		res.addCookie(createAuthCookie(req, "CAFE", "BABE"));
		// res.sendRedirect(req.getContextPath());
		// return null;
		return mapping.success();
	}


}
 