package com.example.CarProject;



import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

public class Search extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public EditText txtBrand;
    public EditText txtModel;
    public EditText txtYear;
    public EditText txtCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtBrand = findViewById(R.id.editBrand);
        txtModel = findViewById(R.id.editModel);
        txtYear = findViewById(R.id.editYear);
        txtCost = findViewById(R.id.editCost4);


        Button btnSearch = findViewById(R.id.searchBtn);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand = txtBrand.getText().toString();
                String model = txtModel.getText().toString();
                String year = txtYear.getText().toString();
                String cost = txtCost.getText().toString();
                SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("brand", brand);
                editor.putString("model", model);
                editor.putString("year", year);
                editor.putString("cost", cost);

                editor.commit();

                startActivity(new Intent(Search.this, SearchResults.class));
            }
        });
    }

}
