package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class cart_item extends AppCompatActivity {
    Button  increment_btn, decrement2 ;
    int count = 1;
    CartItem cartItem;
    TextView total_qt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_item_layout);

        increment_btn = findViewById(R.id.increment_btn);
        decrement2 = findViewById(R.id.decrement2);

    }

}