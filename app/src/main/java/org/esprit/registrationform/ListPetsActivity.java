package org.esprit.registrationform;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListPetsActivity extends AppCompatActivity {
    private ListView listViewPets;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> petData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_pet);

        // Initialize the ListView
        listViewPets = findViewById(R.id.listViewPets);

        // Initialize the adapter
        petData = getPetData();
        adapter = new ArrayAdapter<>(this, R.layout.list_item_pet, R.id.textViewPetName, petData);

        // Bind the adapter to the ListView
        listViewPets.setAdapter(adapter);

        // Add a click listener to the ListView
        listViewPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                // Show a dialog for actions (update or delete)
                AlertDialog.Builder builder = new AlertDialog.Builder(ListPetsActivity.this);
                builder.setMessage("What do you want to do?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Logic for deleting the item from the database and updating the list
                        deletePetDialog(position);
                    }
                });
                builder.setNegativeButton("Cancel", null);

                // Add a button for updating the pet
                builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call the updatePetDialog method when the user chooses to update
                        updatePetDialog(position);
                    }
                });

                builder.show();
            }
        });
    }

    // Logic to retrieve data from the database
    private ArrayList<String> getPetData() {
        ArrayList<String> petData = new ArrayList<>();

        // Use the DataBaseHelper class to retrieve data from the database
        MyDataBaseHelper DB = new MyDataBaseHelper(this);
        Cursor cursor = DB.readAlldataPET();

        // Iterate through the cursor to retrieve data and add it to the list
        while (cursor.moveToNext()) {
            String petName = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_NAME));
            String breed = cursor.getString(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_BREED));
            String displayText = petName + " - " + breed;
            petData.add(displayText);
        }

        // Close the cursor after use
        cursor.close();

        return petData;
    }

    // Method to show a dialog for updating pet information
    private void updatePetDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update");

        // Create a layout for the dialog with two text fields for name and breed
        View view = getLayoutInflater().inflate(R.layout.dialog_update_pet, null);
        final EditText editTextName = view.findViewById(R.id.editTextPetName);
        final EditText editTextBreed = view.findViewById(R.id.editTextPetBreed);

        builder.setView(view);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the new values entered by the user
                String newName = editTextName.getText().toString().trim();
                String newBreed = editTextBreed.getText().toString().trim();

                // Update the pet in the database
                MyDataBaseHelper DB = new MyDataBaseHelper(ListPetsActivity.this);
                DB.updatePet(position + 1, newName, newBreed);

                // Update the list and the adapter
                petData.set(position, newName + " - " + newBreed);
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    // Method to show a dialog for confirming pet deletion
    private void deletePetDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListPetsActivity.this);
        builder.setMessage("Do you want to delete the pet?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the item from the database
                MyDataBaseHelper DB = new MyDataBaseHelper(ListPetsActivity.this);
                DB.deletePet(position + 1); // Add the logic to delete the item from the database

                // Remove the item from the list and update the adapter
                petData.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
