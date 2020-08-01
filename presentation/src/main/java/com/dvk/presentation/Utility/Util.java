package com.dvk.presentation.Utility;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Util
{
   public static int MAIN_REQUEST_CODE = 5;
   public static int CREATE_STORY_REQUEST_CODE = 6;
   public static int CREATE_NOTE_REQUEST_CODE = 7;

   public static String dateFormatter(Calendar calendar){
      return new SimpleDateFormat("d MMM yyyy",Locale.US).format(calendar.getTime());
   }

   public static String timeFormatter(Calendar calendar) throws ParseException {

        return new SimpleDateFormat("h:mm a",Locale.US).format(calendar.getTime());
   }

   public static String getRemainingTime(Calendar calendarDate,Calendar calendar){
      calendar.set(Calendar.YEAR,calendarDate.get(Calendar.YEAR));
      calendar.set(Calendar.MONTH,calendarDate.get(Calendar.MONTH));
      calendar.set(Calendar.DAY_OF_MONTH,calendarDate.get(Calendar.DAY_OF_MONTH));
      long cur=Calendar.getInstance().getTimeInMillis();
      long tim = calendar.getTimeInMillis();
      long millis =tim-cur;
      if(tim < cur){
         millis = ((tim + 86400000) - cur);
      }
      long diffMinutes = millis / (60 * 1000) % 60;
      long diffHours = millis / (60 * 60 * 1000) % 24;
      long diffDays = millis / (24 * 60 * 60 * 1000);
      Log.i("millis ",String.valueOf(calendar.getTime()));
      Log.i("millis ",String.valueOf(millis));
      String day = diffDays==1||diffDays==0?"day":"days";
      return diffDays+" "+day +"\n"+diffHours+" hrs"+" "+diffMinutes+" min"+"\n"+"more";
   }
}
