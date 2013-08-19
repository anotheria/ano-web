package net.anotheria.webutils.filehandling.servlet;

import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;
import net.anotheria.webutils.filehandling.actions.FileStorage;
import net.anotheria.webutils.filehandling.beans.TemporaryFileHolder;
import net.anotheria.webutils.util.DownloadMeter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileDeliveryServlet extends HttpServlet{
	
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -6861040663569404495L;

    /**
     * {@link Logger} logger.
     */
	private static Logger log = LoggerFactory.getLogger(FileDeliveryServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = getRequestedResourceName(req);

		TemporaryFileHolder h = FileStorage.loadFile(name);
		if (h==null){
			res.sendError(404, "Missing binary data, probably file deleted on disk: "+name);
			return;
		}
		byte data[] = h.getData();
		
		String mimeType = h.getMimeType(); 
		if (mimeType!=null)
			res.setContentType(mimeType);
		if (data!=null)
			res.setContentLength(data.length);
		if (h.getLastModified()!=0)
			res.setHeader("Last-Modified", new Date(h.getLastModified()).toGMTString());
		
		BufferedOutputStream bOut = null;
		try{
			bOut = new BufferedOutputStream(res.getOutputStream(), 8192);
			if (data!=null){
				bOut.write(data);
				bOut.flush();
			}
		}finally{
			//BufferedOutputStream executes cascading closing and in this case leads to res output stream closing as well that is probably not necessary.
			IOUtils.closeIgnoringException(bOut);
		}

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


	/**
	 * Returns name of requested resource.
	 *
	 * @param req HttpServletRequest
	 * @return name of resource
	 */
	protected String getRequestedResourceName(HttpServletRequest req) throws ServletException {
		String name = req.getPathInfo();

		if (StringUtils.isEmpty(name))
			throw new ServletException("No filename specified.");

		while (name.startsWith("/"))
			name = name.substring(1);

		if (name!=null && name.startsWith("../"))
			throw new ServletException("Not allowed!");

		return name;
	}

}
