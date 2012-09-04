package com.soap.ext.listener.logger;


import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.eviware.soapui.SoapUI;



public class CustomLogger {

	
	private Logger logger;
	//private org.apache.log4j.spi.LoggerRepository repository = new LoggerRepository(getRootLogger());
		
	public CustomLogger(String logger) {
		this.logger = Logger.getLogger(logger);
	}

	public void removeAllAppenders(){
		logger.removeAllAppenders();
	}
	
	//public static final Level COMMENT = new CustomLevel(COMMENT_INT, COMMENT_STR,3);
    public void comment(Object message) {
    	logger.log(CustomLevel.COMMENT, message);
    }
    
    public void comment(Object message, Throwable e) {
    	logger.log(CustomLevel.COMMENT, message, e);
    }
    
    //public static final Level START = new CustomLevel(START_INT,START_STR,3);
    public void start(Object message) {
    	logger.log(CustomLevel.START, message);
    }
    
    public void start(Object message, Throwable e) {
    	logger.log(CustomLevel.START, message, e);
    }

	
	//public static final Level STEP = new CustomLevel(STEP_INT,STEP_STR,3);
    public void testStep(Object message){
    	logger.log(CustomLevel.STEP, message);
    }
    
    public void testStep(Object message, Throwable e){
    	logger.log(CustomLevel.STEP, message, e);
    }
	
	
	public void info(String string) {
		logger.info(string);
	}


	public void error(String string) {
		logger.error(string);
	}


	public void error(String string, NumberFormatException e) {
		logger.error(string, e);
	}


	public void log(Level logLevel, String message) {
		logger.log(logLevel, message);
	}


	public void error(String string, Exception e) {
		logger.error(string, e);
		
	}
	
	public void logSoapUI(Level level, String message) {
		if (level.equals(CustomLevel.REQUEST)){
			logger.log (CustomLevel.REQUEST, message) ;
			SoapUI.log ( "REQUEST : " + message ) ;
		} 
		else if (level.equals(CustomLevel.RESPONSE)){
			logger.log (CustomLevel.RESPONSE, message ) ;
			SoapUI.log ( "RESPONSE : " + message ) ;
		} 
		else if (level.equals(CustomLevel.STEP)){
			logger.log (CustomLevel.STEP, message ) ;
			SoapUI.log ( "STEP : " + message ) ;
		}  else if (level.equals(CustomLevel.INFO)){
			logger.log (CustomLevel.INFO, message ) ;
			SoapUI.log ( "INFO : " + message ) ;
		}else if (level.equals(CustomLevel.ERROR)){
			logger.log (CustomLevel.ERROR, message) ;
			SoapUI.log ( "ERROR : " + message ) ;
		}else if (level.equals(CustomLevel.SCRIPT)){
			logger.log (CustomLevel.SCRIPT, message) ;
			SoapUI.log ( "SCRIPT : " + message ) ;
		}else if (level.equals(CustomLevel.START)){
			logger.log (CustomLevel.START, message) ;
			SoapUI.log ( "START : " + message ) ;
		}else if (level.equals(CustomLevel.COMMENT)){
			logger.log (CustomLevel.COMMENT, message) ;
			SoapUI.log ( "COMMENT : " + message ) ;
		}else if (level.equals(CustomLevel.FAILED)){
			logger.log (CustomLevel.FAILED, message) ;
			SoapUI.log ( "FAILED : " + message ) ;
		}else if (level.equals(CustomLevel.PASSED)){
			logger.log (CustomLevel.PASSED, message) ;
			SoapUI.log ( "PASSED : " + message ) ;
		}else if (level.equals(CustomLevel.ASSERTION)){
			logger.log (CustomLevel.ASSERTION, message) ;
			SoapUI.log ( "ASSERTION : " + message ) ;
		}else if (level.equals(CustomLevel.TEST_CASE)){
			logger.log (CustomLevel.TEST_CASE, message) ;
			SoapUI.log ( "TEST_CASE : " + message ) ;
		}else {
			logger.error("Log level not found: " + message );
			SoapUI.log ("Log level not found: " + message );
		}
	}


	public void logSoapUI(Level level, String message, Throwable e) {
		if (level.equals(CustomLevel.REQUEST)){
			logger.log (CustomLevel.REQUEST, message , e) ;
			SoapUI.log ( "REQUEST : " + message ) ;
		} 
		else if (level.equals(CustomLevel.RESPONSE)){
			logger.log (CustomLevel.RESPONSE, message , e) ;
			SoapUI.log ( "RESPONSE : " + message ) ;
		} 
		else if (level.equals(CustomLevel.STEP)){
			logger.log (CustomLevel.STEP, message , e) ;
			SoapUI.log ( "STEP : " + message ) ;
		} else if (level.equals(CustomLevel.INFO)){
			logger.log (CustomLevel.INFO, message , e) ;
			SoapUI.log ( "INFO : " + message ) ;
		}else if (level.equals(CustomLevel.ERROR)){
			logger.log (CustomLevel.ERROR, message , e) ;
			SoapUI.log ( "ERROR : " + message ) ;
		}else {
			logger.error("Log level not found: " + message );
			SoapUI.log ("Log level not found: " + message );
		}
		
	}


	public void addAppender(Appender fa) {
		logger.addAppender(fa);
		
	}
	
	
}
