package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class my_itemActivity extends AppCompatActivity {
    Button buy_now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_item);

        buy_now= findViewById(R.id.buy_now);
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(my_itemActivity.this, itemdetails_activity.class);
            }
        });
    }
}