package phg.com.automotiveoctoengine.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import phg.com.automotiveoctoengine.Interfaces.NetworkStateListener;
import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.controllers.NetworkStateReceiver;
import phg.com.automotiveoctoengine.services.MonitoringService;

public class HomeActivity extends AppCompatActivity {

    private ToggleButton toggle_monitor_button;
    Context context = this;

    final UpdateDisplayReceiver updateDisplayReceiver = new UpdateDisplayReceiver();
    final NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver(new NetworkStateListener() {
        @Override
        public void onNetworkAvailable() {
            toggle_monitor_button.setEnabled(true);
        }

        @Override
        public void onNetworkUnavailable() {
            toggle_monitor_button.setEnabled(false);
            Toast.makeText(context, "No network available", Toast.LENGTH_SHORT).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(updateDisplayReceiver, new IntentFilter(UpdateDisplayReceiver.ACTION_UPDATE));

        settings();
        history();
        monitor();
    }

    private void settings() {
        Button settings_button = findViewById(R.id.button_settings);
        settings_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void history() {
        Button history_button = findViewById(R.id.button_history);
        history_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                        startActivity(intent);
                    }
                }
        );

    }

    private void monitor() {
        toggle_monitor_button = findViewById(R.id.toggleButton_monitor);
        toggle_monitor_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggle_monitor_button.isChecked()) {
                            Intent startIntent = new Intent(HomeActivity.this, MonitoringService.class);
                            context.startService(startIntent);

                        }
                        else {
                            Intent stopIntent = new Intent().setAction(MonitoringService.StopReceiver.ACTION_STOP);
                            sendBroadcast(stopIntent);
                            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#ffffff"));

                        }
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        Intent pauseIntent = new Intent();
        pauseIntent.setAction(MonitoringService.PauseReceiver.ACTION_PAUSE);
        sendBroadcast(pauseIntent);
        super.onPause();
    }

    @Override
    protected void onStop() {
        Intent stopIntent = new Intent();
        stopIntent.setAction(MonitoringService.PauseReceiver.ACTION_PAUSE);
        sendBroadcast(stopIntent);
        super.onStop();
    }

    @Override
    protected void onResume() {
        Intent resumeIntent = new Intent();
        resumeIntent.setAction(MonitoringService.ResumeReceiver.ACTION_RESUME);
        sendBroadcast(resumeIntent);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkStateReceiver);
        unregisterReceiver(updateDisplayReceiver);
    }

    public class UpdateDisplayReceiver extends BroadcastReceiver {
        public static final String ACTION_UPDATE = "update";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(" HomeActivity", " onReceive");
            String colorString;

            Bundle extras = intent.getExtras();

            if (extras == null) {
                colorString = "#ffffff";
            } else {
                colorString = extras.getString("COLOR");
            }
            getWindow().getDecorView().setBackgroundColor(Color.parseColor(colorString));
        }
    }
}
