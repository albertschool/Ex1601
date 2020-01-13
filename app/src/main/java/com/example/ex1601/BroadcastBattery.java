package com.example.ex1601;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.BatteryManager;

public class BroadcastBattery extends BroadcastReceiver {

    private boolean msgFlag;
    int level;
    AlertDialog.Builder adb;

    public BroadcastBattery() {
        msgFlag=false;
    }

    @Override
    public void onReceive(Context context, Intent ri) {
        int batLevel = ri.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int batStatus = ri.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        if (batLevel<=20) {
            if (!msgFlag && batStatus!=BatteryManager.BATTERY_STATUS_CHARGING) {
                msgFlag=true;
                adb=new AlertDialog.Builder(context);
                adb.setTitle("Low battery alarm");
                adb.setMessage("Low battery level: "+batLevel+"%\nPlease charge !");
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
        } else if (msgFlag) {
            msgFlag=false;
        }
    }
}
