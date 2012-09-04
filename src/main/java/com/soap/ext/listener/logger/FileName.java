package com.soap.ext.listener.logger;

import java.io.File;

import com.eviware.soapui.model.testsuite.TestCaseRunner;

public class FileName {

	public static String directoryOutput = "test-output"; 
	public static String directoryLogs = directoryOutput + File.separator + "logs";
	
	protected static String getFileName(TestCaseRunner testRunner){
		String result =  testRunner.getTestCase().getTestSuite().getName() + "---" +testRunner.getTestCase().getName();
		result = directoryLogs + File.separator + result.replace("/", "_") ;
		return result;
	}
	
	public static String getDirectoryLog(){
		return directoryLogs;
	}
	
	public static String getFileNameXML(TestCaseRunner testRunner){
		return getFileName(testRunner) + ".xml";
	}
	public static String getFileNameTXT(TestCaseRunner testRunner){
		return getFileName(testRunner) + ".txt";
	}
	public static String getFileNameHTML(TestCaseRunner testRunner){
		return getFileName(testRunner) + ".html";
	}
	
	public static String getHTMLName(String name){
		name = name.replace(" ", "%20");
		name = name.replace("?", "%3F");
		name = name.replace("[", "%5B");
		name = name.replace("]", "%5D");
		name = name.replace("~", "%7E");
		name = name.replace("{", "%7B");
		name = name.replace("}", "%7D");
		name = name.replace("|", "%7C");
		name = name.replace("/", "%2F");
		return name;
	}
}
