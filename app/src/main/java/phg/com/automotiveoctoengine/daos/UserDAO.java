package phg.com.automotiveoctoengine.daos;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import phg.com.automotiveoctoengine.controllers.OkHttpHandler;
import phg.com.automotiveoctoengine.controllers.URLs;
import phg.com.automotiveoctoengine.models.ResponseError;
import phg.com.automotiveoctoengine.models.User;

public class UserDAO {

    Context context;

    public UserDAO(Context context) {
        this.context = context;
    }

    // Status: DONE!
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
            Response response = okHttpHandler.execute(request).get();
            if (response == null) {
                throw new IOException("Something went wrong");
            }
            if (!response.isSuccessful()) {
                ResponseError responseError = gson.fromJson(response.body().string(), ResponseError.class);
                throw new IOException(responseError.getError());
            }
            return true;
        } catch(ExecutionException | InterruptedException e) {
            throw new IOException("Something went wrong");
        }
    }

    // Status: DONE!
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
            Response response = okHttpHandler.execute(request).get();
            if (response == null ) {
                throw new IOException("No response from server");
            }
            if (!response.isSuccessful()) {
                return null;
            }
            return gson.fromJson(response.body().string(), User.class);

        } catch (InterruptedException | ExecutionException e) {
            throw new IOException(e.getMessage());
        }
    }
}
