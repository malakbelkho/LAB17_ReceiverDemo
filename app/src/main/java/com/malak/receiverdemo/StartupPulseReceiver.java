package com.malak.receiverdemo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StartupPulseReceiver extends BroadcastReceiver {

    private static final String TAG = "StartupPulseReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "BOOT_COMPLETED reçu : le téléphone vient de démarrer.");
            Toast.makeText(
                    context,
                    "BroadcastPulse détecte le démarrage du téléphone",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}