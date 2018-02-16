package phg.com.automotiveoctoengine.controllers;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import phg.com.automotiveoctoengine.interfaces.NetworkStateListener;

public class OkHttpHandler extends AsyncTask<Request, Void, String> {

    private Context context;
    private boolean networkAvailable = true;

    public OkHttpHandler(Context context) {
        this.context = context;
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver(new NetworkStateListener() {
            @Override
            public void onNetworkAvailable() {
                networkAvailable = true;
            }

            @Override
            public void onNetworkUnavailable() {
                networkAvailable = false;
            }
        });
        context.getApplicationContext().registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected String doInBackground(Request... request) {
        OkHttpClient client = new OkHttpClient();

        if (networkAvailable) {
            try {
                Response response = client.newCall(request[0]).execute();
                if (response == null) {
                    return new String();
                }
                if (!response.isSuccessful()) {
                    String errorMessage = response.body().string();
                    return errorMessage;
                }
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("OkHTTP", "remote call failed");
                return null;
            }
        } else {
            Log.d("OkHTTP", "no network available");
            return null;
        }

        
    }
}