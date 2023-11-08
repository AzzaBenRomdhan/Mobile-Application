package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login2 extends AppCompatActivity {
    TextView tv ;
     Button btn_shop_product;


    SharedPreferences shared ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        tv= findViewById(R.id.email);
        shared = getSharedPreferences(Login.PREF,MODE_PRIVATE);

            tv.setText(shared.getString("EMAIL","5555"));

        //redirection lil page products
        btn_shop_product = findViewById(R.id.btn_shop_product);
        btn_shop_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this, itemsActivity.class);
                startActivity(intent);
            }
        });
    }
}