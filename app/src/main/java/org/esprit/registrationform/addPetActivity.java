package org.esprit.registrationform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addPetActivity extends AppCompatActivity {
    EditText name_input, breed_input, gender_input, age_input, color_input,
            height_input, weight_input;
    Button btnAddPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpet);

        name_input = findViewById(R.id.editTextText4);
        breed_input = findViewById(R.id.editTextText6);
        gender_input = findViewById(R.id.editTextText7);
        age_input = findViewById(R.id.editTextText8);
        color_input = findViewById(R.id.editTextText9);
        height_input = findViewById(R.id.editTextText10);
        weight_input = findViewById(R.id.editTextText12);
        btnAddPet = findViewById(R.id.button6);
        View btnViewPets = findViewById(R.id.btnViewPets);


        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ageText = age_input.getText().toString().trim();
                String heightText = height_input.getText().toString().trim();

                if (!ageText.isEmpty() && !heightText.isEmpty()) {
                    try {
                        int ageValue = Integer.parseInt(ageText);
                        int heightValue = Integer.parseInt(heightText);

                        MyDataBaseHelper DB = new MyDataBaseHelper(addPetActivity.this);
                        DB.addPet(
                                name_input.getText().toString().trim(),
                                breed_input.getText().toString().trim(),
                                gender_input.getText().toString().trim(),
                                ageValue,
                                color_input.getText().toString().trim(),
                                heightValue,
                                Integer.valueOf(weight_input.getText().toString().trim())
                        );

                        btnViewPets.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(addPetActivity.this, ListPetsActivity.class);
                                startActivity(intent);
                            }
                        });

                        // Ajout réussi, rediriger vers PetDetailActivity
                        Intent intent = new Intent(addPetActivity.this, ListPetsActivity.class);
                        startActivity(intent);

                    } catch (NumberFormatException e) {
                        // Gérer l'exception, par exemple, afficher un message à l'utilisateur
                        Toast.makeText(addPetActivity.this, "Veuillez saisir des valeurs numériques valides pour l'âge et la hauteur", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Gérer le cas où les champs d'âge ou de hauteur sont vides
                    Toast.makeText(addPetActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}