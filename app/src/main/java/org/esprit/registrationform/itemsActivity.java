package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class itemsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    Button buy_now;
    MyDataBaseHelper mydb;
    ArrayList<String> _id, product_title, Description,price;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemsActivity.this, addProductActivity.class);
                startActivity(intent);
            }
        });

        mydb= new MyDataBaseHelper(itemsActivity.this);
        _id = new ArrayList<>();
        product_title = new ArrayList<>();
        Description = new ArrayList<>();
        price = new ArrayList<>();

        customAdapter = new CustomAdapter(itemsActivity.this,_id, product_title,Description,price);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemsActivity.this));
        storeDataInArrays();
    }

    void  storeDataInArrays(){
        Cursor cursor = mydb.readAlldata();
        if(cursor.getCount() == 0 ){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                _id.add(cursor.getString(0));
                product_title.add(cursor.getString(1));
                Description.add(cursor.getString(2));
                price.add(cursor.getString(3));
            }
        }
    }

}