package phg.com.automotiveoctoengine.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.services.UserService;

public class SettingsActivity extends AppCompatActivity {

    private Button calibrate_button;
    private Button logout_button;
    private Button account_button;
    private final UserService userService = new UserService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        calibrate();
        logout();
        account();

    }

    private void calibrate() {
        calibrate_button = findViewById(R.id.button_calibrate);

        calibrate_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SettingsActivity.this, CalibrateActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void logout() {
        logout_button = findViewById(R.id.button_logout);

        logout_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userService.logout();
                    }
                }


        );
    }

    private void account() {
        account_button = findViewById(R.id.button_account);

        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
