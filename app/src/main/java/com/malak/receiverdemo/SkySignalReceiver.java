package com.malak.receiverdemo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SkySignalReceiver extends BroadcastReceiver {

    public interface SkySignalListener {
        void onSkySignalChanged(boolean isAirplaneEnabled);
    }

    private final SkySignalListener listener;

    public SkySignalReceiver(SkySignalListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            boolean isAirplaneEnabled = intent.getBooleanExtra("state", false);

            String message = isAirplaneEnabled
                    ? "Mode avion activé : connexions suspendues"
                    : "Mode avion désactivé : connexions restaurées";

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            if (listener != null) {
                listener.onSkySignalChanged(isAirplaneEnabled);
            }
        }
    }
}
