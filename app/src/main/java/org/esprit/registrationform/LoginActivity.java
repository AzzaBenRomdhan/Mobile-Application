package org.esprit.registrationform;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.esprit.registrationform.MyDataBaseHelper;

import javax.mail.AuthenticationFailedException;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin;
    MyDataBaseHelper DB;

    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username1);
        password = findViewById(R.id.password1);
        btnlogin = findViewById(R.id.btnsignin1);
        DB = new MyDataBaseHelper(this);

        // Demander la permission INTERNET si elle n'est pas accordée
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {android.Manifest.permission.INTERNET};
                requestPermissions(permissions, PERMISSION_CODE);
            }
        }

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass) {
                        Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();

                        // Passer le nom d'utilisateur à HomeActivity
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("USER_NAME", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission INTERNET a été accordée
            } else {
                // La permission INTERNET a été refusée. Vous pouvez informer l'utilisateur ici.
                Toast.makeText(this, "Internet permission denied. Some features may not work.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onForgotPasswordClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_reset_password, null);
        final EditText emailEditText = dialogView.findViewById(R.id.editTextEmail);
        builder.setView(dialogView);

        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userEmail = emailEditText.getText().toString().trim();

                if (isEmailExists(userEmail)) {
                    new SendResetPasswordEmailTask().execute(userEmail);
                } else {
                    Toast.makeText(LoginActivity.this, "Email not found. Please enter the registered email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private boolean isEmailExists(String email) {
        MyDataBaseHelper DB = new MyDataBaseHelper(this);
        Cursor cursor = DB.readAlldataUser();

        if (cursor.moveToFirst()) {
            do {
                String userEmail = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_EMAIL));
                if (userEmail.equals(email)) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }

        // Fermer le curseur dans une clause finally
        cursor.close();
        return false;
    }

    private class SendResetPasswordEmailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String userEmail = params[0];
            sendResetPasswordEmail(userEmail);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(LoginActivity.this, "Password reset email sent. Check your inbox.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendResetPasswordEmail(String userEmail) {
        try {
            // Votre code pour envoyer un e-mail de réinitialisation ici...
            // Assurez-vous d'avoir activé l'accès aux applications moins sécurisées
            // pour le compte Gmail que vous utilisez, car l'accès par des applications tierces est désactivé par défaut.
            // Vous pouvez autoriser cela dans les paramètres de sécurité de votre compte Google.
            EmailSender.sendResetPasswordEmail(userEmail);
        } catch (Exception e) {
            // Gérer d'autres exceptions ici
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "Failed to send reset password email.", Toast.LENGTH_SHORT).show();
        }
    }

}
