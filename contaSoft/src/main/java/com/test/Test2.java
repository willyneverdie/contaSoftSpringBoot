package com.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test2 {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		Locale spanish = new Locale("es", "ES");
		String monthName = "Jqweq"; // German for march
		Date date = new SimpleDateFormat("MMMM", spanish).parse(monthName);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		System.out.println(cal.get(Calendar.MONTH));
		
		
	}

}
