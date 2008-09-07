package net.anotheria.webutils.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.anotheria.util.StringUtils;

public class XMLUser {
	private String username;
	private String password;
	private List<String> roles;
	
	public XMLUser(){
		roles = new ArrayList<String>();
	}
	
	public XMLUser(String aUserName, String aPassword){
		this();
		username = aUserName;
		password = aPassword;
	}
	
	public XMLUser(String aUserName, String aPassword, List<String> someRoles){
		this(aUserName, aPassword);
		roles.addAll(someRoles);
	}
	
	public XMLUser(String aUserName, String aPassword, String commaSeparatedListWithRoles){
		this(aUserName, aPassword, parseRoles(commaSeparatedListWithRoles));
	}
	
	public boolean isUserInRole(String role){
		return roles.indexOf(role)!=-1 || roles.indexOf("*")!=-1;
	}
	
	public String toString(){
		return "Username: "+getUsername()+", Password: "+getPassword()+", Roles: "+getRoles();
	}
	
	private static List<String> parseRoles(String rolesString){
		return rolesString == null ? 
				new ArrayList<String>() : 
					Arrays.asList(StringUtils.tokenize(rolesString.trim(), ','));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public void addRole(String aRole){
		roles.add(aRole);
	}
	
	
}
