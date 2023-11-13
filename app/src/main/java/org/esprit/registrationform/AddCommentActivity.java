package org.esprit.registrationform;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Arrays;
import java.util.List;

public class AddCommentActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private EditText editTextComment;
    private Button buttonAddComment;

    private MyDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        // Initialisez les vues
        editTextComment = findViewById(R.id.editTextComment);
        buttonAddComment = findViewById(R.id.buttonAddComment);

        // Initialisez le helper de la base de données
        dbHelper = new MyDataBaseHelper(this);

        // Ajoutez un écouteur de clic pour le bouton
        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(AddCommentActivity.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Si la permission n'est pas accordée, demandez-la
                    ActivityCompat.requestPermissions(AddCommentActivity.this,
                            new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {
                    // La permission est déjà accordée, ajoutez le commentaire
                    addComment();
                }
            }
        });
    }

    private void addComment() {
        String commentaireText = editTextComment.getText().toString().trim();

        // Vérifier la présence de gros mots
        if (containsProfanity(commentaireText)) {
            Toast.makeText(this, "Comment contains profanity. Please use appropriate language.", Toast.LENGTH_SHORT).show();
        } else if (!commentaireText.isEmpty()) {
            // Ajoutez le commentaire à la base de données
            dbHelper.addCommentaire(commentaireText);

            // Créez l'intention ici, après avoir ajouté le commentaire à la base de données
            startViewCommentsListActivity();

            // Affichez un message de réussite
            Toast.makeText(this, "Comment added successfully", Toast.LENGTH_SHORT).show();

            // Envoi du SMS de confirmation
            sendSMS("Bien reçu: " + commentaireText);

            // Effacez le champ de texte
            editTextComment.getText().clear();
        } else {
            // Affichez un message d'erreur si le champ de texte est vide
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
        }
    }

    // Méthode pour vérifier la présence de gros mots dans un texte
    private boolean containsProfanity(String text) {
        // Liste de mots interdits (gros mots)
        List<String> profanityList = Arrays.asList("caca", "pipi", "loser");

        for (String profanity : profanityList) {
            if (text.toLowerCase().contains(profanity.toLowerCase())) {
                return true; // Gros mot détecté
            }
        }
        return false; // Aucun gros mot détecté
    }

    // Méthode pour démarrer l'activité ViewCommentsActivity
    private void startViewCommentsListActivity() {
        Intent intent = new Intent(AddCommentActivity.this, ViewCommentsActivity.class);
        startActivity(intent);
    }

    // Méthode pour envoyer un SMS de confirmation
    private void sendSMS(String message) {
        Log.d("SMS", "Sending SMS: " + message);

        // Remplacez le numéro de téléphone par votre propre numéro
        String phoneNumber = "97593730";

        // Vérifiez la permission d'envoyer des SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Si la permission n'est pas accordée, demandez-la
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // La permission est déjà accordée, envoyez le SMS
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Log.d("SMS", "SMS sent successfully");
            } catch (Exception e) {
                Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.e("SMS", "Error sending SMS: " + e.getMessage());
            }
        }
    }

    // Gestion de la réponse de la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("SMS", "onRequestPermissionsResult called");

        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission accordée, envoyez le SMS de confirmation
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
                sendSMS("Bien reçu"); // Appel à la méthode sendSMS après avoir obtenu la permission
            } else {
                // Permission refusée, affichez un message d'erreur
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
            // Appel à la méthode super.onRequestPermissionsResult
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
