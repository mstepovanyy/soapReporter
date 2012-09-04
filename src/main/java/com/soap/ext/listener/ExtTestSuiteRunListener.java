package com.soap.ext.listener;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.FileAppender;

import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestSuiteRunContext;
import com.eviware.soapui.model.testsuite.TestSuiteRunListener;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;
import com.soap.ext.listener.logger.CustomLevel;
import com.soap.ext.listener.logger.CustomLogger;
import com.soap.ext.listener.logger.FileName;
import com.soap.ext.listener.logger.SoapUIHTMLLayout;

public class ExtTestSuiteRunListener implements TestSuiteRunListener{

	FileAppender htmlAppender = null;
	private CustomLogger logger = new CustomLogger("Test case");
	String testSuitePrefix = "TestSuite---";
	
	@Override
	public void afterRun(TestSuiteRunner arg0, TestSuiteRunContext arg1) {
		// TODO Auto-generated method stub
		
		List<String> failedTestCases = new ArrayList<String>();
		for (TestCaseRunner testcase : arg0.getResults()){
			if (testcase.getStatus() == TestRunner.Status.FAILED){
				failedTestCases.add(testcase.getTestCase().getName().replace("/", "_"));
			}
		};
		
		
		File directory = new File (FileName.getDirectoryLog());
		
		String[] directoryList = directory.list();
		
		for (int i = 0 ; i < directoryList.length; i++){
			//logger.info("Processing : " + directoryList[i] );
			if (directoryList[i].contains("html") 
					&& !directoryList[i].contains("index.html")
					&& !directoryList[i].contains(testSuitePrefix)
					&& directoryList[i].contains(arg1.getTestSuite().getName())
					){
				
				String testCaseName = directoryList[i].substring ( 
						directoryList[i].indexOf(arg1.getTestSuite().getName())
						+ arg1.getTestSuite().getName().length() + 3);
				
				if (failedTestCases.contains(testCaseName.split("\\.")[0])){
					logger.logSoapUI(CustomLevel.FAILED, "<a href=" + FileName.getHTMLName(directoryList[i]) + ">" 
							+ testCaseName.split("\\.")[0] +"</a>");
				} else {
					logger.logSoapUI(CustomLevel.PASSED, "<a href=" + FileName.getHTMLName(directoryList[i])+ ">" 
							+ testCaseName.split("\\.")[0] +"</a>");
				}
			}
		}
		//htmlAppender.close();
	}

	@Override
	public void afterTestCase(TestSuiteRunner arg0, TestSuiteRunContext arg1,
			TestCaseRunner arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeRun(TestSuiteRunner arg0, TestSuiteRunContext arg1) {
		// TODO Auto-generated method stub
		try {
			htmlAppender = new FileAppender(new SoapUIHTMLLayout(), 
					FileName.getDirectoryLog() + File.separator + testSuitePrefix + arg1.getTestSuite().getName() + ".html");
			logger.removeAllAppenders();
			logger.addAppender(htmlAppender);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void beforeTestCase(TestSuiteRunner arg0, TestSuiteRunContext arg1,
			TestCase arg2) {
		// TODO Auto-generated method stub
		
	}

}
