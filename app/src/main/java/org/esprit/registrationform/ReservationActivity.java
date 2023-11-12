package org.esprit.registrationform;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.esprit.registrationform.MyDataBaseHelper;
import org.esprit.registrationform.R;

public class ReservationActivity extends AppCompatActivity {
    EditText etDaycareName, etDaycareDescription, etDaycarePrice;
    Button btnAddReservation;

    MyDataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

      //  etPetName = findViewById(R.id.etPetName);
     //   etReservationDate = findViewById(R.id.etReservationDate);
       // etReservationTime = findViewById(R.id.etReservationTime);
        etDaycareName = findViewById(R.id.etDaycareName);
        etDaycareDescription = findViewById(R.id.etDaycareDescription);
        etDaycarePrice = findViewById(R.id.etDaycarePrice);
        btnAddReservation = findViewById(R.id.btnAddReservation);

        dataBaseHelper = new MyDataBaseHelper(this);

        btnAddReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDaycareReservation();
            }
        });
    }

    private void addDaycareReservation() {
      //  String petName = etPetName.getText().toString().trim();
    //    String reservationDate = etReservationDate.getText().toString().trim();
      //  String reservationTime = etReservationTime.getText().toString().trim();
        String daycareName = etDaycareName.getText().toString().trim();
        String daycareDescription = etDaycareDescription.getText().toString().trim();
        String daycarePriceStr = etDaycarePrice.getText().toString().trim();

        if (!daycareName.isEmpty() && !daycareDescription.isEmpty() && !daycarePriceStr.isEmpty()) {

            double daycarePrice = Double.parseDouble(daycarePriceStr);

            // Ajoutez la réservation à la base de données
            dataBaseHelper.addDaycareReservation(daycareName, daycareDescription, daycarePrice);
            clearFields();

            // Utilisez Intent pour rediriger vers la nouvelle activité (ReservationListActivity)
            Intent intent = new Intent(ReservationActivity.this, ReservationListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        etDaycareName.setText("");
        etDaycareDescription.setText("");
        etDaycarePrice.setText("");
    }
}
