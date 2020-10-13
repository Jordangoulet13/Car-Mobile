package com.example.CarProject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditCar extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);

        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        final String id = sp.getString("id", "");

        final EditText editBrand = findViewById(R.id.editBrand3);
        final EditText editModel = findViewById(R.id.editModel);
        final EditText editYear = findViewById(R.id.editYear);
        final EditText editCost = findViewById(R.id.editCost1);
        Button btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand = editBrand.getText().toString();
                String model = editModel.getText().toString();
                String year = editYear.getText().toString();
                String cost = editCost.getText().toString();
                editCar(id, brand, model, year, cost);

                startActivity(new Intent(EditCar.this, ShowCars.class));
            }
        });
    }

    public void editCar(String id, String brand, String model, String year, String cost) {
        db.collection("Cars Category").document(id)
                .update(
                        "brand", brand,
                        "model", model,
                        "year", year,
                        "cost", cost
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(EditCar.this, "Car updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditCar.this, "Couldn't update the car", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
