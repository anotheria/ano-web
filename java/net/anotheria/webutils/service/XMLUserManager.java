/*
 * Created on 22.02.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.anotheria.webutils.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * @author another
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XMLUserManager {
	private static Hashtable users;
	 
	private static Logger log;
	private static XMLUserManager instance;
	private static boolean inited;
	
	static{
		log = Logger.getLogger(XMLUserManager.class);
	}
	
	private XMLUserManager(){
		if (!inited)
			throw new RuntimeException("Not inited");
	}
	
	public static XMLUserManager getInstance(){
		if (instance==null)
			instance = new XMLUserManager();
		return instance;
	}
	
	public boolean canLoginUser(String userId, String password){
		String p = (String)users.get(userId);
		if (p==null)
			return false;
		return p.equals(password);
	}
	
	public boolean isKnownUser(String userId){
		return users.containsKey(userId);
	}
	
	private static void init(InputStream stream){
		log.debug("initing from stream: "+stream);
		if (inited){
			try{
				stream.close();
			}catch(IOException ignored){}
			return ;
		}
		users = new Hashtable();
		try{		
			byte data[] = new byte[stream.available()];
			stream.read(data);
			log.debug("file read "+data.length+" bytes.");
			parseUsers(new String(data));
			inited = true;
		}catch(IOException e){
			log.error("init", e);
		}
	}

	public static void init(String fileName){
		init(XMLUserManager.class.getResourceAsStream(fileName));
	}

	public static void init(File f){
		try{
			init(new FileInputStream(f));
		}catch(FileNotFoundException e){
			log.error("init", e);
		}
	}
	
	private static void parseUsers(String content){
		try{
			SAXBuilder reader = new SAXBuilder();
			reader.setValidation(false);
	
			Document doc = reader.build(new StringReader(content));
	
			Element root = doc.getRootElement();
		
			List users = root.getChildren();
			for (int i=0;i<users.size(); i++){
				Element userElem = (Element) users.get(i);
				parseUser(userElem);
			}
		
		}catch(Exception e){
			log.error("parseUsers", e);
		}
	}
	
	private static void parseUser(Element e){
		String username = e.getChild("username").getText();
		String pwd      = e.getChild("password").getText();
		users.put(username, pwd);
		log.debug("added user "+username);
	}
		
	
	public static void main(String a[]){
		BasicConfigurator.configure();
		//File f = new File("etc/appdata/users.xml");
		//XMLUserManager.init(f);
		XMLUserManager.init("/users.xml");
		System.out.println(XMLUserManager.users);
	}
}
