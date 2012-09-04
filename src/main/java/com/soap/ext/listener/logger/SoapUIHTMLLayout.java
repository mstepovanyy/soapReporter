package com.soap.ext.listener.logger;


import org.apache.log4j.HTMLLayout;
import org.apache.log4j.spi.LoggingEvent;  

public class SoapUIHTMLLayout extends HTMLLayout{

	public SoapUIHTMLLayout(){
		super();
	}
	
	String failedColor = "#FFBFBF";
	String passedColor = "#E2FFED";
	String testCaseColor = "#D6DAFF";
	/** Override HTMLLayout's format() method */
	
	// 
	
	  
	public String format(LoggingEvent event)  
	{  
	String record = super.format(event); // Get the log record in the default HTMLLayout format.  
	
	if (record.contains(CustomLevel.FAILED_STR)){
		record = setBgColor(record, failedColor);
	} else if (record.contains(CustomLevel.PASSED_STR)){
		record = setBgColor(record, passedColor);
	} else if (record.contains(CustomLevel.STEP_STR)){
		record = setBgColor(record, passedColor);
	} else if (record.contains(CustomLevel.TEST_CASE_STR)){
		record = setBgColor(record, testCaseColor);
	} else if (record.contains(CustomLevel.REQUEST_STR) || record.contains(CustomLevel.RESPONSE_STR)){
		record = record.trim();
		record = record.replaceAll("<tr>\\W<td>", "<tr><td>");
		record = record.replaceAll("td>\\W<td", "td><td");
		record = record.replaceAll("td>\\W</tr>", "td></tr>");
		record = record.replaceAll("\n", "<br>");
	}
	
	
	if (record.split("\\/td")[3].contains("Test suite") || record.split("\\/td")[3].contains("Test case")){
		record = record.replace("&lt;", "<");
		record = record.replace("&gt;", ">");
	}
	
	
		
	
	
	StringBuffer buffer = new StringBuffer(record);   
			
	return buffer.toString(); // Return the log record with the desired timestamp format.  
	}
	
	private String setBgColor(String record, String backgroundColor) {
		String tdRegExp = "(<td[^>]*)>";
		record = record.replaceAll(tdRegExp, "$1 bgcolor=\"" + backgroundColor + "\">");
		return record;
	}
	
}
