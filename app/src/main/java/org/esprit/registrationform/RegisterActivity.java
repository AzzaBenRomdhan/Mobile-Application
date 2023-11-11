package org.esprit.registrationform;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, repassword;
    Button signup, signin;
    MyDataBaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.btnsignup);
        signin = findViewById(R.id.btnsignin);
        DB = new MyDataBaseHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String userEmail = email.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || userEmail.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Add email validation
                    if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                        Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                    } else {
                        // Add password length validation
                        if (pass.length() < 8) {
                            Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                        } else if (pass.equals(repass)) {
                            Boolean checkuser = DB.checkusername(user);
                            if (checkuser == false) {
                                Boolean insert = DB.insertData(user, pass,userEmail);
                                if (insert == true) {
                                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public Boolean insertData(String username, String password, String email) {
        SQLiteDatabase db = DB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDataBaseHelper.COLUMN_USERNAME, username);
        contentValues.put(MyDataBaseHelper.COLUMN_PASSWORD, password);
        contentValues.put(MyDataBaseHelper.COLUMN_EMAIL, email);
        long result = db.insert(MyDataBaseHelper.TABLE_USERS, null, contentValues);
        return result != -1;
    }


}
