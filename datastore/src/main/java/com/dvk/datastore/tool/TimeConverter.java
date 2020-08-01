package com.dvk.datastore.tool;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeConverter {
    @TypeConverter
    public static Calendar fromTimestamp(String value) {
        if (value != null) {
            try {
                Date date =  new SimpleDateFormat("HH:mm", Locale.ENGLISH).parse(value);
                Calendar calendar = Calendar.getInstance();
                assert date != null;
                calendar.setTime(date);
                return calendar;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @TypeConverter
    public static String dateToTimestamp(Calendar value) {
        String time;
        if(value!=null) {
            String AM_PM;
            if(value.get(Calendar.HOUR_OF_DAY)<12){
                AM_PM="AM";
            }
            else AM_PM="PM";
            time = value.get(Calendar.HOUR_OF_DAY) + ":" + value.get(Calendar.MINUTE)+" "+AM_PM;;
            return time;
        }
        return null;
    }
}
