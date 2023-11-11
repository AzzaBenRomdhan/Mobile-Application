package org.esprit.registrationform;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
    private ArrayAdapter<String> adapter;
    private ArrayList<String> userData;
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

        // Get the ImageView for the "Edit" button
        ImageView editProfileBtn = findViewById(R.id.editProfileBtn);

        // Set an OnClickListener for the "Edit" button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the updateProfileDialog method when the "Edit" button is clicked
                updateProfileDialog();
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
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Retrieve the username from the database
                MyDataBaseHelper DB = new MyDataBaseHelper(ProfileActivity.this);
                Cursor cursor = DB.readAlldataUser(); // Assuming this method retrieves all user data

                if (cursor.moveToFirst()) {
                    String username = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_USERNAME));
                    // Add the logic to delete the item from the database
                    DB.deleteAccount(username);
                    // Display a toast message for demonstration
                    Toast.makeText(ProfileActivity.this, "Account deleted!", Toast.LENGTH_SHORT).show();

                    // Navigate to the login screen or any other desired screen after deleting the account
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity
                } else {
                    // Handle the case where no user data is found
                    Toast.makeText(ProfileActivity.this, "Error deleting account", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
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
    // Method to show a dialog for updating user information
    public void updateProfileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update");

        // Create a layout for the dialog with three text fields for username, email, and password
        View view = getLayoutInflater().inflate(R.layout.dialog_update_user, null);
        final EditText editTextUsername = view.findViewById(R.id.editTextUsername);
        final EditText editTextEmail = view.findViewById(R.id.editTextEmail);
        final EditText editTextPassword = view.findViewById(R.id.editTextPassword);

        // Retrieve the existing values from the database
        MyDataBaseHelper DB = new MyDataBaseHelper(ProfileActivity.this);
        Cursor cursor = DB.readAlldataUser();

        if (cursor.moveToFirst()) {
            String currentUsername = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_USERNAME));
            String currentEmail = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_EMAIL));
            String currentPassword = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_PASSWORD));

            // Pre-fill the EditText fields with the existing values
            editTextUsername.setText(currentUsername);
            editTextEmail.setText(currentEmail);
            editTextPassword.setText(currentPassword);
        }

        cursor.close();

        builder.setView(view);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the new values entered by the user
                String newName = editTextUsername.getText().toString().trim();
                String newMail = editTextEmail.getText().toString().trim();
                String newPwd = editTextPassword.getText().toString().trim();

                // Retrieve the current username
                String currentUsername = ((TextView) findViewById(R.id.nameuser)).getText().toString().trim();

                // Validate email format
                if (!isValidEmail(newMail)) {
                    // Show an error message or handle invalid email format
                    Toast.makeText(ProfileActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return; // Stop further processing
                }

                // Validate password length
                if (newPwd.length() < 8) {
                    // Show an error message or handle insufficient password length
                    Toast.makeText(ProfileActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    return; // Stop further processing
                }

                // Update the user in the database
                MyDataBaseHelper DB = new MyDataBaseHelper(ProfileActivity.this);
                DB.updateProfile(currentUsername, newName, newMail, newPwd);

                // Update the displayed user data
                TextView usernameTextView = findViewById(R.id.nameuser);
                TextView emailTextView = findViewById(R.id.email2);
                TextView passwordTextView = findViewById(R.id.password2);

                usernameTextView.setText(newName);
                emailTextView.setText(newMail);
                passwordTextView.setText(newPwd);
            }


        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }
    // Méthode pour valider le format de l'e-mail
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}




