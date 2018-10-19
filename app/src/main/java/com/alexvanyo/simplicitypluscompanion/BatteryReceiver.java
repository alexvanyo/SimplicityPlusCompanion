package com.alexvanyo.simplicitypluscompanion;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;
import com.getpebble.android.kit.util.PebbleDictionary;
import java.util.UUID;

public class BatteryReceiver extends PebbleDataReceiver {
    private static final UUID APP_UUID;
    private static final int KEY_BATTERY_LEVEL = 0;
    private static final int KEY_REQUEST_BATTERY = 1;

    static {
        APP_UUID = UUID.fromString("d6a34819-636d-48bf-b3ee-292e48cd7da0");
    }

    public BatteryReceiver() {
        super(APP_UUID);
    }

    public void receiveData(Context context, int transactionId, PebbleDictionary data) {
        Log.d("BatteryReceiver", "received!");
        PebbleKit.sendAckToPebble(context, transactionId);
        if (data.getInteger(KEY_REQUEST_BATTERY) != null) {
            int batteryLevel = context.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("level", KEY_BATTERY_LEVEL);
            PebbleDictionary sendDictionary = new PebbleDictionary();
            sendDictionary.addInt32(KEY_BATTERY_LEVEL, batteryLevel);
            PebbleKit.sendDataToPebble(context.getApplicationContext(), APP_UUID, sendDictionary);
        }
    }
}
