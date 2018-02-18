package phg.com.automotiveoctoengine.daos;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Credentials;
import okhttp3.Request;
import phg.com.automotiveoctoengine.controllers.OkHttpHandler;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.controllers.URLs;
import phg.com.automotiveoctoengine.models.HistoryRecord;
import phg.com.automotiveoctoengine.models.HistoryResponse;
import phg.com.automotiveoctoengine.models.User;

public class HistoryDAO {
    private final Context context;
    public HistoryDAO(Context context) {
        this.context = context;
    }

    public List<HistoryRecord> getRecords() throws IOException {

        final OkHttpHandler okHttpHandler = new OkHttpHandler(context);
        final Gson gson = new Gson();
        final User currentUser = SharedPrefManager.getInstance(context).getUser();
        final String credential = Credentials.basic(currentUser.getUsername(), currentUser.getPassword());

        final Request request = new Request.Builder()
                .url(URLs.URL_CLASSIFIER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", credential)
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            String response = okHttpHandler.execute(request).get();
            if (response == null) {
                throw new IOException();
            }
            return gson.fromJson(response, HistoryResponse.class).getResults();

        } catch (InterruptedException | ExecutionException e) {
            throw new IOException(e.getMessage());
        }
    }
}
