package com.example.sawarikar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.sawarikar.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class VehicleInformation extends AppCompatActivity {

    DatabaseHelper db;

    ListView listView_vehicles;
    ArrayList<HashMap<String,String>>vehicles_arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_information);
        getSupportActionBar().hide();

        db =new DatabaseHelper(VehicleInformation.this);

        listView_vehicles = findViewById(R.id.vehicle_list);

        vehicles_arrayList = db.getDbData();

        setDataToListView();
    }

    public void setDataToListView(){
        VehicleAdapter vehicleAdapter = new VehicleAdapter(VehicleInformation.this,vehicles_arrayList);
        listView_vehicles.setAdapter(vehicleAdapter);
    }

}
