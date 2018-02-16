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

    //the constants
    private static final String SHARED_PREF_NAME = "user_session";
    private static final String KEY_FIRST_NAME = "key_first_name";
    private static final String KEY_LAST_NAME = "key_last_name";
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_PASSWORD = "key_password";
    private static final String KEY_ATTN_SCORE = "key_attn_score";
    private static final String KEY_LAST_CLASSIFICATION = "key_last_classification";

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

    //method to let the user login
    //this method will store the user data in shared preferences
    public void login(User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, user.getFirstname());
        editor.putString(KEY_LAST_NAME, user.getLastname());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.apply();
    }

    //check whether user is already logged in
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_FIRST_NAME, null),
                sharedPreferences.getString(KEY_LAST_NAME, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null)
        );
    }

    //log the user out
    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void setAttentionScore(float score) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        editor.putFloat(KEY_ATTN_SCORE, score);
        editor.putString(KEY_LAST_CLASSIFICATION, timeStamp);
        editor.apply();
    }

    public float getAttentionScore() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String lastClassTime = sharedPreferences.getString(KEY_LAST_CLASSIFICATION, "1");
        String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Log.d("SPM last class time", lastClassTime);
        Log.d("SPM curr time", currentTime);
        // return the current attention score, or 1 if the last classification is more than 1800 seconds (30 minutes ago)
        if ( (Long.parseLong(currentTime) - Long.parseLong(lastClassTime) ) > 1800) {
            Log.d("SPM ", "no classification found for the past 30 min");
            return 1;
        }
        else return sharedPreferences.getFloat(KEY_ATTN_SCORE, 1);
    }
}
