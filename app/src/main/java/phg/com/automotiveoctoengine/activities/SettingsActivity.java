package phg.com.automotiveoctoengine.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.services.UserService;

public class SettingsActivity extends AppCompatActivity {

    private final Context context = this;
    private final UserService userService = new UserService(this);
    final SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(context);

    final String LOW = "LOW";
    final String MEDIUM = "MEDIUM";
    final String HIGH = "HIGH";
    final int BACK = 0;
    final int FRONT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        calibrate();
        logout();
        account();
        qualitySettings();
        compressionSettings();
        frequencySettings();
        cameraFacingSettings();
    }

    private void calibrate() {
        Button calibrate_button = findViewById(R.id.button_calibrate);

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
        Button logout_button = findViewById(R.id.button_logout);

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
        Button account_button = findViewById(R.id.button_account);

        account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void qualitySettings() {
        RadioGroup imageQualityGroup = findViewById(R.id.radioGroup_imageQuality);
        RadioButton imageQualityLow = findViewById(R.id.radioButtonQuality_low);
        RadioButton imageQualityMedium = findViewById(R.id.radioButtonQuality_medium);
        RadioButton imageQualityHigh = findViewById(R.id.radioButtonQuality_high);

        String imageQuality = sharedPrefManager.getImageQuality();

        if (imageQuality.equals(LOW)) {
            imageQualityLow.setChecked(true);
        }
        else if (imageQuality.equals(MEDIUM)) {
            imageQualityMedium.setChecked(true);
        }
        else if (imageQuality.equals(HIGH)) {
            imageQualityHigh.setChecked(true);
        }

        imageQualityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButtonQuality_low) {
                    sharedPrefManager.setImageQuality(LOW);
                    Toast.makeText(context, "low image quality selected", Toast.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButtonQuality_medium) {
                    sharedPrefManager.setImageQuality(MEDIUM);
                    Toast.makeText(context, "medium image quality selected", Toast.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButtonQuality_high) {
                    sharedPrefManager.setImageQuality(HIGH);
                    Toast.makeText(context, "high image quality selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void compressionSettings() {
        RadioGroup imageCompressionGroup = findViewById(R.id.radioGroup_imageCompression);
        RadioButton imageCompressionLow = findViewById(R.id.radioButtonCompression_low);
        RadioButton imageCompressionMedium = findViewById(R.id.radioButtonCompression_medium);
        RadioButton imageCompressionHigh = findViewById(R.id.radioButtonCompression_high);

        String imageCompression = sharedPrefManager.getImageCompression();

        if (imageCompression.equals(LOW)) {
            imageCompressionLow.setChecked(true);
        }
        else if (imageCompression.equals(MEDIUM)) {
            imageCompressionMedium.setChecked(true);
        }
        else if (imageCompression.equals(HIGH)) {
            imageCompressionHigh.setChecked(true);
        }

        imageCompressionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButtonCompression_low) {
                    sharedPrefManager.setImageCompression(LOW);
                    Toast.makeText(context, "low image Compression selected", Toast.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButtonCompression_medium) {
                    sharedPrefManager.setImageCompression(MEDIUM);
                    Toast.makeText(context, "medium image Compression selected", Toast.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButtonCompression_high) {
                    sharedPrefManager.setImageCompression(HIGH);
                    Toast.makeText(context, "high image Compression selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void frequencySettings() {
        RadioGroup imageFrequencyGroup = findViewById(R.id.radioGroup_frequency);
        RadioButton imageFrequencyLow = findViewById(R.id.radioButtonFrequency_low);
        RadioButton imageFrequencyMedium = findViewById(R.id.radioButtonFrequency_medium);
        RadioButton imageFrequencyHigh = findViewById(R.id.radioButtonFrequency_high);

        String imageFrequency = sharedPrefManager.getMonitoringFrequency();

        if (imageFrequency.equals(LOW)) {
            imageFrequencyLow.setChecked(true);
        }
        else if (imageFrequency.equals(MEDIUM)) {
            imageFrequencyMedium.setChecked(true);
        }
        else if (imageFrequency.equals(HIGH)) {
            imageFrequencyHigh.setChecked(true);
        }

        imageFrequencyGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButtonFrequency_low) {
                    sharedPrefManager.setMonitoringFrequency(LOW);
                    Toast.makeText(context, "low monitoring Frequency selected", Toast.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButtonFrequency_medium) {
                    sharedPrefManager.setMonitoringFrequency(MEDIUM);
                    Toast.makeText(context, "medium monitoring Frequency selected", Toast.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButtonFrequency_high) {
                    sharedPrefManager.setMonitoringFrequency(HIGH);
                    Toast.makeText(context, "high monitoring Frequency selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cameraFacingSettings() {
        RadioGroup cameraFacingGroup = findViewById(R.id.radioGroup_chooseCamera);
        RadioButton cameraFacingBack = findViewById(R.id.radioButton_back);
        RadioButton cameraFacingFront = findViewById(R.id.radioButton_front);

        int cameraFacing = sharedPrefManager.getCameraFacing();

        if (cameraFacing == 0) {
            cameraFacingBack.setChecked(true);
        }
        else if (cameraFacing == 1) {
            cameraFacingFront.setChecked(true);
        }

        cameraFacingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButton_back) {
                    sharedPrefManager.setCameraFacing(BACK);
                    Toast.makeText(context, "Rear facing camera selected", Toast.LENGTH_SHORT).show();
                }
                else if (checkedId == R.id.radioButton_front) {
                    sharedPrefManager.setCameraFacing(FRONT);
                    Toast.makeText(context, "Front facing camera selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
