package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addProductActivity extends AppCompatActivity {

    EditText prodcutTitle, priceinput, description;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        prodcutTitle = findViewById(R.id.prodcutTitle);
        priceinput = findViewById(R.id.priceinput);
        description = findViewById(R.id.description);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(addProductActivity.this);
                myDB.addProduct(prodcutTitle.getText().toString().trim(),
                        priceinput.getText().toString().trim(),
                        description.getText().toString().trim());
            }
        });
    }
}