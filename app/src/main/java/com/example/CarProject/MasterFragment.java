package com.example.CarProject;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MasterFragment extends Fragment {

    private ListView lv;

    public MasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView","MasterFragment");
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d("onViewCreated","MasterFragment");
        lv = (ListView) view.findViewById(R.id.theList);  // it is in fragment_master.xml

    }

    public void setList(final ArrayList<Car> cars) {
        ArrayList<String> models = new ArrayList<>();
        final ArrayList<String> ids = new ArrayList<>();
        for (Car c : cars) {
            if (!models.contains(c.getModel()))
                models.add(c.getModel());
        }

        for (Car c : cars) {
            ids.add(c.getId());
        }

        final ArrayList<Car> cars2 = new ArrayList<>();
        for (Car c : cars) {
            cars2.add(c);
        }

        ArrayAdapter<String> adapterM = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, models);
        lv.setAdapter(adapterM);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemClick", "MasterFragment");
                // DetailFragment class should be created first


                SharedPreferences prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
//                final String customerID = prefs.getString("customerID","");
                final String managerID = prefs.getString("managerID","");
                if(managerID.equals("0") ){
                    DetailFragment df = new DetailFragment();
                    Bundle b = new Bundle();
                    b.putSerializable("data", cars.get(position));
                    // b.putSerializable("data", someArrayList);

                    df.setArguments(b);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.theDetail, df)   // R.id.theDetail refers to the <FrameLayout>
                            .commit();
                } else {
                    DetailFragmentCust df = new DetailFragmentCust();
                    Bundle b = new Bundle();
                    b.putSerializable("data", cars.get(position));
                    // b.putSerializable("data", someArrayList);

                    df.setArguments(b);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.theDetail, df)   // R.id.theDetail refers to the <FrameLayout>
                            .commit();

                }
            }
        });
    }

}
