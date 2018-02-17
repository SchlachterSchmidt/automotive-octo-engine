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

    public boolean register(User user) {

        if (!allFormFieldsFilledIn(user)) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!passwordsMatch(user)) {
            Toast.makeText(context, "Make sure passwords match", Toast.LENGTH_SHORT).show();
            return false;
        }

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

    public boolean update(User newUserDetails) {
        if (!passwordsMatch(newUserDetails)) {
            Toast.makeText(context, "Make sure passwords match", Toast.LENGTH_SHORT).show();
            return false;
        }

        UserDAO userDAO = new UserDAO(context);
        try {
            userDAO.update(newUserDetails);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void logout() {
        SharedPrefManager.getInstance(context).logout();
    }

    private boolean allFormFieldsFilledIn(User user) {
        if (    user.getFirstname().isEmpty() ||
                user.getLastname().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getUsername().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getConfirm_password().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean passwordsMatch(User user) {
        return user.getPassword().equals(user.getConfirm_password());
    }

    // ToDo: password length
    // ToDo: valid email address
}
