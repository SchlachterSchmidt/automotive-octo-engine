package phg.com.automotiveoctoengine.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import phg.com.automotiveoctoengine.R;

public class HomeActivity extends AppCompatActivity {

    private static Button settings_button;
    private static Button history_button;
    private static ToggleButton toggle_monitor_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        settingsButton();
        historyButton();
        toggleMonitorButton();
    }

    public void settingsButton() {
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

    public void historyButton() {
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

    public void toggleMonitorButton() {
        toggle_monitor_button = findViewById(R.id.toggleButton_monitor);
        toggle_monitor_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggle_monitor_button.isChecked()) {
                            Toast.makeText(HomeActivity.this, "Monitoring started",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(HomeActivity.this, "Monitoring stopped",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

}
