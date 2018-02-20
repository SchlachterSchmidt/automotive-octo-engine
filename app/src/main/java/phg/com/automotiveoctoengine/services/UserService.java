package phg.com.automotiveoctoengine.services;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if (!validateNewPasswordsMatch(user.getPassword(), user.getConfirmPassword())) {
            Toast.makeText(context, "Make sure passwords match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validateEmail(user.getEmail())) {
            Toast.makeText(context, "Please enter valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validatePasswordComplexity(user.getPassword())) {
            Toast.makeText(context, "Password does not meet requirements", Toast.LENGTH_SHORT).show();
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

        if (!validateEmail(email)) {
            Toast.makeText(context, "Please enter valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        User updatedUser = SharedPrefManager.getInstance(context).getUser();
        updatedUser.setFirstname(firstName);
        updatedUser.setLastname(lastName);
        updatedUser.setEmail(email);
        updatedUser.setUsername(userName);

        return update(updatedUser);
    }

    // Done
    public boolean changePassword(String oldPassword, String newPassword, String confirmNewPassword) {

        if (!validateCurrentPassword(oldPassword)) {
            Toast.makeText(context, "Current Password incorrect", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateNewPasswordsMatch(newPassword, confirmNewPassword)) {
            Toast.makeText(context, "Make sure passwords match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!validatePasswordComplexity(newPassword)) {
            Toast.makeText(context, "Password does not meet requirements", Toast.LENGTH_SHORT).show();
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
    public Boolean deactivateAccount(Boolean isChecked) {

        if (!isChecked) {
            Toast.makeText(context, "Check box to deactivate account", Toast.LENGTH_SHORT).show();
            return false;
        }
        User updatedUser = SharedPrefManager.getInstance(context).getUser();
        updatedUser.deactivate();

        Boolean success = update(updatedUser);
        if (!success) {
            return false;
        }
        Toast.makeText(context, "Account deactivated", Toast.LENGTH_SHORT).show();
        logout();
        return true;
    }

    // Done
    private boolean allFormFieldsFilledIn(User user) {
        return !(user.getFirstname().isEmpty() ||
                user.getLastname().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getUsername().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getConfirmPassword().isEmpty());
    }

    // Done
    private boolean validateNewPasswordsMatch(String newPassword, String confirmNewPassword) {
        return newPassword.equals(confirmNewPassword);
    }

    // Done
    private boolean validateEmail(String emailStr) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    // Done
    private boolean validateCurrentPassword(String password) {
        return password.equals(SharedPrefManager.getInstance(context).getUser().getPassword());
    }

    // Done
    private boolean validatePasswordComplexity(String password) {
        // validates password meets the following minimum requirements:
        // - one digit
        // - one lower case char
        // - one upper case char
        // - one special char
        // - no space
        // at least 8 chars long
        final Pattern VALID_PASSWORD_REGEX =
                Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!£*@#$%^&_+=])(?=\\S+$).{8,}");
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }
}


