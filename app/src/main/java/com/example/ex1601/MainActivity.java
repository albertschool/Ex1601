package com.example.ex1601;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author		Albert Levy <albert.school2015@gmail.com>
 * @version     3.0
 * @since		13/01/2020
 * Basic application to demonstrate:
 * 1. Battery broadcast receiver
 * 2. Boot broadcast receiver
 * 3. Notifications
 */
public class MainActivity extends AppCompatActivity {

    TextView tVhighlevel, tVlowlevel;
    SeekBar sBhighlevel, sBlowlevel;

    BroadcastBattery broadcastBat;
    int maxlevel, minlevel, sethighLevel, setlowLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tVhighlevel=(TextView) findViewById(R.id.tVhighlevel);
        sBhighlevel=(SeekBar) findViewById(R.id.sBhighlevel);
        tVlowlevel=(TextView) findViewById(R.id.tVlowlevel);
        sBlowlevel=(SeekBar) findViewById(R.id.sBlowlevel);

        maxlevel=95;
        minlevel=15;

        SharedPreferences settings=this.getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        sethighLevel=settings.getInt("sethighLevel",40);
        setlowLevel=settings.getInt("setlowLevel",30);

        tVhighlevel.setText(""+sethighLevel);
        sBhighlevel.setProgress((int) ((float)(100/(maxlevel-minlevel))*(sethighLevel-minlevel)));
        sBhighlevel.setOnSeekBarChangeListener(SBCL);
        tVlowlevel.setText(""+setlowLevel);
        sBlowlevel.setProgress((int) ((float)(100/(maxlevel-minlevel))*(setlowLevel-minlevel)));
        sBlowlevel.setOnSeekBarChangeListener(SBCL);
/*
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE},MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

*/
        broadcastBat = new BroadcastBattery();

    }

    /**
     * Setting a seekbar listener to get & display the battery percentage preferred
     */
    SeekBar.OnSeekBarChangeListener SBCL=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar sB, int prog, boolean fromUser) {
            if (sB==sBhighlevel) {
                sethighLevel=(int)((float)(maxlevel-minlevel)*prog/100+minlevel);
                tVhighlevel.setText(""+sethighLevel);
            } else {
                setlowLevel=(int)((float)(maxlevel-minlevel)*prog/100+minlevel);
                tVlowlevel.setText(""+setlowLevel);
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    /**
     * Register the battery status receiver as application start
     */
    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastBat,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    /**
     * Unregister the battery status receiver if application ended
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastBat);
    }

    /**
     * Moving the activity to background if Back button pressed
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * Saving preferred battery precentage alert to SharedPref file &
     * passing the activity back
     * <p>
     * @param view
     */
    public void btn(View view) {
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("sethighLevel",sethighLevel);
        editor.putInt("setlowLevel",setlowLevel);
        editor.commit();
        moveTaskToBack(true);
    }

    /**
     * Show menu options
     * <p>
     * @param menu
     */
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Respond to the menu item selected
     * <p>
     * @param item
     */
    public boolean onOptionsItemSelected (MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.menuCredits) {
//            credits...
        } else {
            finish();
        }
        return true;
    }
}
