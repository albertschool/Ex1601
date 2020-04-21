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
import android.os.Build;
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
                    NotificationCompat.Builder myNoti = new NotificationCompat.Builder(context.getApplicationContext(), "my_notify");
                    Intent tmpInt = new Intent(context.getApplicationContext(), BroadcastBattery.class);
                    PendingIntent pI = PendingIntent.getActivity(context, 0, tmpInt, 0);
                    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

                    bigText.setBigContentTitle("Low battery alarm !");
                    bigText.setSummaryText("Battery level is "+batLevel+"%");
                    myNoti.setContentIntent(pI);
                    myNoti.setSmallIcon(R.drawable.bat_alarm);
                    myNoti.setContentTitle("Low battery alarm !");
                    myNoti.setContentText(st);
                    myNoti.setPriority(Notification.PRIORITY_MAX);
                    myNoti.setStyle(bigText);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        String channelId = "my_notify";
                        NotificationChannel channel = new NotificationChannel(
                                channelId,
                                "Channel human readable title",
                                NotificationManager.IMPORTANCE_HIGH);
                        nm.createNotificationChannel(channel);
                        myNoti.setChannelId(channelId);
                    }
                    nm.notify(0, myNoti.build());

                    Toast toast=Toast. makeText(context,st,Toast. LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast. show();

                } else if (batLevel <= lowlevel) {
                    if (!lowmsgFlag) {
                        lowmsgFlag=true;
                        st="Low battery level: " + batLevel + "%\nPlease charge now !!!";

                        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationCompat.Builder myNoti = new NotificationCompat.Builder(context.getApplicationContext(), "my_notify");
                        Intent tmpInt = new Intent(context.getApplicationContext(), BroadcastBattery.class);
                        PendingIntent pI = PendingIntent.getActivity(context, 0, tmpInt, 0);
                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

                        bigText.setBigContentTitle("Low battery alarm !!!");
                        bigText.setSummaryText("Battery level is "+batLevel+"%");
                        myNoti.setContentIntent(pI);
                        myNoti.setSmallIcon(R.drawable.bat_alarm);
                        myNoti.setContentTitle("Low battery alarm !!!");
                        myNoti.setContentText(st);
                        myNoti.setPriority(Notification.PRIORITY_MAX);
                        myNoti.setStyle(bigText);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        {
                            String channelId = "my_notify";
                            NotificationChannel channel = new NotificationChannel(
                                    channelId,
                                    "Channel human readable title",
                                    NotificationManager.IMPORTANCE_HIGH);
                            nm.createNotificationChannel(channel);
                            myNoti.setChannelId(channelId);
                        }
                        nm.notify(0, myNoti.build());
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
