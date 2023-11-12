package org.esprit.registrationform;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendrierActivity extends AppCompatActivity {

    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private CalendarView calendarView;
    private Calendar startCalendar;
    private MyDataBaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking);

        // Initialize views
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        calendarView = findViewById(R.id.calendarView2);

        // Set initial dates in the EditText fields
        setInitialDates();

        // Initialize the database helper
        dbHelper = new MyDataBaseHelper(this);

        // Add a listener to detect date selections
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Format the selected date
                String selectedDate = formatDate(year, month, dayOfMonth);

                // Determine whether to set the start or end date
                if (editTextStartDate.isFocused()) {
                    editTextStartDate.setText(selectedDate);
                } else if (editTextEndDate.isFocused()) {
                    // Validate that the end date is after the start date
                    if (validateEndDate(year, month, dayOfMonth)) {
                        editTextEndDate.setText(selectedDate);
                    } else {
                        Snackbar.make(view, "End date must be after the start date", Snackbar.LENGTH_SHORT).show();
                    }
                }

                // Example usage of a Snackbar to display the selected date
                Snackbar.make(view, "Selected Date: " + selectedDate, Snackbar.LENGTH_SHORT).show();
            }
        });

        // Get references to the spinners in your layout
        Spinner spinnerDayCare = findViewById(R.id.spinnerDayCare);
        Spinner spinnerAnimalName = findViewById(R.id.spinnerAnimalName);



        // Populate the Day Care Spinner
        populateDayCareSpinner(spinnerDayCare);

        // Populate the Animal Name Spinner
        populateAnimalNameSpinner(spinnerAnimalName);


        Button confirmButton = findViewById(R.id.buttonConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get selected values
                String selectedDayCare = spinnerDayCare.getSelectedItem().toString();
                String selectedAnimalName = spinnerAnimalName.getSelectedItem().toString();
                String startDate = editTextStartDate.getText().toString();
                String endDate = editTextEndDate.getText().toString();

                // Check if any field is empty
                if (TextUtils.isEmpty(selectedDayCare) || TextUtils.isEmpty(selectedAnimalName) ||
                        TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)) {
                    Snackbar.make(v, "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Add daycare reservation to the database
                    addDaycare(selectedDayCare, selectedAnimalName, startDate, endDate);

                    // Show success message
                    Snackbar.make(v, "Reservation successfully added", Snackbar.LENGTH_SHORT).show();

                    // Optionally, you can clear the input fields or perform any other actions after adding the reservation
                    // clearInputFields();
                }
            }
        });
    }

    private void setInitialDates() {
        // Get the current date
        startCalendar = Calendar.getInstance();
        Date currentDate = startCalendar.getTime();

        // Format the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);

        // Set the current date as the initial start and end dates
        editTextStartDate.setText(formattedDate);
        editTextEndDate.setText(formattedDate);
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        // Format the selected date
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month, dayOfMonth);

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(selectedCalendar.getTime());
    }

    private boolean validateEndDate(int year, int month, int dayOfMonth) {
        // Create a calendar for the selected end date
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(year, month, dayOfMonth);

        // Compare the selected end date with the start date
        return endCalendar.after(startCalendar);
    }

    private void populateDayCareSpinner(Spinner spinnerDayCare) {
        // Retrieve day care names from the database
        Cursor dayCareCursor = dbHelper.getAllReservations();
        List<String> dayCareList = new ArrayList<>();

        // Assuming the day care name is in the "COLUMN_DAYCARE_NAME" column
        while (dayCareCursor.moveToNext()) {
            String dayCareName = dayCareCursor.getString(dayCareCursor.getColumnIndex(MyDataBaseHelper.COLUMN_DAYCARE_NAME));
            dayCareList.add(dayCareName);
        }

        // Create an adapter for the day care spinner
        ArrayAdapter<String> dayCareAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dayCareList);
        dayCareAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the day care spinner
        spinnerDayCare.setAdapter(dayCareAdapter);
    }

    private void populateAnimalNameSpinner(Spinner spinnerAnimalName) {
        // Retrieve pet names from the database
        Cursor petCursor = dbHelper.readAlldataPET();
        List<String> petList = new ArrayList<>();

        // Assuming the pet name is in the "COLUMN_NAME" column
        while (petCursor.moveToNext()) {
            String petName = petCursor.getString(petCursor.getColumnIndex(MyDataBaseHelper.COLUMN_NAME));
            petList.add(petName);
        }

        // Create an adapter for the animal name spinner
        ArrayAdapter<String> animalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, petList);
        animalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter for the animal name spinner
        spinnerAnimalName.setAdapter(animalAdapter);
    }

    private void addDaycare(String dayCare, String animalName, String startDate, String endDate) {
        dbHelper.addDaycare(dayCare, animalName, startDate, endDate);
    }
}
