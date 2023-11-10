package org.esprit.registrationform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btn_shop_product, rating_btn, comment_btn, ajout_product_btn,add_pet_btn,daycare_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // Retrieve user's name from Intent
        String userName = getIntent().getStringExtra("USER_NAME");

        // Update the TextView with the user's name
        TextView hiSalmaTextView = findViewById(R.id.email);
        hiSalmaTextView.setText("Hi " + userName);

        // Redirect to the products page
        btn_shop_product = findViewById(R.id.btn_shop_product);
        rating_btn = findViewById(R.id.rating_btn);
        comment_btn = findViewById(R.id.comment_btn);
        ajout_product_btn = findViewById(R.id.ajout_product_btn);
        add_pet_btn = findViewById(R.id.add_pet_btn);
        daycare_btn = findViewById(R.id.daycare_btn);

       /* daycare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, addPetActivity.class);
                startActivity(intent);
            }
        });*/
        add_pet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, addPetActivity.class);
                startActivity(intent);
            }
        });
        ajout_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, addProductActivity.class);
                startActivity(intent);
            }
        });
        rating_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RatingActivity.class);
                startActivity(intent);
            }
        });

        comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddCommentActivity.class);
                startActivity(intent);
            }
        });
        btn_shop_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, itemsActivity.class);
                startActivity(intent);
            }
        });
    }
}
