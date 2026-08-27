package com.robotta.bot.rebuild;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

public class BotForegroundService extends Service {
    public static final String ACTION_START_OR_UPDATE = "com.robotta.bot.rebuild.START";
    public static final String ACTION_STOP = "com.robotta.bot.rebuild.STOP";
    public static final String EXTRA_TEXT = "text";
    public static final String EXTRA_CURRENT = "current";
    private static final String CHANNEL = "robotta_rebuild";

    @Override public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel c = new NotificationChannel(
                CHANNEL, "Robotta Rebuild", NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(c);
        }
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_STOP.equals(intent.getAction())) {
            stopForeground(STOP_FOREGROUND_REMOVE);
            stopSelf();
            return START_NOT_STICKY;
        }
        String text = intent == null ? "Robotta rebuild" :
                intent.getStringExtra(EXTRA_TEXT);
        NotificationCompat.Builder n = new NotificationCompat.Builder(this, CHANNEL)
                .setSmallIcon(android.R.drawable.stat_sys_upload)
                .setContentTitle("Robotta Rebuild")
                .setContentText(text == null ? "Running" : text)
                .setOngoing(true);
        startForeground(1001, n.build());
        return START_STICKY;
    }

    @Override public IBinder onBind(Intent intent) { return null; }
}
