package com.dvk.presentation.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dvk.presentation.AlarmReceiver.AlarmReceiver;

import java.util.Calendar;

public class Alarm extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Calendar calendar = (Calendar)intent.getSerializableExtra("Calendar");
        String event = (String)intent.getSerializableExtra("Event");
        boolean isToStartAlarm = (boolean)intent.getBooleanExtra("isToStartAlarm",false);
        if(calendar!=null) {
            if(isToStartAlarm)
            startAlarm(calendar, event);
            else cancelAlarm(calendar);
        }
        finish();
    }
    private void startAlarm(Calendar c,String event) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("Event",event);
        Log.i("event description  ", event);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,(int)c.getTimeInMillis(), intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int)c.getTimeInMillis(), intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
