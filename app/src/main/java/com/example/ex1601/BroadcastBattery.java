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

    private boolean msgFlag;
    int level;
    AlertDialog.Builder adb;

    public BroadcastBattery() {
        msgFlag=false;
    }

    @Override
    public void onReceive(Context context, Intent ri) {
        SharedPreferences settings=context.getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        level=settings.getInt("setLevel",20);

        int batLevel = ri.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int batStatus = ri.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
        if (batLevel<=level) {
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
//                ad.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            }
        } else if (msgFlag) {
            msgFlag=false;
        }
    }
}
