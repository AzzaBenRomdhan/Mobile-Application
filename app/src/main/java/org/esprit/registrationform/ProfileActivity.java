package org.esprit.registrationform;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Call the function to retrieve and display user data
        displayUserData();
        // Initialize the retourButton and set the OnClickListener
        ImageView retourButton = findViewById(R.id.btn_retour);

        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the logic to go back to the previous page
                onBackPressed();
            }
        });
    }

    // Method to handle sign-out click
    public void signOutClicked(View view) {
        // Implement sign-out logic here
        // For example, you can navigate back to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    // Method to handle delete account click
    public void deleteAccountClicked(View view) {
        // Build and show a confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete your account?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()  {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked Yes, implement delete account logic here

                // Display a toast message for demonstration
                Toast.makeText(ProfileActivity.this, "Account deleted!", Toast.LENGTH_SHORT).show();

                // Navigate to the login screen or any other desired screen after deleting the account
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked No, do nothing or handle accordingly
            }
        });
        builder.show();
    }
    // Function to retrieve and display user data
    private void displayUserData() {
        // Get references to the TextView elements
        TextView usernameTextView = findViewById(R.id.nameuser);
        TextView emailTextView = findViewById(R.id.email2);
        TextView passwordTextView = findViewById(R.id.password2);
        TextView usernameAccountTextView = findViewById(R.id.username_account);

        // Use the DataBaseHelper class to retrieve user data from the database
        MyDataBaseHelper DB = new MyDataBaseHelper(this);
        Cursor cursor = DB.readAlldataUser(); // You need to implement a method to read user data

        // Check if there is at least one row in the cursor
        if (cursor.moveToFirst()) {
            // Retrieve user data from the cursor
            String username = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_USERNAME));
            String email = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_EMAIL));
            String password = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_PASSWORD));
            // Set the username in another TextView
            usernameAccountTextView.setText(username  + " ACCOUNT");
            // Update the TextView elements with user data
            usernameTextView.setText( username);
            emailTextView.setText(email);
            passwordTextView.setText(password);
        }

        // Close the cursor after use
        cursor.close();
    }

}




