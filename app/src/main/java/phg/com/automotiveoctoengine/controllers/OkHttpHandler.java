package phg.com.automotiveoctoengine.controllers;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by phg on 30/01/18.
 */

public class OkHttpHandler extends AsyncTask<Request, Void, Response> {

    @Override
    protected Response doInBackground(Request... request) {
        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request[0]).execute();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
