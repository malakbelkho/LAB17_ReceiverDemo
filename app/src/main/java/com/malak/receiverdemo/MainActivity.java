package com.malak.receiverdemo;


import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SkySignalReceiver skySignalReceiver;
    private boolean isSkyReceiverActive = false;

    private TextView statusTitle;
    private TextView statusDescription;
    private TextView eventTimeline;
    private Button toggleSkyReceiverButton;
    private Button sendPulseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTitle = findViewById(R.id.statusTitle);
        statusDescription = findViewById(R.id.statusDescription);
        eventTimeline = findViewById(R.id.eventTimeline);
        toggleSkyReceiverButton = findViewById(R.id.toggleSkyReceiverButton);
        sendPulseButton = findViewById(R.id.sendPulseButton);

        skySignalReceiver = new SkySignalReceiver(isAirplaneEnabled -> {
            if (isAirplaneEnabled) {
                updateStatus(
                        "Mode avion détecté",
                        "Le receiver dynamique a capté un changement système.",
                        "Événement système : mode avion activé"
                );
            } else {
                updateStatus(
                        "Connexion restaurée",
                        "Le mode avion a été désactivé.",
                        "Événement système : mode avion désactivé"
                );
            }
        });

        toggleSkyReceiverButton.setOnClickListener(view -> toggleSkyReceiver());

        sendPulseButton.setOnClickListener(view -> sendInternalPulse());
    }

    private void toggleSkyReceiver() {
        if (!isSkyReceiverActive) {
            IntentFilter skyFilter = new IntentFilter();
            skyFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

            registerReceiver(skySignalReceiver, skyFilter);

            isSkyReceiverActive = true;

            updateStatus(
                    "Receiver dynamique activé",
                    "BroadcastPulse écoute maintenant les changements du mode avion.",
                    "Receiver dynamique enregistré avec registerReceiver()"
            );

            toggleSkyReceiverButton.setText("Désactiver l’écoute avion");

        } else {
            unregisterReceiver(skySignalReceiver);

            isSkyReceiverActive = false;

            updateStatus(
                    "Receiver dynamique désactivé",
                    "L’application n’écoute plus le mode avion.",
                    "Receiver dynamique retiré avec unregisterReceiver()"
            );

            toggleSkyReceiverButton.setText("Activer l’écoute avion");
        }
    }

    private void sendInternalPulse() {
        Intent pulseIntent = new Intent(PulseEventReceiver.ACTION_PULSE_EVENT);

        pulseIntent.putExtra(
                PulseEventReceiver.EXTRA_PULSE_MESSAGE,
                "Message envoyé depuis MainActivity"
        );

        pulseIntent.setComponent(
                new ComponentName(this, PulseEventReceiver.class)
        );

        sendBroadcast(pulseIntent);

        updateStatus(
                "Broadcast custom envoyé",
                "Un événement interne a été envoyé vers PulseEventReceiver.",
                "Broadcast interne : message envoyé depuis l’Activity"
        );

        Toast.makeText(this, "Signal interne envoyé", Toast.LENGTH_SHORT).show();
    }

    private void updateStatus(String title, String description, String timelineLine) {
        statusTitle.setText(title);
        statusDescription.setText(description);

        String previousTimeline = eventTimeline.getText().toString();

        if (previousTimeline.trim().isEmpty()) {
            eventTimeline.setText("• " + timelineLine);
        } else {
            eventTimeline.setText("• " + timelineLine + "\n" + previousTimeline);
        }
    }

    @Override
    protected void onDestroy() {
        if (isSkyReceiverActive) {
            unregisterReceiver(skySignalReceiver);
            isSkyReceiverActive = false;
        }

        super.onDestroy();
    }
}