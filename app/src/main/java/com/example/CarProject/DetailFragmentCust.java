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
public class DetailFragmentCust extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DetailFragmentCust() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","DetailFragment");
        View v = inflater.inflate(R.layout.fragment_detailcustomer, null);
        final Car car = (Car) getArguments().getSerializable("data");


        Button btnEdit = (Button) v.findViewById(R.id.btnBook);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("id", car.getId());
                editor.putString("brand", car.getBrand());
                editor.putString("model", car.getModel());
                editor.putString("year", car.getYear());
                editor.putString("cost", car.getCost());
                editor.putString("rented", car.getRented());
                editor.commit();
                Intent i = new Intent(getContext(), Booking.class);
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
