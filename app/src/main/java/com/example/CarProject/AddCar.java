package com.example.CarProject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddCar extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public EditText txtBrand;
    public EditText txtModel;
    public EditText txtYear;
    public EditText txtCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        txtBrand = findViewById(R.id.editBrand);
        txtModel = findViewById(R.id.editModel);
        txtYear = findViewById(R.id.editYear);
        txtCost = findViewById(R.id.editCost4);
    }

    public void addCar(View v) {
        String brand = txtBrand.getText().toString();
        String model = txtModel.getText().toString();
        String year = txtYear.getText().toString();
        String cost = txtCost.getText().toString();

        Car c = new Car(brand, model, year, cost);
        db.collection("Cars Category").document().set(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddCar.this, "Car added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCar.this, "Error adding car", Toast.LENGTH_SHORT).show();
                    }
                });

        startActivity(new Intent(this, ShowCars.class));
    }
}
