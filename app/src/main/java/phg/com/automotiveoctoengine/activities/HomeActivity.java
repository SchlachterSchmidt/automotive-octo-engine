package phg.com.automotiveoctoengine.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.controllers.CameraController;
import phg.com.automotiveoctoengine.services.MonitoringService;

public class HomeActivity extends AppCompatActivity {

    private static Button settings_button;
    private static Button history_button;
    private static ToggleButton toggle_monitor_button;

    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        settings();
        history();
        monitor();
    }

    public void settings() {
        settings_button = findViewById(R.id.button_settings);
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

    public void history() {
        history_button = findViewById(R.id.button_history);
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

    public void monitor() {
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

    /*@Override
    protected void onPause() {
        cameraController.releaseCamera();
        super.onPause();
    }

    @Override
    protected void onResume() {
        cameraController.retakeCamera();
        super.onResume();
    }

    @Override
    protected void onStop() {
        cameraController.releaseCamera();
        super.onStop();
    }*/
}
