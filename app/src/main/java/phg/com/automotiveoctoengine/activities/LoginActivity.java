package phg.com.automotiveoctoengine.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.models.User;
import phg.com.automotiveoctoengine.services.UserService;

public class LoginActivity extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static Button login_button;
    private static Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }
        login();
        register();
    }
    public void login(){
        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        login_button = findViewById(R.id.button_login);
        register_button = findViewById(R.id.button_register);

        final UserService userService = new UserService(this);

        login_button.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User protoUser = new User();
                    protoUser.setUsername(username.getText().toString());
                    protoUser.setPassword(password.getText().toString());
                    boolean successful = userService.login(protoUser);
                    if (successful) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }
                }
            }
        );
    }
    public void register() {
        register_button = findViewById(R.id.button_register);

        register_button.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            }
        );
    }
}
