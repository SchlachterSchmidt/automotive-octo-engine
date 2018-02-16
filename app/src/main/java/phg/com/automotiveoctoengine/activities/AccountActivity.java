package phg.com.automotiveoctoengine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import phg.com.automotiveoctoengine.R;

public class AccountActivity extends AppCompatActivity {

    private Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }
}
