package com.example.CarProject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowAvailableCars extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "ShowCars";

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cars);

        loadCars();
    }

    public void loadCars() {
        final ArrayList<Car> cars = new ArrayList<>();
        final ArrayList<Car> availableCars = new ArrayList<>();
        db.collection("Cars Category").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Car c = new Car();
                                c.setCost(doc.getString("cost"));
                                c.setBrand(doc.getString("brand"));
                                c.setId(doc.getId());
                                c.setModel(doc.getString("model"));
                                c.setYear(doc.getString("year"));
                                c.setRented(doc.getString("rented"));
                                if(c.getRented().equals("0")){
                                    cars.add(c);
                                }
                            }




                            Log.d(TAG, cars.toString());
                            MasterFragment mf = (MasterFragment) getSupportFragmentManager().findFragmentById(R.id.categories);
                            mf.setList(cars);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}