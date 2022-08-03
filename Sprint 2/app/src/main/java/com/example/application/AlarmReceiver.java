package com.example.application;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        // Notification pops up when it's time,
        // and go to IncomingActivity when user taps notification
        Intent intentIncoming = new Intent(context, IncomingActivity.class);
        intentIncoming.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentIncoming, 0);
        // Set ringtone
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "soundmemo")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("SoundMemo")
                .setContentText("Time for Task!")
                .setSound(soundUri)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());

    }
}
