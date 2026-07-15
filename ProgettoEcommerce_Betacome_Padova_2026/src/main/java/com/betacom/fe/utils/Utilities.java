package com.betacom.fe.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import com.betacom.fe.exception.AcademyException;

public class Utilities 
{
	private final static String PATTERN_DATE_1 = "dd/MM/yyyy";
	
	public static String dateToString(String pattern, LocalDate myDate) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ITALIAN);	
		return myDate.format(formatter);
	}
	
	public static String dateToString(LocalDate myDate) 
	{
	    return dateToString(PATTERN_DATE_1, myDate);
	}
	
	public static LocalDate stringToDate(String myDate) throws AcademyException
	{
		LocalDate r = null;
		try {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE_1, Locale.ITALIAN);
		r= LocalDate.parse(myDate, formatter);
			
		} catch (DateTimeParseException e) {
			throw new AcademyException("Formato della data invalido" + myDate + "formato previsto" + PATTERN_DATE_1);
		}
		return r;
	}
}
