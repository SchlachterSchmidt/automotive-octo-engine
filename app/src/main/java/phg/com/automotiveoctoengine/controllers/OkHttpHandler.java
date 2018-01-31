package phg.com.automotiveoctoengine.controllers;

import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by phg on 30/01/18.
 */

public class OkHttpHandler extends AsyncTask<Request, Void, Response> {

    @Override
    protected Response doInBackground(Request... request) {
        OkHttpClient client = new OkHttpClient();

        try {
            return client.newCall(request[0]).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
