package com.geolabs.furgonclient.furgonclient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundService extends Service {

    private NotificationManager mNM;
    private final IBinder mBinder = new LocalBinder();
    Intent notificationIntent;

    private String txtTitle;
    private String txtDescription;

    public class LocalBinder extends Binder {
        BackgroundService getService() {
            return BackgroundService.this;
        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        txtTitle = getString(R.string.company_name);
        txtDescription = getString(R.string.service_started);

        Notification notification = new Notification(R.drawable.ic_stat_logo, txtDescription, System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(BackgroundService.this, 0, new Intent(BackgroundService.this, MainActivity.class), 0);
        notification.setLatestEventInfo(BackgroundService.this, txtTitle, txtDescription, contentIntent);
        mNM.notify(R.string.service_started, notification);
        notificationIntent = new Intent(this, MainActivity.class);
        Toast.makeText(getApplicationContext(), getString(R.string.service_started), Toast.LENGTH_SHORT).show();
        showNotification();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void onDestroy() {
        mNM.cancel(R.string.service_started);
        stopSelf();
    }

    private void showNotification() {
        Notification notification = new Notification(R.drawable.ic_stat_logo, txtDescription, System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, MainActivity.class), 0);
        notification.setLatestEventInfo(this, txtTitle, txtDescription, contentIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        mNM.notify(R.string.service_started, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}