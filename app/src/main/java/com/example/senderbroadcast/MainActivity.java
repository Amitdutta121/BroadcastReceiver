package com.example.senderbroadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver batteryBroadcastReceiver;
    IntentFilter intentBattary;

    public TextView textView;
    public TextView batteryState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.data);
        batteryState = findViewById(R.id.batteryState);
        intentFilterAndBroadcast();

    }

    private void intentFilterAndBroadcast(){
        intentBattary = new IntentFilter();
        intentBattary.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                    float batteryPct = level * 100 / (float)scale;
                    batteryState.setText(batteryPct+"%");
                }
            }
        };
    }

    public void sendBroadcast(View v) {
        Intent intent = new Intent("com.example.EXAMPLE_ACTION");
        intent.putExtra("com.example.EXTRA_TEXT", "Broadcast received");
        sendBroadcast(intent);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String receivedText = intent.getStringExtra("com.example.EXTRA_TEXT");
            textView.setText(receivedText);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.example.EXAMPLE_ACTION");
        registerReceiver(broadcastReceiver, intentFilter);
        registerReceiver(batteryBroadcastReceiver, intentBattary);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(batteryBroadcastReceiver);
    }
}
