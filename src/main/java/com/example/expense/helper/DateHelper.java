package com.example.expense.helper;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static Date stringToDate(String dateToParse) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return formatter.parse(dateToParse);
    }

    public static String dateToString(Date dateToParse) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        return formatter.format(dateToParse);
    }

}
