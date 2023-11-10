package org.esprit.registrationform;// RatingActivity.java

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.esprit.registrationform.R;

public class RatingActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private Button buttonSubmitRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rating);

        // Initialize views
        ratingBar = findViewById(R.id.ratingBar);
        buttonSubmitRating = findViewById(R.id.buttonMyRating);

        // Add click listener for the submit rating button
        buttonSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRating();
            }
        });
    }

    private void submitRating() {
        float userRating = ratingBar.getRating();
        // Do something with the user's rating, such as storing it in the database

        // For now, let's just display a toast message
        Toast.makeText(this, "Your Rating: " + userRating, Toast.LENGTH_SHORT).show();
    }
}

