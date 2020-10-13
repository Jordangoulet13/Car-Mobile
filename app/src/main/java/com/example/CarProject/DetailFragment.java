package com.example.CarProject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DetailFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","DetailFragment");
        View v = inflater.inflate(R.layout.fragment_detail, null);
        final Car car = (Car) getArguments().getSerializable("data");


        Button btnEdit = (Button) v.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("id", car.getId());
                editor.commit();
                Intent i = new Intent(getContext(), EditCar.class);
                startActivity(i);
            }
        });

        Button btnDelete = (Button) v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("id", car.getId());
                editor.commit();
                Intent i = new Intent(getContext(), DeleteCar.class);
                startActivity(i);
            }
        });

        Button btnHome = (Button) v.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        TextView textV = (TextView) v.findViewById(R.id.txtInfo); // this is referring to the item inside fragment_detail
        textV.setText(car.toString());
        return v;
    }
}
