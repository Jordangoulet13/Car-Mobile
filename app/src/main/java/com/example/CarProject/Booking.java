package com.example.CarProject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Booking extends AppCompatActivity {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_booking);

            SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
            final String id = sp.getString("id", "");
            final String brand = sp.getString("brand", "");
            final String model = sp.getString("model", "");
            final String year = sp.getString("year", "");
            final String cost = sp.getString("cost", "");
            //final String rented = sp.getString("rented", "");

            final TextView editBrand = findViewById(R.id.editBrand2);
            final TextView editModel = findViewById(R.id.editModel);
            final TextView editYear = findViewById(R.id.editYear);
            final TextView editCost = findViewById(R.id.editBrand);
            final EditText days = findViewById(R.id.daysTxt);
            final TextView totalCost = findViewById(R.id.totalCost);
            final CheckBox editRented = findViewById(R.id.checkBox);

            editBrand.setText(brand);
            editModel.setText(model);
            editYear.setText(year);
            editCost.setText(cost);
            //editRented.setText(rented);
//            final EditText editModel = findViewById(R.id.editModel);
//            final EditText editYear = findViewById(R.id.editYear);
//            final EditText editCost = findViewById(R.id.editCost);
            Button btnEdit = findViewById(R.id.btnEdit);
            Button btnCalc = findViewById(R.id.calcBtn);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( editRented.isChecked()){
                        String rented="1";
                        editCar(id, rented);
                        startActivity(new Intent(com.example.CarProject.Booking.this, ShowCars.class));
                    }



                }
            });
            btnCalc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String daysnum = days.getText().toString();
                    int dn = Integer.parseInt(daysnum);
                    int dc = Integer.parseInt(cost);
                    int fin=dn*dc;
                    totalCost.setText(Integer.toString(fin));

                }
            });
        }

        public void editCar(String id, String rented) {
            db.collection("Cars Category").document(id)
                    .update(
                            "rented", rented

                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(com.example.CarProject.Booking.this, "Car booked", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(com.example.CarProject.Booking.this, "Couldn't update the car", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }