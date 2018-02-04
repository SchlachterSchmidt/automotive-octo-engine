package phg.com.automotiveoctoengine.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.services.MonitoringService;

public class HomeActivity extends AppCompatActivity {

    private ToggleButton toggle_monitor_button;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        final IntentFilter filter = new IntentFilter(UpdateDisplayReceiver.ACTION_UPDATE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        final UpdateDisplayReceiver updateDisplayReceiver = new UpdateDisplayReceiver();
        toggle_monitor_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (toggle_monitor_button.isChecked()) {
                            Intent intent = new Intent(HomeActivity.this, MonitoringService.class);
                            context.startService(intent);
                            registerReceiver(updateDisplayReceiver, filter);
                        }
                        else {
                            Intent sIntent = new Intent();
                            sIntent.setAction(MonitoringService.StopReceiver.ACTION_STOP);
                            sendBroadcast(sIntent);
                            getWindow().getDecorView().setBackgroundColor(Color.parseColor("#ffffff"));
                            unregisterReceiver(updateDisplayReceiver);
                        }
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        Intent sIntent = new Intent();
        sIntent.setAction(MonitoringService.PauseReceiver.ACTION_PAUSE);
        sendBroadcast(sIntent);
        super.onPause();
    }

    @Override
    protected void onStop() {
        Intent sIntent = new Intent();
        sIntent.setAction(MonitoringService.PauseReceiver.ACTION_PAUSE);
        sendBroadcast(sIntent);
        super.onStop();
    }

    @Override
    protected void onResume() {
        Intent sIntent = new Intent();
        sIntent.setAction(MonitoringService.ResumeReceiver.ACTION_RESUME);
        sendBroadcast(sIntent);
        super.onResume();
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
                getWindow().getDecorView().setBackgroundColor(Color.parseColor(colorString));

            }
        }
    }
}
