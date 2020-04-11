package com.example.ex1601;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.view.WindowManager;

import androidx.core.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

public class BroadcastBattery extends BroadcastReceiver {

    private boolean highmsgFlag, lowmsgFlag;
    int highlevel, lowlevel;
    AlertDialog.Builder adb;

    public BroadcastBattery() {
        lowmsgFlag = false;
        highmsgFlag = false;
    }

    @Override
    public void onReceive(Context context, Intent ri) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        highlevel = settings.getInt("sethighLevel", 30);
        lowlevel = settings.getInt("setlowLevel", 20);

        int batLevel = ri.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batStatus = ri.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        if (batStatus != BatteryManager.BATTERY_STATUS_CHARGING) {
            if (batLevel <= highlevel) {
                if (!highmsgFlag) {
                    highmsgFlag = true;

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            context)
                            // Set Icon
                            .setSmallIcon(R.drawable.bat_alarm)
                            // Set Ticker Message
                            .setTicker("ticker")
                            // Set Title
                            .setContentTitle("Low battery alarm !")
                            // Set Text
                            .setContentText("Low battery level: \" + batLevel + \"%\\nPlease charge !")
                            // Add an Action Button below Notification
                            //.addAction(R.drawable.ic_launcher, "Action Button", pIntent)
                            // Set PendingIntent into Notification
                            //.setContentIntent(pIntent)
                            // Dismiss Notification
                            .setAutoCancel(false);
                    NotificationManager notificationmanager = (NotificationManager) context
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    // Build Notification with Notification Manager
                    notificationmanager.notify(0, builder.build());

                    adb = new AlertDialog.Builder(context);
                    adb.setTitle("1st Low battery alarm !");
                    adb.setIcon(R.drawable.bat_alarm);
                    adb.setMessage("Low battery level: " + batLevel + "%\nPlease charge !");
                    adb.setCancelable(false);
                    adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog ad = adb.create();
                    ad.show();
                } else if (batLevel <= lowlevel) {
                    if (!lowmsgFlag) {
                        lowmsgFlag=true;

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                context)
                                // Set Icon
                                .setSmallIcon(R.drawable.bat_alarm)
                                // Set Ticker Message
                                .setTicker("ticker")
                                // Set Title
                                .setContentTitle("Low battery alarm !!!")
                                // Set Text
                                .setContentText("Low battery level: \" + batLevel + \"%\\nPlease charge now !!!")
                                // Add an Action Button below Notification
                                //.addAction(R.drawable.ic_launcher, "Action Button", pIntent)
                                // Set PendingIntent into Notification
                                //.setContentIntent(pIntent)
                                // Dismiss Notification
                                .setAutoCancel(false);
                        NotificationManager notificationmanager = (NotificationManager) context
                                .getSystemService(Context.NOTIFICATION_SERVICE);
                        // Build Notification with Notification Manager
                        notificationmanager.notify(0, builder.build());

                        adb=new AlertDialog.Builder(context);
                        adb.setTitle("2nd Low battery alarm !!!");
                        adb.setIcon(R.drawable.bat_alarm);
                        adb.setMessage("Low battery level: "+batLevel+"%\nPlease charge now !!!");
                        adb.setCancelable(false);
                        adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog ad=adb.create();
                        ad.show();
                    }
                }
            }
        } else if (highmsgFlag || lowmsgFlag) { // charging !
            highmsgFlag = false;
            lowmsgFlag = false;
        }
    }
}
