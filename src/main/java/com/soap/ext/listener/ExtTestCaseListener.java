package com.soap.ext.listener;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;



import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.support.TestRunListenerAdapter;
import com.eviware.soapui.model.testsuite.SamplerTestStep;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestStepResult.TestStepStatus;


import com.soap.ext.listener.logger.CustomLevel;
import com.soap.ext.listener.logger.CustomLogger;
import com.soap.ext.listener.logger.SoapUIHTMLLayout;

/*import com.lohika.listener.logger.FileName;
import com.lohika.listener.logger.XMLSoapUILayout;
import com.lohika.listener.logger.results.ResultsInfo;*/
import com.soap.ext.listener.logger.FileName;

public class ExtTestCaseListener extends TestRunListenerAdapter{

	
	private CustomLogger logger = new CustomLogger(getClass().getName());
	
	FileAppender txtAppender = null;
	FileAppender xmlAppender = null;
	FileAppender htmlAppender = null;
	int testCases = 0;
	
	public void beforeRun( TestCaseRunner testRunner, TestCaseRunContext runContext )
	{
		super.beforeRun(testRunner, runContext);
	
		
		try {

			
			if ( ! testRunner.getTestCase().getTestSuite().getName().contains("Modules")){
				txtAppender = new FileAppender(new PatternLayout(), 
						FileName.getFileNameTXT(testRunner));
				logger.removeAllAppenders();
				logger.addAppender(txtAppender);
				
				htmlAppender = new FileAppender(new SoapUIHTMLLayout(), FileName.getFileNameHTML(testRunner));
				
				logger.addAppender(htmlAppender);
			}
			logger.logSoapUI(CustomLevel.TEST_CASE, testRunner.getTestCase().getName());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  
	public void afterRun( TestCaseRunner testRunner, TestCaseRunContext runContext )
	{
		super.afterRun(testRunner, runContext);
		logger.logSoapUI(CustomLevel.TEST_CASE, "END : " + testRunner.getTestCase().getName());
	}

	public void afterStep(TestCaseRunner testRunner, TestCaseRunContext runContext, TestStepResult testStep) {
		super.afterStep(testRunner, runContext, testStep);
		
		if (testStep.getStatus() == TestStepStatus.FAILED){
			logger.logSoapUI(CustomLevel.FAILED,  testStep.getTestStep().getName());
			for (int i =0 ; i < testStep.getMessages().length; i++){
				logger.logSoapUI(CustomLevel.ASSERTION, testStep.getMessages()[i]);
			}
			
		} else {
			logger.logSoapUI(CustomLevel.STEP, testStep.getTestStep().getName());
		}
		
		if (testStep instanceof SamplerTestStep){
			SamplerTestStep ts = ( SamplerTestStep )testStep;
			logger.logSoapUI(CustomLevel.REQUEST, ts.getTestRequest().getRequestContent() );
			logger.logSoapUI(CustomLevel.RESPONSE, ts.getAssertionStatus().toString());
		}
		
		if( testStep instanceof MessageExchange ){
			MessageExchange messageExchange = (MessageExchange)testStep; 
			logger.logSoapUI(CustomLevel.REQUEST, new String (messageExchange.getRawRequestData()) );
			logger.logSoapUI(CustomLevel.RESPONSE, new String (messageExchange.getRawResponseData()) );
			
		}
		
	}

	public void beforeStep(TestCaseRunner testRunner, TestCaseRunContext runContext) {
		super.beforeStep(testRunner, runContext);
		
	}

	public void beforeStep(TestCaseRunner testRunner, TestCaseRunContext runContext, TestStep testStep) {
		super.beforeStep(testRunner, runContext, testStep);
			
	}
}
