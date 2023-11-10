package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class itemdetails_activity extends AppCompatActivity {

    TextView get_title,get_Description, get_price, total_qt;
    String  _id, product_title, Description, price;
    Button buy_now, increment, decrement ;
    ImageButton retour_items;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdetails);

        get_title=findViewById(R.id.get_title);
        get_Description=findViewById(R.id.get_Description);
        get_price=findViewById(R.id.get_price);
        buy_now=findViewById(R.id.buy_now);
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //qunatité
        increment=findViewById(R.id.increment);
        decrement=findViewById(R.id.decrement);
        total_qt=findViewById(R.id.total_qt);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                total_qt.setText(""+count);
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<=0) count=0;
                else count--;
                total_qt.setText(""+count);

            }
        });
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 // Créer une intention pour passer à l'activité du panier avec les données nécessaires
                Intent intent = new Intent(itemdetails_activity.this, CartActivity.class);
                intent.putExtra("product_title", product_title);
                intent.putExtra("price", String.valueOf(price));
                intent.putExtra("quantity", String.valueOf(count));
                startActivity(intent);
            }
        });
        //retour page list products
        retour_items=findViewById(R.id.retour_items);
        retour_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemdetails_activity.this, itemsActivity.class);
                startActivity(intent);
            }
        });

         getandSetIntentData();
    }




    void getandSetIntentData() {
        if (getIntent().hasExtra("_id") &&
                getIntent().hasExtra("product_title") &&
                getIntent().hasExtra("Description") &&
                getIntent().hasExtra("price")) {

            // Getting data from intent
            _id = getIntent().getStringExtra("_id");
            product_title = getIntent().getStringExtra("product_title");
            Description = getIntent().getStringExtra("Description");
            price = getIntent().getStringExtra("price");

            // Set the data to TextViews
            get_title.setText(product_title);
            get_Description.setText(Description);
            get_price.setText(price);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }

}