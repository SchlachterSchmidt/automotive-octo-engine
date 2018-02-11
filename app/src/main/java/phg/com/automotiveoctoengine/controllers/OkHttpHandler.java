package phg.com.automotiveoctoengine.controllers;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import phg.com.automotiveoctoengine.Interfaces.NetworkStateListener;

public class OkHttpHandler extends AsyncTask<Request, Void, Response> {

    Context context;
    boolean networkAvailable = false;

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

    public OkHttpHandler() {

    }

    @Override
    protected Response doInBackground(Request... request) {
        OkHttpClient client = new OkHttpClient();


        if (networkAvailable) {
            try {
                return client.newCall(request[0]).execute();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("OkHTTP", "remote call failed");
                return null;
            }
        } else {
            Log.d("OkHTTP", "no network available");

        }
        return null;
    }
}