package phg.com.automotiveoctoengine.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.models.User;
import phg.com.automotiveoctoengine.services.UserService;

public class LoginActivity extends AppCompatActivity {

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

    // Done
    private void login(){
        final EditText username = findViewById(R.id.editText_username);
        final EditText password = findViewById(R.id.editText_password);
        Button login_button = findViewById(R.id.button_login);

        final Context context = this;
        final UserService userService = new UserService(context);

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

    private void register() {
        Button register_button = findViewById(R.id.button_register);

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
