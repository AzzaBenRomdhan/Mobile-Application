package org.esprit.registrationform;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class AfficheListCalenderActivity extends AppCompatActivity {

    private ListView listView;
    private MyDataBaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_list_calender);

        // Initialize views
        listView = findViewById(R.id.listView);
        dbHelper = new MyDataBaseHelper(this);

        // Display daycare reservations in the ListView
        displayDaycareReservations();
    }

    private void displayDaycareReservations() {
        // Get all daycare reservations from the database
        Cursor daycareCursor = dbHelper.getAllDaycareReservations();

        List<String> reservationsList = new ArrayList<>();

        // Assuming the columns you want to display are "daycare_name", "pet_name", "start_date", and "end_date"
        while (daycareCursor.moveToNext()) {
            String daycareName = daycareCursor.getString(daycareCursor.getColumnIndex(MyDataBaseHelper.COLUMN_DAYCARE_NAME));
            String petName = daycareCursor.getString(daycareCursor.getColumnIndex(MyDataBaseHelper.COLUMN_PET_NAME_1));
            String startDate = daycareCursor.getString(daycareCursor.getColumnIndex(MyDataBaseHelper.COLUMN_START_DATE));
            String endDate = daycareCursor.getString(daycareCursor.getColumnIndex(MyDataBaseHelper.COLUMN_END_DATE));

            // Create a string representation of the reservation
            String reservationInfo = "Daycare: " + daycareName + "\nPet: " + petName +
                    "\nStart Date: " + startDate + "\nEnd Date: " + endDate;

            reservationsList.add(reservationInfo);
        }

        // Create an adapter for the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservationsList);

        // Set the adapter for the ListView
        listView.setAdapter(adapter);
    }
}
