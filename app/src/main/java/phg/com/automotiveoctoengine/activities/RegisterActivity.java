package phg.com.automotiveoctoengine.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.models.User;
import phg.com.automotiveoctoengine.services.UserService;

public class RegisterActivity extends AppCompatActivity {

    private static EditText first_name;
    private static EditText last_name;
    private static EditText email;
    private static EditText username;
    private static EditText password;
    private static EditText confirm_password;
    private static Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register();
    }

    private void register() {

        first_name = findViewById(R.id.editText_first_name);
        last_name = findViewById(R.id.editText_last_name);
        email = findViewById(R.id.editText_email);
        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        confirm_password = findViewById(R.id.editText_confirm_password);
        submit_button = findViewById(R.id.button_submit);

        final UserService userService = new UserService(this);

        submit_button.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    User user = new User();
                    user.setFirstname(first_name.getText().toString());
                    user.setLastname(last_name.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setConfirm_password(confirm_password.getText().toString());

                    boolean successful = userService.register(user);

                    if (successful) {
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        );
    }
}
