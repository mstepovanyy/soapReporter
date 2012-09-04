package com.soap.ext.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.FileAppender;

import com.eviware.soapui.model.testsuite.ProjectRunContext;
import com.eviware.soapui.model.testsuite.ProjectRunListener;
import com.eviware.soapui.model.testsuite.ProjectRunner;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.model.testsuite.TestSuiteRunner;
import com.soap.ext.listener.logger.CustomLevel;
import com.soap.ext.listener.logger.CustomLogger;
import com.soap.ext.listener.logger.FileName;
import com.soap.ext.listener.logger.SoapUIHTMLLayout;



public class ExtProjectListener implements ProjectRunListener{

	private CustomLogger logger = new CustomLogger("Test suite");
	
	FileAppender htmlAppender = null;
	String directoryLog;
	String testSuitePrefix = "TestSuite---";
	
	public void afterRun(ProjectRunner arg0, ProjectRunContext arg1) {
		
		List<String> failedTestSuites = new ArrayList<String>();
		
		for (TestSuiteRunner testSuite : arg0.getResults()){
			for (TestCaseRunner testcase : testSuite.getResults()){
				if (testcase.getStatus() == TestRunner.Status.FAILED){
					failedTestSuites.add(testcase.getTestCase().getTestSuite().getName());
				}
			};
		}
		// TODO Auto-generated method stub
		directoryLog = FileName.getDirectoryLog();
		File directory = new File (directoryLog);
		
		String[] directoryList = directory.list();
		
		//logger.info("This is test file appender");
		//logger.info("Found files : " + directoryList.length);
		for (int i = 0 ; i < directoryList.length; i++){
			//logger.info("Processing : " + directoryList[i] );
			if (directoryList[i].contains("html") 
					&& !directoryList[i].contains("index.html")
					&& directoryList[i].contains(testSuitePrefix)){
				String testSuiteName = directoryList[i].split("---")[1];
				//logger.info(testSuiteName);
				String ts = testSuiteName.split("\\.")[0];
				if (failedTestSuites.contains(ts)){
					logger.logSoapUI(CustomLevel.FAILED, 
									"<a href=" + 
									FileName.getHTMLName(directoryList[i])+">" + 
									ts + "[" +Collections.frequency(failedTestSuites, ts) + "]" + 
									"</a>");
				} else {
					logger.logSoapUI(CustomLevel.PASSED, "<a href="+FileName.getHTMLName(directoryList[i])+">"+ts+"</a>");
				}
			}
		}
		
	}

	public void afterTestSuite(ProjectRunner arg0, ProjectRunContext arg1,
			TestSuiteRunner arg2) {
		// TODO Auto-generated method stub
		
	}

	public void beforeRun(ProjectRunner arg0, ProjectRunContext arg1) {
		// TODO Auto-generated method stub
		try {
			htmlAppender = new FileAppender(new SoapUIHTMLLayout(), 
					FileName.getDirectoryLog() + File.separator + "index.html");
			
			logger.addAppender(htmlAppender);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void beforeTestSuite(ProjectRunner arg0, ProjectRunContext arg1,
			TestSuite arg2) {
		// TODO Auto-generated method stub
		
	}

}
