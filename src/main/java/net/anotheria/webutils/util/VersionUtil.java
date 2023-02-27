package net.anotheria.webutils.util;

import java.io.File;

import jakarta.servlet.ServletContext;

import net.anotheria.util.maven.MavenVersion;
import net.anotheria.util.maven.MavenVersionReader;

public class VersionUtil {
	public static MavenVersion getWebappVersion(ServletContext context){
		//this is pretty hacky now.
		String pathToMetaInf = context.getRealPath("/META-INF");
		return MavenVersionReader.findVersionInDirectory(new File(pathToMetaInf));
	}
	
	public static MavenVersion getWebappLibVersion(ServletContext context, String libname){
		String pathToLib = context.getRealPath("/WEB-INF/lib");
		return MavenVersionReader.findJarInDirectory(new File(pathToLib), libname);
	}
}
