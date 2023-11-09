package org.esprit.registrationform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btn_shop_product;

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
        btn_shop_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, itemsActivity.class);
                startActivity(intent);
            }
        });
    }
}
