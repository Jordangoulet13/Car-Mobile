package com.example.CarProject;

import android.content.SharedPreferences;
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

public class SearchResults extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String TAG = "ShowCars";

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);


        loadCars();
    }

    public void loadCars() {
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        final String brand = sp.getString("brand", "");
        final String model = sp.getString("model", "");
        final String year = sp.getString("year", "");
        final String cost = sp.getString("cost", "");

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
                                if(c.getCost().equals(cost)){
                                    cars.add(c);
                                }
                                c.setBrand(doc.getString("brand"));
                                if(c.getBrand().equals(brand)){
                                    cars.add(c);
                                }
                                c.setId(doc.getId());
                                c.setModel(doc.getString("model"));
                                if(c.getModel().equals(model)){
                                    cars.add(c);
                                }
                                c.setYear(doc.getString("year"));
                                if(c.getYear().equals(year)){
                                    cars.add(c);
                                }
                                c.setRented(doc.getString("rented"));

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
