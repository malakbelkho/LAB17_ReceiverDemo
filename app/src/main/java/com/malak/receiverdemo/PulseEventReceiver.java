package com.malak.receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PulseEventReceiver extends BroadcastReceiver {

    public static final String ACTION_PULSE_EVENT =
            "com.example.receiverdemo.action.PULSE_EVENT";

    public static final String EXTRA_PULSE_MESSAGE = "extra_pulse_message";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_PULSE_EVENT.equals(intent.getAction())) {
            String receivedMessage = intent.getStringExtra(EXTRA_PULSE_MESSAGE);

            if (receivedMessage == null || receivedMessage.trim().isEmpty()) {
                receivedMessage = "Aucun message reçu";
            }

            Toast.makeText(
                    context,
                    "Signal interne reçu : " + receivedMessage,
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}
