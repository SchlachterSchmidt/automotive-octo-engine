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

    private final Context context = this;

    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private EditText username;
    private EditText old_password;
    private EditText new_password;
    private EditText confirm_password;
    private CheckBox deactivate_account;

    private Button submit_update_user_details;
    private Button submit_change_password;
    private Button submit_deactivate_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        updateUserDetails();
        changePassword();
        deactivateAccount();
    }

    // done
    private void updateUserDetails() {

        User currentUser = SharedPrefManager.getInstance(context).getUser();
        final UserService userService = new UserService(context);

        first_name = findViewById(R.id.editText_first_name);
        last_name  = findViewById(R.id.editText_last_name);
        email = findViewById(R.id.editText_email);
        username = findViewById(R.id.editText_username);
        submit_update_user_details = findViewById(R.id.button_updateAccount);

        first_name.setText(currentUser.getFirstname());
        last_name.setText(currentUser.getLastname());
        email.setText(currentUser.getEmail());
        username.setText(currentUser.getUsername());

        submit_update_user_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String firstName = first_name.getText().toString();
                final String lastName = last_name.getText().toString();
                final String mail = email.getText().toString();
                final String userName = username.getText().toString();

                Boolean success = userService.updateUserDetails(firstName, lastName, mail, userName);

                if (success) {

                    Toast.makeText(context, "Account updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // done
    private void changePassword() {

        final UserService userService = new UserService(context);

        old_password = findViewById(R.id.editText_old_password);
        new_password = findViewById(R.id.editText_new_password);
        confirm_password = findViewById(R.id.editText_confirm_new_password);
        submit_change_password = findViewById(R.id.button_changePassword);

        submit_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String oldPassword = old_password.getText().toString();
                final String newPassword = new_password.getText().toString();
                final String confirmPassword = confirm_password.getText().toString();

                Boolean success = userService.changePassword(oldPassword, newPassword, confirmPassword);

                if (success) {
                    Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Done
    private void deactivateAccount() {

        final UserService userService = new UserService(context);

        deactivate_account = findViewById(R.id.checkBox_deactivateAccount);
        submit_deactivate_button = findViewById(R.id.button_submit_deactivate);

        submit_deactivate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean isChecked = deactivate_account.isChecked();
                Boolean success = userService.deactivateAccount(isChecked);
                if (success) {
                    Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
