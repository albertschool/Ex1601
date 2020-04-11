package com.example.ex1601;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.round;

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

        maxlevel=90;
        minlevel=15;

        SharedPreferences settings=this.getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        sethighLevel=settings.getInt("sethighLevel",30);
        setlowLevel=settings.getInt("setlowLevel",20);

        tVhighlevel.setText(""+sethighLevel);
        sBhighlevel.setProgress((int) (1.25*(sethighLevel-minlevel)));
        sBhighlevel.setOnSeekBarChangeListener(SBCL);
        tVlowlevel.setText(""+setlowLevel);
        sBlowlevel.setProgress((int) (1.25*(setlowLevel-minlevel)));
        sBlowlevel.setOnSeekBarChangeListener(SBCL);
        broadcastBat = new BroadcastBattery();

    }

    SeekBar.OnSeekBarChangeListener SBCL=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar sB, int prog, boolean fromUser) {
            if (sB==sBhighlevel) {
                sethighLevel=((int) (0.8*prog+minlevel));
                tVhighlevel.setText(""+sethighLevel);
            } else {
                setlowLevel=((int) (0.8*prog+minlevel));
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


    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcastBat,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastBat);
    }

    public void btn(View view) {
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("sethighLevel",sethighLevel);
        editor.putInt("setlowLevel",setlowLevel);
        editor.commit();
    }
}
