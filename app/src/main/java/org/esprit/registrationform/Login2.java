package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Login2 extends AppCompatActivity {
    TextView tv ;

    SharedPreferences shared ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        tv= findViewById(R.id.textView0);
        shared = getSharedPreferences(Login.PREF,MODE_PRIVATE);

        tv.setText(shared.getString("email","0000000000000000000000"));


    }
}