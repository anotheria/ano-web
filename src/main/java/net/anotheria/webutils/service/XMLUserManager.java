package net.anotheria.webutils.service;

import net.anotheria.util.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//import org.apache.log4j.BasicConfigurator;

/**
 * A very basic user manager mainly for embedded authorization purposes.
 * @author another
 */
public class XMLUserManager {
	private static Map<String, XMLUser> users;
	 
	private static Logger log;
	private static XMLUserManager instance ;
	private static boolean inited;
	
	static{
		log = LoggerFactory.getLogger(XMLUserManager.class);
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
		XMLUser u = users.get(userId);
		if (u==null)
			return false;
		return u.getPassword().equals(password);
	}
	
	public boolean userInRole(String userId, String role){
		return isKnownUser(userId) && users.get(userId).isUserInRole(role);
	}
	
	public boolean isKnownUser(String userId){
		return users.containsKey(userId);
	}
	
	private static void init(InputStream stream){
		log.debug("initing from stream: "+stream);
		try{
			if (inited)
				return ;
			users = new ConcurrentHashMap<String, XMLUser>();
			try{		
				byte data[] = new byte[stream.available()];
				stream.read(data);
				log.debug("file read "+data.length+" bytes.");
				parseUsers(new String(data));
				inited = true;
			}catch(IOException e){
				log.error("init", e);
			}
		}finally{
			IOUtils.closeIgnoringException(stream);
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
		
			@SuppressWarnings("unchecked")List<Element> users = root.getChildren();
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
		String roles    = "";
		try{
			roles = e.getChildText("roles");
		}catch(Exception ignored){
			//older installations don't have the roles attribute in their xml user list
		}
		
		XMLUser toAdd = new XMLUser(username, pwd, roles);
		users.put(toAdd.getUsername(), toAdd);
		log.debug("added user "+toAdd);
	}
		
	
	public static void main(String a[]){
//		BasicConfigurator.configure();
		//File f = new File("etc/appdata/users.xml");
		//XMLUserManager.init(f);
		XMLUserManager.init("/users.xml");
		System.out.println(XMLUserManager.users);
		
		Collection<XMLUser> col = XMLUserManager.users.values();
		for (XMLUser u : col){
			System.out.println(u+" is in Role mapeditor "+u.isUserInRole("mapeditor"));
		}
	}
}
