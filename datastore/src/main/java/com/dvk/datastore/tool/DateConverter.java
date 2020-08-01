package com.dvk.datastore.tool;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverter {

    static DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    @TypeConverter
    public static Calendar fromTimestamp(String value) {
        if (value != null) {
            try {
                Date date = df.parse(value);
                Calendar cal = Calendar.getInstance();
                assert date != null;
                cal.setTime(date);
                cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
                return cal;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @TypeConverter
    public static String dateToTimestamp(Calendar value) {
        String date;
        if(value!=null) {
            date = value.get(Calendar.DAY_OF_MONTH) + "-" + value.get(Calendar.MONTH) + "-" + value.get(Calendar.YEAR);
            return date;
        }
        return null;
    }
}