package com.example.ex1601;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class BroadcastBattery extends BroadcastReceiver {

    private boolean highmsgFlag, lowmsgFlag;
    int highlevel, lowlevel;
    String st;

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
                    st="Low battery level: " + batLevel + "%\nPlease charge !";
                    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification noti=new Notification.Builder(context)
                            .setSmallIcon(R.drawable.bat_alarm)
                            .setContentTitle("Low battery alarm !")
                            .setContentText(st)
                            .setPriority(Notification.PRIORITY_HIGH)
                            .setAutoCancel(false)
                            .build();
                    nm.notify(0,noti);
                    Toast toast=Toast. makeText(context,st,Toast. LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast. show();
                } else if (batLevel <= lowlevel) {
                    if (!lowmsgFlag) {
                        lowmsgFlag=true;
                        st="Low battery level: " + batLevel + "%\nPlease charge now !!!";
                        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification noti=new Notification.Builder(context)
                                .setSmallIcon(R.drawable.bat_alarm)
                                .setContentTitle("Low battery alarm !!!")
                                .setContentText(st)
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setAutoCancel(false)
                                .build();
                        nm.notify(0,noti);
                        Toast toast=Toast. makeText(context,st,Toast. LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast. show();
                    }
                }
            }
        } else if (highmsgFlag || lowmsgFlag) { // only if charging !
            highmsgFlag = false;
            lowmsgFlag = false;
        }
    }
}
