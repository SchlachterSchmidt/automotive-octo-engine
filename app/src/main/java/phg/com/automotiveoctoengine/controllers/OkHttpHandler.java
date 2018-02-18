package phg.com.automotiveoctoengine.controllers;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import phg.com.automotiveoctoengine.interfaces.NetworkStateListener;
import phg.com.automotiveoctoengine.models.ResponseError;

public class OkHttpHandler extends AsyncTask<Request, Void, String> {

    private List<Exception> exceptions = new ArrayList<>();
    private final Context context;
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
                    exceptions.add(new Exception("No response from server"));
                    return null;
                }
                if (!response.isSuccessful()) {
                    Gson gson = new Gson();
                    String errorMessage = response.body().string();
                    ResponseError responseError = gson.fromJson(errorMessage, ResponseError.class);
                    exceptions.add(new Exception(responseError.getError()));
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                exceptions.add(e);
                return null;
            }
        } else {
            exceptions.add(new Exception("Network not available"));
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {

        if (!exceptions.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Exception e: exceptions) {

                sb.append(e.getMessage());
                sb.append("\n");
            }
            final String messages = sb.toString();
            // in order to get the UI thread back, we use the getMainLooper method Toast any exceptions
            // that may have occurred
            Handler mainHandler = new Handler(context.getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, messages, Toast.LENGTH_SHORT).show();
                }
            };
            mainHandler.post(myRunnable);
        }
    }
}