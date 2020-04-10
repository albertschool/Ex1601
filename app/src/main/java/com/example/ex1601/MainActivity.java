package com.example.ex1601;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText eThighlevel, eTlowlevel;

    BroadcastBattery broadcastBat;
    int sethighLevel, setlowLevel;
    String sthighlevel, stlowlevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eThighlevel=(EditText)findViewById(R.id.eThighlevel);
        eTlowlevel=(EditText)findViewById(R.id.eTlowlevel);

        SharedPreferences settings=this.getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        sethighLevel=settings.getInt("sethighLevel",30);
        setlowLevel=settings.getInt("setlowLevel",20);

        eThighlevel.setHint(""+sethighLevel);
        eTlowlevel.setHint(""+setlowLevel);
        broadcastBat = new BroadcastBattery();

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcastBat,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastBat);
    }

    public void btn(View view) {
        sthighlevel=eThighlevel.getText().toString();
        stlowlevel=eTlowlevel.getText().toString();
        if (sthighlevel.isEmpty() || stlowlevel.isEmpty()) {
            Toast.makeText(this, "The default levels will be applied !", Toast.LENGTH_SHORT).show();
        } else {
            sethighLevel=Integer.parseInt(sthighlevel);
            setlowLevel=Integer.parseInt(stlowlevel);
            if (sethighLevel<=0 || setlowLevel<=0 || sethighLevel<=setlowLevel) {
                Toast.makeText(this, "The high level have to be bigger the the lower level\nPlease correct !", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                SharedPreferences.Editor editor=settings.edit();
                editor.putInt("sethighLevel",sethighLevel);
                editor.putInt("setlowLevel",setlowLevel);
                editor.commit();
            }
        }
    }
}
