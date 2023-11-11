package org.esprit.registrationform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin;
    MyDataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        btnlogin = findViewById(R.id.btnsignin1);
        DB = new MyDataBaseHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass) {
                        Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                        // Pass the user's name to HomeActivity
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("USER_NAME", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Inside your LoginActivity
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the user's email (assuming you have an email field in your layout)
                String userEmail = username.getText().toString();

                // Check if the email exists in the database
                if (DB.checkEmailExists(userEmail)) {
                    // Generate a temporary password (you may use a more secure method)
                    String temporaryPassword = generateTemporaryPassword();

                    // Update the user's password in the database
                    DB.updatePassword(userEmail, temporaryPassword);

                    // Provide feedback to the user
                    Toast.makeText(LoginActivity.this, "Temporary password sent to your email. Use it to login.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Email not found. Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to generate a temporary password (you may use a more secure method)
    private String generateTemporaryPassword() {
        // Your password generation logic here
        // Example: return UUID.randomUUID().toString().substring(0, 8);
        return "tempPassword";  // Placeholder example, replace with your logic
    }
}
