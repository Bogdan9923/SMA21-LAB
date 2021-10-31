package com.example.assignment5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView battery_charging_text ;
    private TextView voltage_text ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        battery_charging_text = (TextView) findViewById(R.id.battery_charging_status_id);
        voltage_text = (TextView) findViewById(R.id.battery_voltage_id);
        loadBatterySection();
    }

    private void loadBatterySection() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(batteryInfoReceiver, intentFilter);
    }
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateBatteryData(intent);
        }
    };

    private void updateBatteryData(Intent intent) {
        boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);

        if (present) {

            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int pluggedLbl = 0;

            String plug_mode = "";

            switch (plugged) {
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    plug_mode = "Charging wireless";
                    break;

                case BatteryManager.BATTERY_PLUGGED_USB:
                    plug_mode = "Charging with USB";
                    break;

                case BatteryManager.BATTERY_PLUGGED_AC:
                    plug_mode = "Charging from AC";
                    break;

                default:
                    plug_mode = "Not charging";
                    break;
            }

            // display plugged status ...
            battery_charging_text.setText(plug_mode);



            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            if (voltage > 0) {
                voltage_text.setText("Voltage : " + voltage + " mV");
            }


        } else {
            Toast.makeText(this, "No Battery present", Toast.LENGTH_SHORT).show();
        }

    }

}