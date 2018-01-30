package phg.com.automotiveoctoengine.services;

import android.content.Context;
import android.widget.Toast;

import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.daos.UserDAO;
import phg.com.automotiveoctoengine.models.User;

/**
 * Created by phg on 28/01/18.
 */

public class UserService {

    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public boolean register(User user) {

        // Business logic goes here

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

        else {
            UserDAO userDAO = new UserDAO();
            boolean successful = userDAO.register(user);

            if (!successful) {
                // ToDo: user registration failed, need to inform user why
                return false;
            }

            SharedPrefManager prefManager = SharedPrefManager.getInstance(context);
            prefManager.login(user);
            Toast.makeText(context, "Thanks for registering", Toast.LENGTH_SHORT).show();
            return true;

        }
    }

    public boolean login(User protoUser) {
        UserDAO userDAO = new UserDAO();
        User currentUser = userDAO.login(protoUser);
        // currentUser does not have plain text pw
        currentUser.setPassword(protoUser.getPassword());
        if (currentUser != null) {
            SharedPrefManager.getInstance(context).login(currentUser);
            Toast.makeText(context, "Welcome back", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(context,"Username or password not correct", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void logout() {
        SharedPrefManager.getInstance(context).logout();
    }

    public void update(User user) {
        // ToDo: update
    }
}
