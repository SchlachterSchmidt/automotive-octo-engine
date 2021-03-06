package phg.com.automotiveoctoengine.daos;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import phg.com.automotiveoctoengine.controllers.OkHttpHandler;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.controllers.URLs;
import phg.com.automotiveoctoengine.models.Classification;
import phg.com.automotiveoctoengine.models.User;

public class MonitoringDAO {

    public Classification classify(Context context, String imagePath) throws IOException, NetworkErrorException {

        User currentUser = SharedPrefManager.getInstance(context).getUser();
        float currentScore = SharedPrefManager.getInstance(context).getAttentionScore();
        String credential = Credentials.basic(currentUser.getUsername(), currentUser.getPassword());

        Gson gson = new Gson();
        File file = new File(imagePath);
        OkHttpHandler okHttpHandler = new OkHttpHandler(context);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data", file.getName(), RequestBody.create(MediaType.parse("image/jpeg"), file))
                .addFormDataPart("prev_score", String.valueOf(currentScore))
                .build();

        final Request request = new Request.Builder()
                .url(URLs.URL_CLASSIFIER)
                .post(body)
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Authorization", credential)
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            String response = okHttpHandler.execute(request).get();
            if (response == null) {
                throw new NetworkErrorException("No response from server");
            }
            Log.d("MonitoringDAO response", response);
            return gson.fromJson(response ,Classification.class);
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException(e.getMessage());
        }
    }
}
