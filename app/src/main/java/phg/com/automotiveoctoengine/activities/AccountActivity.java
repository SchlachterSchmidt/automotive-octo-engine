package phg.com.automotiveoctoengine.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.controllers.SharedPrefManager;
import phg.com.automotiveoctoengine.models.User;
import phg.com.automotiveoctoengine.services.UserService;

public class AccountActivity extends AppCompatActivity {

    private Context context = this;

    EditText first_name;
    EditText last_name;
    EditText email;
    EditText username;
    EditText old_password;
    EditText new_password;
    EditText confirm_password;
    CheckBox deactivate_account;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        update();
    }

    private void update() {

        first_name = findViewById(R.id.editText_first_name);
        last_name  = findViewById(R.id.editText_last_name);
        email = findViewById(R.id.editText_email);
        username = findViewById(R.id.editText_username);
        old_password = findViewById(R.id.editText_old_password);
        new_password = findViewById(R.id.editText_new_password);
        confirm_password = findViewById(R.id.editText_confirm_new_password);
        deactivate_account = findViewById(R.id.checkBox);
        submit_button = findViewById(R.id.button_submit);

        final UserService userService = new UserService(context);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User userDetails = new User();
                userDetails.setFirstname(first_name.getText().toString());
                userDetails.setLastname(last_name.getText().toString());
                userDetails.setEmail(email.getText().toString());
                userDetails.setUsername(username.getText().toString());
                userDetails.setPassword(new_password.getText().toString());
                userDetails.setConfirm_password(confirm_password.getText().toString());

                if (deactivate_account.isChecked()) {
                    userDetails.deactivate();
                }
                if (!confirm_password.getText().toString().equals(SharedPrefManager.getInstance(context).getUser().getPassword())) {
                    Toast.makeText(context, "Current password not correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean success = userService.update(userDetails);

                    if (success) {
                        if (deactivate_account.isChecked()) {
                            Toast.makeText(context, "Account deactivated", Toast.LENGTH_SHORT).show();
                            userService.logout();
                        }
                        Toast.makeText(context, "Account updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
