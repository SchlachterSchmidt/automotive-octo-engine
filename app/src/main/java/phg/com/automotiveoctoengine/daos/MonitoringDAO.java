package phg.com.automotiveoctoengine.daos;

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
import okhttp3.Response;
import phg.com.automotiveoctoengine.controllers.OkHttpHandler;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.controllers.URLs;
import phg.com.automotiveoctoengine.models.Classification;
import phg.com.automotiveoctoengine.models.User;


public class MonitoringDAO {


    private Gson gson = new Gson();

    public Classification classify(Context context, String imagePath) {
        User currentUser = SharedPrefManager.getInstance(context).getUser();
        String credential = Credentials.basic(currentUser.getUsername(), currentUser.getPassword());

        Log.d(" FILE PATH", imagePath);
        File file = new File(imagePath);
        Log.d(" OPENED FILE", file.getName());

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("data", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), file))
                .addFormDataPart("prev_score", "1")
                .build();

        final Request request = new Request.Builder()
                .url(URLs.URL_CLASSIFIER)
                .post(body)
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Authorization", credential)
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            Response response = okHttpHandler.execute(request).get();
            String responseJson = response.body().string();

            if (!response.isSuccessful()) {
                Log.d("Response body string", responseJson);
                throw new IOException(" Unexpected code " + response);
            }

            Log.d(" MONITORINGDAO response", responseJson);
            Classification classification = gson.fromJson(responseJson ,Classification.class);

            return classification;
        } catch (InterruptedException | IOException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
