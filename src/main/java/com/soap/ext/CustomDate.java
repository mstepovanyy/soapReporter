package com.soap.ext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDate {
	static String testStep;
	static String format = "EEEEEEEEE, d-MMM-yy HH:mm:ss Z";
	static String date;
	
	private CustomDate(){
		
	}
	
	public static String getDate(String testStep, long timeShift){
		if ( !testStep.equals(CustomDate.testStep) || CustomDate.date.isEmpty()){
			CustomDate.testStep = testStep;
			Date d = new Date( (new Date()).getTime() + timeShift*1000);
			CustomDate.date = new SimpleDateFormat(format).format(d);
		}
		return CustomDate.date;
		
	}

}
