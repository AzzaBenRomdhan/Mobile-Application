package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        // Récupérer les données passées depuis l'activité précédente
        String productTitle = getIntent().getStringExtra("product_title");
        String price = getIntent().getStringExtra("price"); // Assurez-vous que la clé correspond à celle utilisée dans itemdetails_activity
        String quantity = getIntent().getStringExtra("quantity");


        double quantity2 = Double.parseDouble(quantity);

        double price2 = Double.parseDouble(price)* quantity2;


        // Afficher les données dans l'activité du panier
        TextView cartTitle = findViewById(R.id.cart_title);
        TextView cartPrice = findViewById(R.id.cart_price);

        cartTitle.setText(productTitle);
        cartPrice.setText("Total Price: $" + price2);


    }
}