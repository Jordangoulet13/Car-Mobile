package com.example.CarProject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder().setTimestampsInSnapshotsEnabled(true).build();
        db.setFirestoreSettings(settings);

//        String carData=readJson(MainActivity.this,"cars.json");
//        ArrayList<Cars> c = uploadCars(carData);
//        firebaseCars(c);
//        String data =readJson(MainActivity.this,"users.json");
//        ArrayList<Users> u =uploadUser(data);
//        firebaseUsers(u);

        final EditText email = (EditText) findViewById(R.id.emailText);
        final EditText pass = (EditText) findViewById(R.id.passwordTxt);
        Button login = (Button) findViewById(R.id.login);
        final Button signUp = (Button) findViewById(R.id.signUp);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String entEmail = email.getText().toString().trim();
                final String entpass = pass.getText().toString().trim();
                if (entEmail.equals("") && entpass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter your information", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            boolean correct = false;
                            boolean admin = false;
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    if (doc.getString("email").equals(entEmail) && doc.getString("password").equals(entpass)) {
                                        if (doc.getString("is_manager").equals("1") || doc.getString("is_sales").equals("1")) {
                                            admin = true;
                                        }
                                        correct = true;
                                        break;
                                    } else {
                                        correct = false;

                                    }
                                }
                                if (correct && admin == false) {
                                    Toast.makeText(MainActivity.this, "Logging in...", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, CustomerActivity.class);
                                    i.putExtra("admin", admin);
                                    SharedPreferences sp;
                                    sp = getSharedPreferences("prefs",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("managerID","1");
                                    editor.putString("userEmail",entEmail);
                                    editor.commit();
                                    startActivity(i);
                                } else if (correct && admin) {
                                    Toast.makeText(MainActivity.this, "Logging in admin...", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, ManagerActivity.class);
                                    i.putExtra("admin", admin);
                                    SharedPreferences sp;
                                    sp = getSharedPreferences("prefs",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("managerID","0");
                                    editor.putString("userEmail",entEmail);
                                    editor.commit();
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, "incorrect login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
    }

    private String readJson(Context ctx, String fn) {
        String data = "";
        try {
            AssetManager am = ctx.getAssets();
            InputStream is = am.open(fn);
            int size = is.available();
            byte[] buff = new byte[size];
            is.read(buff);
            is.close();
            data = new String(buff, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private ArrayList<Users> uploadUser(String d) {
        String data = d;
        ArrayList<Users> temp = new ArrayList<>();
        try {
            JSONArray ar = new JSONArray(data);
            JSONObject ob;
            Users u;
            for (int i = 0; i < ar.length(); i++) {
                ob = ar.getJSONObject(i);
                u = new Users();
                u.id = ob.getString("id");
                u.fname = ob.getString("first_name");
                u.lname = ob.getString("last_name");
                u.email = ob.getString("email");
                u.password = ob.getString("password");
                u.is_manager = ob.getString("is_manager");
                u.is_sales = ob.getString("is_sales");
                temp.add(u);
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
//    private ArrayList<Cars> uploadCars(String d){
//        String data=d;
//        ArrayList<Cars> temp = new ArrayList<>();
//        try{
//            JSONArray ar = new JSONArray(data);
//            JSONObject ob;
//            Cars c;
//            for(int i =0;i<ar.length();i++){
//                ob=ar.getJSONObject(i);
//                c=new Cars();
//                c.id=ob.getString("id");
//                c.cost=ob.getString("cost");
//                c.colour=ob.getString("colour");
//                c.brand=ob.getString("car_make");
//                c.model=ob.getString("car_model");
//                c.passenger=ob.getString("passenger");
//                c.year=ob.getString("car_year");
//                c.rented=ob.getString("rented");
//                temp.add(c);
//            }
//            return temp;
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    private void firebaseUsers(ArrayList<Users>u){
//        Map<String,Object> dt;
//        dt=new HashMap<>();
//        CollectionReference dba =db.collection("Users");
//        for(Users temp:u){
//            dt.put("id",temp.id);
//            dt.put("first_name",temp.fname);
//            dt.put("last_name",temp.lname);
//            dt.put("email",temp.email);
//            dt.put("password",temp.password);
//            dt.put("is_manager",temp.is_manager);
//            dt.put("is_sales",temp.is_sales);
//            dba.add(dt);
//        }
//    }
//    private void firebaseCars(ArrayList<Cars>c){
//        Map<String,Object> dt;
//        dt=new HashMap<>();
//        CollectionReference dba =db.collection("Cars Category");
//        for(Cars temp:c){
//            dt.put("id",temp.id);
//            dt.put("brand",temp.brand);
//            dt.put("colour",temp.colour);
//            dt.put("model",temp.model);
//            dt.put("cost",temp.cost);
//            dt.put("year",temp.year);
//            dt.put("rented",temp.rented);
//            dt.put("passenger",temp.passenger);
//            dba.add(dt);
//        }
//    }
//
//}


//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button btnAdd = findViewById(R.id.btnManager);
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, ManagerLogin.class);
//                startActivity(i);
//            }
//        });
//
//        Button btnShow = findViewById(R.id.btnCusrtomer);
//        btnShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, CustomerLogin.class);
//                startActivity(i);
//            }
//        });

