package com.example.ex1601;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

public class BroadcastBattery extends BroadcastReceiver {

    private boolean highmsgFlag, lowmsgFlag;
    int highlevel, lowlevel;
    String st;
    AlertDialog.Builder adb;

    public BroadcastBattery() {
        lowmsgFlag = false;
        highmsgFlag = false;
    }

//    @SuppressLint("WrongConstant")
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

                    st="Low battery level: " + batLevel + "%\nPlease charge !";
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            context)
                            // Set Icon
                            .setSmallIcon(R.drawable.bat_alarm)
                            // Set Ticker Message
                            .setTicker("ticker")
                            // Set Title
                            .setContentTitle("Low battery alarm !")
                            // Set Text
                            .setContentText(st)
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
                    Toast toast=Toast. makeText(context,st,Toast. LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast. show();
//                    Toast.makeText(context, (st), Toast.LENGTH_LONG).show();

/*
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
                    ad.show();*/
                } else if (batLevel <= lowlevel) {
                    if (!lowmsgFlag) {
                        lowmsgFlag=true;

                        st="Low battery level: " + batLevel + "%\nPlease charge now !!!";
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                context)
                                // Set Icon
                                .setSmallIcon(R.drawable.bat_alarm)
                                // Set Ticker Message
                                .setTicker("ticker")
                                // Set Title
                                .setContentTitle("Low battery alarm !!!")
                                // Set Text
                                .setContentText(st)
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
                        Toast toast=Toast. makeText(context,st,Toast. LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast. show();
//                        Toast.makeText(context, (st), Toast.LENGTH_LONG).show();

/*
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
                        ad.show();*/
                    }
                }
            }
        } else if (highmsgFlag || lowmsgFlag) { // charging !
            highmsgFlag = false;
            lowmsgFlag = false;
        }
    }
}
