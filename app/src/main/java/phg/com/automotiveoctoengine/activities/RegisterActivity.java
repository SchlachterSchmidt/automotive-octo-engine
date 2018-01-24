package phg.com.automotiveoctoengine.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import phg.com.automotiveoctoengine.R;

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
        submitButton();
    }

    public void submitButton() {
        first_name = findViewById(R.id.editText_first_name);
        last_name = findViewById(R.id.editText_last_name);
        email = findViewById(R.id.editText_email);
        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        confirm_password = findViewById(R.id.editText_confirm_password);
        submit_button = findViewById(R.id.button_submit);

        submit_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (    first_name.getText().toString().isEmpty() ||
                                last_name.getText().toString().isEmpty() ||
                                email.getText().toString().isEmpty() ||
                                username.getText().toString().isEmpty() ||
                                password.getText().toString().isEmpty() ||
                                confirm_password.getText().toString().isEmpty() ) {
                                    Toast.makeText(RegisterActivity.this,"Please fill in all fields",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"Thanks for registering",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );
    }
}
