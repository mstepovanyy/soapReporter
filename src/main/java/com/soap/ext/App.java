package com.soap.ext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String path = "/AdminServer/rest/v1/{command}";
        System.out.println(expand(path));
    }
    static String expand(String path) {
		if (path.matches(".*\\{.*\\}.*")){
			//Matcher matcher = Pattern.compile(".*\\{(.*)\\}.*").matcher(path);
			Matcher matcher = Pattern.compile(".*\\{(.*)\\}.*").matcher(path);
			if (matcher.find()){
				String propertyName = matcher.group(1);
				System.out.println (propertyName);
				String propertyValue = "Value";
				
				path = path.replaceAll(""+propertyName+"", "Value");
				//path = expand(path);
			}
		}
		return path;
	}
}
