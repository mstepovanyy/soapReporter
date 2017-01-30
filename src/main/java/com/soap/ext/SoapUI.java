package com.soap.ext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.lf5.LogLevel;

import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStepInterface;
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStepInterface;
import com.eviware.soapui.model.testsuite.SamplerTestStep;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestProperty;
import com.eviware.soapui.model.testsuite.TestRunContext;
import com.eviware.soapui.support.types.StringToStringsMap;
import com.soap.ext.listener.logger.CustomLevel;
import com.soap.ext.listener.logger.CustomLogger;



public class SoapUI {
	
	private static CustomLogger logger = new CustomLogger(SoapUI.class.getName());
	
	
	/**
	 * Receive date for current request
	 * @param context
	 * @return Date
	 */
	public static String getDate(TestCaseRunContext context) {
		logger.logSoapUI(CustomLevel.INFO, "Generating Date ... ");
		return CustomDate.getDate(getSuiteName(context) + getTestCaseName(context)+ getStepName(context), 0);
	}
	
	/**
	 * Receive date for current request
	 * @param context
	 * @return Date
	 */
	public static String getDate(TestCaseRunContext context, int timeShift) {
		logger.logSoapUI(CustomLevel.INFO, "Generating Date ... ");
		return CustomDate.getDate(getSuiteName(context) + getTestCaseName(context)+ getStepName(context), timeShift);
	}
	
	/**
	 * Receive path for request 
	 * @param context
	 * @return URL
	 */
	private static String getPath(TestCaseRunContext context) {
		if (context.getTestCase().getTestStepByName(getStepName(context)) instanceof HttpTestRequestStepInterface){
			String path =  ((HttpTestRequestStepInterface)context.getTestCase().getTestStepByName(getStepName(context))).getTestRequest().getPath();
			return expandProperties(context, path);
		}
		return null;
	}

	private static String getStepName(TestCaseRunContext context) {
		return context.getCurrentStep().getName();
	}
	
	private static String getSuiteName(TestCaseRunContext context) {
		return context.getCurrentStep().getTestCase().getTestSuite().getName();
	}
	
	private static String getTestCaseName(TestCaseRunContext context) {
		return context.getCurrentStep().getTestCase().getName();
	}
	
	/**
	 * Evaluate properties from SoapUI.
	 * @param context
	 * @param path
	 * @return
	 */
	static String expandProperties(TestCaseRunContext context, String path ){
		return expandProperties(context, path, false);
	}
	
	/**
	 * 
	 * @param context - this variable for json skipping
	 * @param path
	 * @param content
	 * @return
	 */
	static String expandProperties(TestCaseRunContext context, String path, boolean content ) {
		logger.logSoapUI(CustomLevel.INFO, "ExpandProperties : " + path);
		
		while(path.matches(".*\\{.*\\}.*")){
			if (content && (!path.contains("#"))){
				// this code used for skipp json data
				return path;
			}
			Matcher matcher = Pattern.compile(".*\\{(.*)\\}.*").matcher(path);
			if (matcher.find()){

				String propertyName = matcher.group(1);
				String propertyValue = getProperty( context, propertyName);
				
				if (propertyValue.contains("{")){
					propertyValue = expandProperties(context, propertyValue, content);
				}
				
				
				logger.logSoapUI(CustomLevel.INFO, "PropertyName : " + propertyName);
				path = path.replaceAll("\\$\\{"+propertyName+"\\}", propertyValue);
				path = path.replaceAll("\\{"+propertyName+"\\}", propertyValue);
			}
		}
		
		logger.logSoapUI(CustomLevel.INFO, "ExpandProperties result: " + path);
		return path;
	}

	/**
	 * Find regexp for input string and return first match. 
	 * @param regexp
	 * @param input
	 * @return
	 */
	private static String getRegExpResult (String regexp, String input){
		Matcher matcher;
		matcher = Pattern.compile( regexp ).matcher(input);
		matcher.find();
		return matcher.group(1);
	}
	
	/**
	 * Trying to get property from TestStep/Suite/Project/TestCase
	 * @param context
	 * @param propertyName
	 * @return
	 */
	private static String getProperty(TestCaseRunContext context, String propertyName) {
		TestProperty prop;
		logger.logSoapUI(CustomLevel.INFO, "Get Property : " + propertyName );
		String regExpResult;
		
		if (propertyName.contains("#Project#")){
			regExpResult = getRegExpResult("\\#Project\\#(.*)", propertyName);
			prop = context.getTestCase().getTestSuite().getProject().getProperty(regExpResult);
			
		} else if (propertyName.contains("#TestCase#")){
			regExpResult = getRegExpResult("\\#TestCase\\#(.*)", propertyName);
			prop = context.getTestCase().getProperty(regExpResult);
			
		}  else if (propertyName.contains("#TestSuite#")){
			regExpResult = getRegExpResult("\\#TestSuite\\#(.*)", propertyName);
			prop = context.getTestCase().getTestSuite().getProperty(regExpResult);
			
		}else if (propertyName.contains("#")){
			String step =  getRegExpResult("(.*)\\#.*", propertyName);
			String property = getRegExpResult(".*\\#(.*)", propertyName);
			prop= context.getTestCase().getTestStepByName(step).getProperty(property);
		} else {
			prop= context.getTestCase().getTestStepByName(getStepName(context)).getProperty(propertyName);
		} ;
		try {
			logger.logSoapUI(CustomLevel.INFO, "Property : " + propertyName + " Value: " + prop.getValue());
		} catch (java.lang.NullPointerException e){
			return propertyName;
		}
		return prop.getValue();
	}
	
	/**
	 * Get selected header for current request
	 * @param context
	 * @param header
	 * @return
	 */
	private static String getHeader(TestCaseRunContext context, String header) {
		StringToStringsMap headers = new StringToStringsMap();
		if (context.getTestCase().getTestStepByName(getStepName(context)) instanceof HttpTestRequestStepInterface){
			headers = ((HttpTestRequestStepInterface)context.getTestCase().getTestStepByName(getStepName(context))).getTestRequest().getRequestHeaders();
		}
		return headers.get(header, "Header not found: " + header);
	}
	
	
}
