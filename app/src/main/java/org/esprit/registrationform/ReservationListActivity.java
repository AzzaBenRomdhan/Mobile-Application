package org.esprit.registrationform;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationListActivity extends AppCompatActivity {
    ListView listView;
    MyDataBaseHelper dataBaseHelper;
    private SimpleCursorAdapter adapter;
    Button btnCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        listView = findViewById(R.id.listView);
        dataBaseHelper = new MyDataBaseHelper(this);

        // Afficher la liste des réservations existantes lors de la création de l'activité
        displayReservationList();

        // Ajouter un listener pour détecter les clics sur les éléments de la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOptionsDialog(position);
            }
        });
// Redirect
        btnCalendar = findViewById(R.id.btnCalendar);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReservationListActivity.this, CalendrierActivity.class);
                startActivity(intent);
            }
        });

    }

    private void displayReservationList() {
        // Récupérer toutes les réservations depuis la base de données
        Cursor cursor = dataBaseHelper.getAllReservations();

        // Utiliser un SimpleCursorAdapter pour lier les données au ListView
        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{MyDataBaseHelper.COLUMN_DAYCARE_NAME, MyDataBaseHelper.COLUMN_DAYCARE_PRICE},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );

        // Mettre à jour le ListView avec le nouvel adaptateur
        listView.setAdapter(adapter);
    }

    private void showOptionsDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options of daycare");

        CharSequence[] options = {"Update", "Delete"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // Mettre à jour la réservation
                        updateReservationDialog(position);
                        break;
                    case 1:
                        // Supprimer la réservation
                        deleteReservationDialog(position);
                        break;
                }
            }
        });

        builder.show();
    }

    private void updateReservationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update DayCare");

        // Implement the layout for updating the reservation here
        // Use an EditText or other views as needed
        View view = getLayoutInflater().inflate(R.layout.dialog_update_reservation, null);
        final EditText editTextNewPrice = view.findViewById(R.id.editTextReservationPrice);
        final EditText editTextNewDayCareName = view.findViewById(R.id.editTextDayCareName);

        builder.setPositiveButton("Mettre à jour", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Implement the reservation update here
                // Use dataBaseHelper.updateReservation() or a similar method
                // Make sure to refresh the list after the update

                // Example call to the updateReservation method
                Cursor cursor = (Cursor) adapter.getItem(position);
                int reservationId = cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_RESERVATION_ID));
                String newPrice = editTextNewPrice.getText().toString();
                String newDayCareName = editTextNewDayCareName.getText().toString();

                dataBaseHelper.updateReservation(reservationId, newPrice, newDayCareName);

                // Refresh the reservation list
                displayReservationList();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.setView(view);
        builder.show();
    }

    private void deleteReservationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this daycare?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Supprimer la réservation de la base de données
                Cursor cursor = (Cursor) adapter.getItem(position);
                int reservationId = cursor.getInt(cursor.getColumnIndex(MyDataBaseHelper.COLUMN_RESERVATION_ID));
                dataBaseHelper.deleteReservation(reservationId);

                // Rafraîchir la liste des réservations
                displayReservationList();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
