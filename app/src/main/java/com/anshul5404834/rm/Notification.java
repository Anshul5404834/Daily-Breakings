package com.anshul5404834.rm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

public class Notification {
    Context context;

    public Notification(Context context) {
        this.context = context;
    }


    public void Notifications() {
        try {

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = null;

                channel = new NotificationChannel("abcd", "DailyBreaking", NotificationManager.IMPORTANCE_HIGH);

                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{200, 100, 200, 100, 200, 300, 100});
                channel.setShowBadge(true);
                channel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, channel.getAudioAttributes());
                channel.setLockscreenVisibility(1);

                notificationManager.createNotificationChannel(channel);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "abcd");

                builder.setContentText("new notification").setContentTitle("New message ")
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_focused)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
                notificationManager.notify(0, builder.build());
            }

        } catch (Exception e) {
        }
    }
}