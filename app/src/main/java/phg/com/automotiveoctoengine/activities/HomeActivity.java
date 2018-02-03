package phg.com.automotiveoctoengine.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        toggle_monitor_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggle_monitor_button.isChecked()) {
                            Intent intent = new Intent(HomeActivity.this, MonitoringService.class);
                            context.startService(intent);
                        }
                        else {
                            Intent sIntent = new Intent();
                            sIntent.setAction(MonitoringService.StopReceiver.ACTION_STOP);
                            sendBroadcast(sIntent);
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
}
