package phg.com.automotiveoctoengine.daos;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import phg.com.automotiveoctoengine.models.User;

/**
 * Created by phg on 28/01/18.
 */

public class UserDAO {

    // dummy user account list
    private List<User> userList = new ArrayList<>();

    public boolean register(User user) {
        // ToDo: use HTTPController to make request to API to save user

        userList.add(user);
        return true;
    }

    public User login(User protoUser) {
        // ToDo: use HTTPController to check username and password match and exist in DB
        // dummy method to log user in
        for (User u : userList) {
            if (u.getUsername().equals(protoUser.getUsername()) &&
                    u.getPassword().equals(protoUser.getPassword())) {
                return u;
            }
        }
        return null;
    }
}
