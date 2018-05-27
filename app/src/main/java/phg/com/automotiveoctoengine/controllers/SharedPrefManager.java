package phg.com.automotiveoctoengine.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import phg.com.automotiveoctoengine.activities.LoginActivity;
import phg.com.automotiveoctoengine.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_USER_SESSION = "user_session";
    private static final String KEY_USER_ID = "key_user_id";
    private static final String KEY_FIRST_NAME = "key_first_name";
    private static final String KEY_LAST_NAME = "key_last_name";
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_ACTIVE = "key_active";
    private static final String KEY_ATTN_SCORE = "key_attn_score";
    private static final String KEY_LAST_CLASSIFICATION = "key_last_classification";

    private static final String SHARED_PREF_MONITORING_SETTINGS = "camera_settings";
    private static final String KEY_IMAGE_QUALITY = "image_quality";
    private static final String KEY_IMAGE_COMPRESSION = "image_compression";
    private static final String KEY_CAMERA_FACING = "use_camera";
    private static final String KEY_MONITORING_FREQUENCY = "monitoring_frequency";
    
    private final String MEDIUM = "MEDIUM";

    private static SharedPrefManager sharedPrefManager;
    private static Context context;

    private SharedPrefManager(Context context) {
        SharedPrefManager.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (sharedPrefManager == null) {
            sharedPrefManager = new SharedPrefManager(context);
        }
        return sharedPrefManager;
    }

    public void login(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_FIRST_NAME, user.getFirstname());
        editor.putString(KEY_LAST_NAME, user.getLastname());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putBoolean(KEY_ACTIVE, user.isActive());
        editor.apply();

        Log.d("", "");


    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER_SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER_SESSION, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID, 0),
                sharedPreferences.getString(KEY_FIRST_NAME, null),
                sharedPreferences.getString(KEY_LAST_NAME, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getBoolean(KEY_ACTIVE, true)
        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void setAttentionScore(float score) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        editor.putFloat(KEY_ATTN_SCORE, score);
        editor.putString(KEY_LAST_CLASSIFICATION, timeStamp);
        editor.apply();
    }

    public float getAttentionScore() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_USER_SESSION, Context.MODE_PRIVATE);
        String lastClassTime = sharedPreferences.getString(KEY_LAST_CLASSIFICATION, "1");
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // return the current attention score, or 1 if the last classification is more than 1800 seconds (30 minutes ago)
        if ( (Long.parseLong(currentTime) - Long.parseLong(lastClassTime) ) > 1800) {
            return 1;
        }
        else return sharedPreferences.getFloat(KEY_ATTN_SCORE, 1);
    }

    public void setCameraFacing(int cameraFacing) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_CAMERA_FACING, cameraFacing);
        editor.apply();
    }

    public int getCameraFacing() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        // default to the front facing camera with id 1
        return sharedPreferences.getInt(KEY_CAMERA_FACING, 1);
    }

    public void setImageQuality(String quality) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMAGE_QUALITY, quality);
        editor.apply();
    }

    public String getImageQuality() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_IMAGE_QUALITY, MEDIUM);
    }

    public void setImageCompression(String compression) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMAGE_COMPRESSION, compression);
        editor.apply();
    }

    public String getImageCompression() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_IMAGE_COMPRESSION, MEDIUM);
    }

    public void setMonitoringFrequency(String frequency) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MONITORING_FREQUENCY, frequency);
        editor.apply();
    }

    public String getMonitoringFrequency() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MONITORING_FREQUENCY, MEDIUM);
    }

    public int getMonitoringFrequencyAsInt() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_MONITORING_SETTINGS, Context.MODE_PRIVATE);
        String frequency = sharedPreferences.getString(KEY_MONITORING_FREQUENCY, MEDIUM);
        if (frequency.equals("LOW")) return 3000;
        if (frequency.equals("MEDIUM")) return 2000;
        return 1000;
    }
}
