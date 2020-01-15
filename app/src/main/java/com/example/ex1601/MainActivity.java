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

    EditText eT;
    BroadcastBattery broadcastBat;
    int setLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eT=(EditText)findViewById(R.id.eT);

        setLevel=20;
        broadcastBat = new BroadcastBattery();

    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcastBat,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastBat); }

    public void btn(View view) {
        String st=eT.getText().toString();
        setLevel=Integer.parseInt(st);
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=settings.edit();
        editor.putInt("setLevel",setLevel);
        editor.commit();
    }
}
