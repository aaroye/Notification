package com.yee.roye.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotificationReciever extends BroadcastReceiver {
    @Override

    public void onReceive(Context context, Intent intent) {
        Bundle bd = intent.getExtras();
        if (!(bd.get("msg").equals("MyNotification"))) return;
        NotificationManager notifyManager =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //*點擊通知要跳轉的Activity
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= 26) {
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id,description,importance);
            channel.enableLights(true);
            channel.enableVibration(true);
            notifyManager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(context, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    //*自訂icon設定
                    .setSmallIcon(R.drawable.ic_alarm)
                    .setContentTitle(bd.get("title").toString())
                    .setContentText(bd.get("message").toString())
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .build();
            notifyManager.notify(1,notification);


        }
        else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    //*自訂icon設定
                    .setSmallIcon(R.drawable.ic_alarm)
                    .setContentTitle(bd.get("title").toString())
                    .setContentText(bd.get("message").toString())
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    //無法移除的通知
                    //.setOngoing(true)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND)
                    //延遲零秒，震動0.3秒，延遲0.3秒，震動0.3秒
                    .setVibrate(new long[]{0,300,300,300})
                    .setAutoCancel(true)
                    .setContentIntent(pi);
            notifyManager.notify(1,builder.build());
        }

    }
}
