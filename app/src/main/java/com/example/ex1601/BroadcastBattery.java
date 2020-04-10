package com.example.ex1601;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.view.WindowManager;

import static android.content.Context.MODE_PRIVATE;

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
                    adb = new AlertDialog.Builder(context);
                    adb.setTitle("1st Low battery alarm !");
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
                        adb=new AlertDialog.Builder(context);
                        adb.setTitle("2nd Low battery alarm !!!");
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
