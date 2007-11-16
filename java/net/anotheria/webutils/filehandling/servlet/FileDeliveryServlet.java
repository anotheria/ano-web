package net.anotheria.webutils.filehandling.servlet;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.anotheria.webutils.filehandling.actions.FileStorage;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.util.DownloadMeter;

public class FileDeliveryServlet extends HttpServlet{
	
	private static Logger log = Logger.getLogger(FileDeliveryServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getPathInfo();
		
		if (name==null || name.length()==0)
			throw new ServletException("No filename specified.");
		
		while (name.startsWith("/"))
			name = name.substring(1);
		
		if (name!=null && name.startsWith("../"))
			throw new ServletException("Not allowed!");

		TemporaryFileHolder h = FileStorage.loadFile(name);
		byte data[] = h.getData();
		
		String mimeType = h.getMimeType(); 
		if (mimeType!=null)
			res.setContentType(mimeType);
		if (data!=null)
			res.setContentLength(data.length);
		if (h.getLastModified()!=0)
			res.setHeader("Last-Modified", new Date(h.getLastModified()).toGMTString());
		
		BufferedOutputStream bOut = new BufferedOutputStream(res.getOutputStream(), 8192);
		bOut.write(data);
		bOut.flush();
		bOut.close();

		if (data!=null)
			DownloadMeter.addDownload(data.length);
		
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.sendError(403, "Forbidden");
		return;
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		log.debug("doHead Called: "+req.getPathInfo());
		System.out.println("doHead Called: "+req.getPathInfo());
		super.doHead(req, res);
	}
}
