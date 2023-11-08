package org.esprit.registrationform;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.esprit.registrationform.database.Appdatabase;
import org.esprit.registrationform.entities.User;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    SharedPreferences myPref;
    public static final String PREF = "MyPref";
    Button save, clear;
    EditText email, password;
    private Appdatabase database;
    private static final String EMAIL_PATTERN =
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pwd);
        save = findViewById(R.id.buttonSave);
        clear = findViewById(R.id.buttonClear);
        // Create the shared preferences file
        myPref = getSharedPreferences(PREF, MODE_PRIVATE);
        // Edit the shared preferences file
        SharedPreferences.Editor editor = myPref.edit();
        Intent intent = new Intent(this, Login2.class);

        if (myPref.contains("email") && myPref.contains("pwd")) {
            email.setText(myPref.getString("email", ""));
            password.setText(myPref.getString("pwd", ""));
        }

        save.setOnClickListener(e -> {
            editor.putString("EMAIL",email.getText().toString());
            editor.putString("PWD",password.getText().toString());
            editor.commit();

            String emailInput = email.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            if (isValidEmail(emailInput)) {
                User newUser = new User(emailInput, passwordInput);
                long result = Appdatabase.getInstance(this).userDAO().addUser(newUser);

                if (result != -1) {
                    // User added successfully
                    showToast("User added successfully");
                    intent.putExtra("user_email", emailInput); // Pass email to Login2
                    startActivity(intent);
                } else {
                    // Failed to add user
                    showToast("Failed to add user");
                }
            } else {
                // Invalid email format
                showToast("Invalid email format");
            }
        });

        clear.setOnClickListener(e -> {
            editor.clear();
            editor.commit();
            email.setText("");
            password.setText("");
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }
}
