package com.soap.ext.listener.logger;

import org.apache.log4j.Level;

@SuppressWarnings("serial")
public class CustomLevel extends Level{



	protected CustomLevel(int level, String levelStr, int syslogEquivalent) {
		super(level, levelStr, syslogEquivalent);
	}

	// For testcases
	public static final int COMMENT_INT = INFO_INT - 10;
	public static final int STEP_INT = INFO_INT - 11;
	public static final int START_INT = INFO_INT - 20;
	public static final int PASSED_INT = INFO_INT + 10;
	public static final int FAILED_INT = ERROR_INT-2;
	public static final int ASSERTION_INT = ERROR_INT-3;
	
	// For selenium
	public static final int FIND_INT = INFO_INT - 30;
	public static final int GOTO_INT = INFO_INT - 31;
	public static final int EDIT_INT = INFO_INT - 32;
	public static final int CLICK_INT = INFO_INT - 33;
	public static final int SCRIPT_INT = INFO_INT - 34;
	
	public static final int REQUEST_INT = INFO_INT - 40;
	public static final int RESPONSE_INT = INFO_INT - 41;
	public static final int TEST_CASE_INT = INFO_INT - 42;
	
	
	private static final String COMMENT_STR = "COMMENT";
	public static final String STEP_STR = "STEP";
	private static final String START_STR = "B_INFO";
	private static final String FIND_STR = "FIND";
	private static final String GOTO_STR = "GOTO";
	private static final String EDIT_STR = "EDIT";
	private static final String CLICK_STR = "CLICK";
	private static final String SCRIPT_STR = "SCRIPT";
	public static final String REQUEST_STR = "REQUEST";
	public static final String RESPONSE_STR = "RESPONSE";
	public static final String PASSED_STR = "PASSED";
	public static final String FAILED_STR = "FAILED";
	public static final String TEST_CASE_STR = "TEST_CASE";
	public static final String ASSERTION_STR = "ASSERTION";
	
	public static final Level COMMENT = new CustomLevel(COMMENT_INT, COMMENT_STR,3);
	public static final Level STEP = new CustomLevel(STEP_INT,STEP_STR,3);
	public static final Level START = new CustomLevel(START_INT,START_STR,3);
	
	// For selenium commands
	public static final Level FIND = new CustomLevel(FIND_INT,FIND_STR,3);
	public static final Level GOTO = new CustomLevel(GOTO_INT,GOTO_STR,3);
	public static final Level EDIT = new CustomLevel(EDIT_INT,EDIT_STR,3);
	public static final Level CLICK = new CustomLevel(CLICK_INT,CLICK_STR,3);
	public static final Level SCRIPT = new CustomLevel(SCRIPT_INT,SCRIPT_STR,3);
	
	public static final Level REQUEST = new CustomLevel(REQUEST_INT,REQUEST_STR,3);
	public static final Level RESPONSE = new CustomLevel(RESPONSE_INT,RESPONSE_STR,3);
	public static final Level PASSED = new CustomLevel(PASSED_INT,PASSED_STR,3);
	public static final Level FAILED = new CustomLevel(FAILED_INT,FAILED_STR,3);
	public static final Level TEST_CASE = new CustomLevel(TEST_CASE_INT,TEST_CASE_STR,3);
	public static final Level ASSERTION = new CustomLevel(ASSERTION_INT,ASSERTION_STR,3);
	
		
	public static Level toLevel(String sArg) {
		return toLevel(sArg, Level.DEBUG);
    }
    
    public static  Level toLevel(int val) {
    	return toLevel(val, Level.DEBUG);
  	}
	
	public static Level toLevel(int val, Level defaultLevel) {
		
		switch(val) {
		    
		    case COMMENT_INT: return CustomLevel.COMMENT;
		    case STEP_INT: return CustomLevel.STEP;
		    case START_INT: return CustomLevel.START;
		    
		    case FIND_INT: return CustomLevel.FIND;
		    case GOTO_INT: return CustomLevel.GOTO;
		    case EDIT_INT: return CustomLevel.EDIT;
		    case CLICK_INT: return CustomLevel.CLICK;
		    case SCRIPT_INT: return CustomLevel.SCRIPT;
		    case REQUEST_INT: return CustomLevel.REQUEST;
		    case RESPONSE_INT: return CustomLevel.RESPONSE;
		    case PASSED_INT: return CustomLevel.PASSED;
		    case FAILED_INT: return CustomLevel.FAILED;
		    case TEST_CASE_INT: return CustomLevel.TEST_CASE;
		    case ASSERTION_INT: return CustomLevel.ASSERTION;
		    
		    default: return Level.toLevel(val, defaultLevel);
		}
    }
	
	public static Level toLevel(String sArg, Level defaultLevel) {                 
		
		if(sArg == null)
		       return defaultLevel;
		
		String s = sArg.toUpperCase();

		if(s.equals(COMMENT_STR)) return COMMENT; 
	    if(s.equals(STEP_STR)) return STEP; 
	    if(s.equals(START_STR))  return START;
	    if(s.equals(FIND_STR))  return FIND;  
	    if(s.equals(GOTO_STR)) return GOTO;
	    if(s.equals(EDIT_STR)) return EDIT;
	    if(s.equals(CLICK_STR)) return CLICK;
	    if(s.equals(SCRIPT_STR)) return SCRIPT;
	    if(s.equals(REQUEST_STR)) return REQUEST;
	    if(s.equals(RESPONSE_STR)) return RESPONSE;
	    if(s.equals(PASSED_STR)) return PASSED;
	    if(s.equals(FAILED_STR)) return FAILED;
	    if(s.equals(TEST_CASE_STR)) return TEST_CASE;
	    if(s.equals(ASSERTION_STR)) return ASSERTION;
			    
		return Level.toLevel(sArg,defaultLevel);
	}

}

