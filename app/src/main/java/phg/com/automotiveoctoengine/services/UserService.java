package phg.com.automotiveoctoengine.services;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.daos.UserDAO;
import phg.com.automotiveoctoengine.models.User;

public class UserService {

    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    // Status: DONE!
    public boolean register(User user) {

        if (!validateUserInput(user)) return false;

        UserDAO userDAO = new UserDAO(context);
        SharedPrefManager prefManager = SharedPrefManager.getInstance(context);

        try{
            boolean successful = userDAO.register(user);
            if (successful) {
                prefManager.login(user);
                Toast.makeText(context, "Thanks for registering", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
        catch(IOException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Status: Done!
    public boolean login(User protoUser) {

        UserDAO userDAO = new UserDAO(context);
        try {
            User currentUser = userDAO.login(protoUser);
            if (currentUser != null) {
                // currentUser does not have plain text pw
                currentUser.setPassword(protoUser.getPassword());
                SharedPrefManager.getInstance(context).login(currentUser);
                Toast.makeText(context, "Welcome back", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(context, "Username or password not correct", Toast.LENGTH_SHORT).show();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Status: DONE!
    public void logout() {
        SharedPrefManager.getInstance(context).logout();
    }

    public void update(User user) {
        // ToDo: update
    }

    private boolean validateUserInput(User user) {
        if (    user.getFirstname().isEmpty() ||
                user.getLastname().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getUsername().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getConfirm_password().isEmpty()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if ( !(user.getPassword().equals(user.getConfirm_password())) ) {
            Toast.makeText(context, "Make sure passwords match", Toast.LENGTH_SHORT).show();
            return false;
        }

        // ToDo: password length
        // ToDo: valid email address
        return true;
    }
}
