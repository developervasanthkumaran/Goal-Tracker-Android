package com.dvk.presentation.AlarmReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.dvk.presentation.R;

import static com.dvk.presentation.AlarmReceiver.NotificationHelper.channelID;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       String message = intent.getStringExtra("Event");
       if(message!=null && !message.isEmpty()) {
           Log.i("message ", message);
           message += " is finished";
       }
        else message = "some task has been completed";

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb =new NotificationCompat.Builder(context, channelID)
                .setContentTitle("Task Alert!")
                .setContentText(message)
                .setColor(Color.GREEN)
                .setSmallIcon(R.drawable.ic_note);
        notificationHelper.getManager().notify(1, nb.build());
    }

}
