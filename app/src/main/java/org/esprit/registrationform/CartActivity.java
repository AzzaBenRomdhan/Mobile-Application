package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    ImageButton retour_items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        retour_items=findViewById(R.id.retour_items);
        retour_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, itemdetails_activity.class);
                startActivity(intent);
            }
        });
        // Traiter les données du curseur et les stocker dans une liste de produits du panier
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(this);
        Cursor cursor = dbHelper.readAlldataCart();

        // Initialiser la liste de produits du panier
        cartItemList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("product_title");
            int descriptionIndex = cursor.getColumnIndex("description");
            int priceIndex = cursor.getColumnIndex("price");
            int quantityIndex = cursor.getColumnIndex("quantity");

            do {
                if (!cursor.isAfterLast()) {
                    String productTitle = cursor.getString(titleIndex);
                    String description = cursor.getString(descriptionIndex);
                    int price = cursor.getInt(priceIndex);
                    int quantity = cursor.getInt(quantityIndex);

                    cartItemList.add(new CartItem(productTitle, description, price, quantity));
                }
            } while (cursor.moveToNext());

            cursor.close();

            // Update the TextView with the total price
            double totalPrice = 0.0;
            for (CartItem cartItem : cartItemList) {
                totalPrice += cartItem.getPrice();
            }

            TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
            totalPriceTextView.setText("Total: $" + String.format("%.2f", totalPrice));

            // Configurer le RecyclerView et l'adapter
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            cartAdapter = new CartAdapter(cartItemList, this);
            recyclerView.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
        }
    }

}