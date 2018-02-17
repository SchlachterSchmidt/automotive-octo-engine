package phg.com.automotiveoctoengine.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.daos.UserDAO;
import phg.com.automotiveoctoengine.models.User;

public class UserService {

    private final Context context;

    public UserService(Context context) {
        this.context = context;
    }

    // Done
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

    // Done
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
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Done
    private boolean update(User user) {
        UserDAO userDAO = new UserDAO(context);
        try {
            User currentUser = userDAO.update(user);
            currentUser.setPassword(user.getPassword());
            SharedPrefManager.getInstance(context).login(currentUser);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // Done
    public boolean updateUserDetails(String firstName, String lastName, String email, String userName) {

        User updatedUser = SharedPrefManager.getInstance(context).getUser();
        Log.d("1 updateUserDetails", "currently logged in user details\n" + updatedUser.toString());
        updatedUser.setFirstname(firstName);
        updatedUser.setLastname(lastName);
        updatedUser.setEmail(email);
        updatedUser.setUsername(userName);

        Log.d("2 updateUserDetails", "updated user details\n" + updatedUser.toString());
        return update(updatedUser);
    }

    // Done
    public boolean changePassword(String oldPassword, String newPassword, String confirmNewPassword) {

        if (!oldPassword.equals(SharedPrefManager.getInstance(context).getUser().getPassword())) {
            Toast.makeText(context, "Current Password incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(context, "Make sure passwords match", Toast.LENGTH_SHORT).show();
            return false;
        }

        User updatedUser = SharedPrefManager.getInstance(context).getUser();
        updatedUser.setPassword(newPassword);
        Boolean success = update(updatedUser);
        if (success) {
            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
        else return false;
    }

    // Done
    public void logout() {
        SharedPrefManager.getInstance(context).logout();
    }

    // Done
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

    // Done
    private boolean passwordsMatch(User user) {
        return user.getPassword().equals(user.getConfirm_password());
    }

    // ToDo: password length
    // ToDo: valid email address
}
