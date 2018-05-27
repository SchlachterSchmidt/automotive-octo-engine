package phg.com.automotiveoctoengine.daos;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import phg.com.automotiveoctoengine.controllers.OkHttpHandler;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.controllers.URLs;
import phg.com.automotiveoctoengine.models.User;

public class UserDAO {

    private final Context context;

    public UserDAO(Context context) {
        this.context = context;
    }

    public boolean register(User user) throws IOException {

        final MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final OkHttpHandler okHttpHandler = new OkHttpHandler(context);
        final Gson gson = new Gson();

        final String postdata = gson.toJson(user);
        final RequestBody body = RequestBody.create(MEDIA_TYPE, postdata);

        final Request request = new Request.Builder()
            .url(URLs.URL_USERS)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("cache-control", "no-cache")
            .build();

        try {
            String response = okHttpHandler.execute(request).get();
            if (response == null) {
                throw new IOException();
            }
            return true;
        } catch(ExecutionException | InterruptedException e) {
            throw new IOException(e.getMessage());
       }
    }

    public User login(User protoUser) throws IOException {

        final OkHttpHandler okHttpHandler = new OkHttpHandler(context);
        final Gson gson = new Gson();
        final String credential = Credentials.basic(protoUser.getUsername(), protoUser.getPassword());

        // OkHttp needs a body to do a post, even if it's empty
        final RequestBody body = RequestBody.create(null, "");

        final Request request = new Request.Builder()
                .url(URLs.URL_LOGIN)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", credential)
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            String response = okHttpHandler.execute(request).get();
            if (response == null ) {
                throw new IOException();
            }
            return gson.fromJson(response, User.class);
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException(e.getMessage());
        }
    }

    public User update(User newUser) throws IOException {

        final OkHttpHandler okHttpHandler = new OkHttpHandler(context);
        final Gson gson = new Gson();
        final User currentUser = SharedPrefManager.getInstance(context).getUser();
        final String credential = Credentials.basic(currentUser.getUsername(), currentUser.getPassword());

        final MediaType MEDIA_TYPE = MediaType.parse("application/json");
        final String postdata = gson.toJson(newUser);
        final RequestBody body = RequestBody.create(MEDIA_TYPE, postdata);

        final Request request = new Request.Builder()
                .url(URLs.URL_USERS + "/" + currentUser.getId())
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", credential)
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            String response = okHttpHandler.execute(request).get();
            if (response == null ) {
                throw new IOException();
            }
            return gson.fromJson(response, User.class);

        } catch (InterruptedException | ExecutionException e) {
            throw new IOException(e.getMessage());
        }
    }
}
