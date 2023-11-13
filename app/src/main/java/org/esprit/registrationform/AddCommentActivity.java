package org.esprit.registrationform;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class AddCommentActivity extends AppCompatActivity {

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
                addComment();
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
            // Affichez un message de réussite
            Toast.makeText(this, "Comment added successfully", Toast.LENGTH_SHORT).show();
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

}
