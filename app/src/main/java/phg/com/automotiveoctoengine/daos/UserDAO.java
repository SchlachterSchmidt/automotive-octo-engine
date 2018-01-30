package phg.com.automotiveoctoengine.daos;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import phg.com.automotiveoctoengine.controllers.OkHttpHandler;
import phg.com.automotiveoctoengine.controllers.URLs;
import phg.com.automotiveoctoengine.models.User;

public class UserDAO {

    OkHttpHandler okHttpHandler = new OkHttpHandler();

    // Register a new user
    public boolean register(User user) {
        final MediaType MEDIA_TYPE = MediaType.parse("application/json");

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("username", user.getUsername());
            postdata.put("firstname", user.getFirstname());
            postdata.put("lastname", user.getLastname());
            postdata.put("email", user.getEmail());
            postdata.put("password", user.getPassword());
        } catch(JSONException e){
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        final Request request = new Request.Builder()
            .url(URLs.URL_USERS)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("cache-control", "no-cache")
            .build();

        try {
            Response response = okHttpHandler.execute(request).get();
            // String serverResponse = response.body().string();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
            return false;
        } catch(java.io.IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Login user
    public User login(User protoUser) {
        String credential = Credentials.basic(protoUser.getUsername(), protoUser.getPassword());

        // OkHttp needs a body to do a post, even if it's empty
        RequestBody body = RequestBody.create(null, "");

        final Request request = new Request.Builder()
                .url(URLs.URL_LOGIN)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", credential)
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = okHttpHandler.execute(request).get();
            String userJson = response.body().string();

            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            User user = gson.fromJson(userJson ,User.class);

            Log.d("Response body string", userJson);
            return user;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
