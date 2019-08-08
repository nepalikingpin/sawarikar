package com.example.sawarikar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sawarikar.sql.DatabaseHelper;

public class AddVehicle extends AppCompatActivity {

    EditText eTvehicle_name,eTvehicle_number,eTvehicle_cc,eTvehicle_year,eTvehicle_type,eTvehicle_fuel,eTvehicle_date;
    Button addBtn;
    DatabaseHelper db;

    Boolean toEdit = false;

    String vehicle_id,vehicle_name,vehicle_number,vehicle_cc,vehicle_year,vehicle_type,vehicle_fuel,vehicle_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        db = new DatabaseHelper(AddVehicle.this);
        //getting data from intent

        Intent getEditData = getIntent();
        vehicle_id = getEditData.getStringExtra("ID");
        vehicle_name = getEditData.getStringExtra("NAME");
        vehicle_number = getEditData.getStringExtra("NUMBER");
        vehicle_cc = getEditData.getStringExtra("CC");
        vehicle_year= getEditData.getStringExtra("YEAR");
        vehicle_type= getEditData.getStringExtra("TYPE");
        vehicle_fuel= getEditData.getStringExtra("FUEL");
        vehicle_date= getEditData.getStringExtra("DATE");

        System.out.println("Data from intent = "+vehicle_id+","+vehicle_name+","+vehicle_number+","+vehicle_cc+","+vehicle_year+","+vehicle_type+","+vehicle_fuel+","+vehicle_date+"");

        //initialization

        eTvehicle_name=findViewById(R.id.vehicle_name);
        eTvehicle_number=findViewById(R.id.vehicle_number);
        eTvehicle_cc=findViewById(R.id.vehicle_cc);
        eTvehicle_year=findViewById(R.id.vehicle_year);
        eTvehicle_type=findViewById(R.id.vehicle_type);
        eTvehicle_fuel=findViewById(R.id.vehicle_fuel);
        eTvehicle_date=findViewById(R.id.vehicle_date);

        addBtn = findViewById(R.id.addbtn);

        if (vehicle_id != null && vehicle_name != null && vehicle_number != null && vehicle_cc != null && vehicle_year != null && vehicle_type!=null && vehicle_fuel != null && vehicle_date!= null) {
            System.out.println("After pressing edit Button");
            eTvehicle_name.setText(vehicle_name);
            eTvehicle_number.setText(vehicle_number);
            eTvehicle_cc.setText(vehicle_cc);
            eTvehicle_year.setText(vehicle_year);
            eTvehicle_type.setText(vehicle_type);
            eTvehicle_fuel.setText(vehicle_fuel);
            eTvehicle_date.setText(vehicle_date);


            toEdit = true;

            addBtn.setText("Update Vehicle Details");
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eTvehicle_name.getText().toString().trim().isEmpty() && !eTvehicle_number.getText().toString().trim().isEmpty() && !eTvehicle_cc.getText().toString().isEmpty() && !eTvehicle_year.getText().toString().isEmpty() && !eTvehicle_type.getText().toString().isEmpty() && !eTvehicle_fuel.getText().toString().isEmpty()&& !eTvehicle_date.getText().toString().isEmpty() ) {
                    Toast.makeText(AddVehicle.this, "Valid Input", Toast.LENGTH_SHORT).show();
                    boolean isInserted;

                    if (toEdit) {
                        // update contact
                        isInserted = db.updateData(Integer.valueOf(vehicle_id), eTvehicle_name.getText().toString(), eTvehicle_number.getText().toString(), eTvehicle_cc.getText().toString(),eTvehicle_year.getText().toString(),eTvehicle_type.getText().toString()eTvehicle_fuel.getText().toString()eTvehicle_date.getText().toString());
                    } else {
                        // insert contact
                        isInserted = db.addvehicle(eTvehicle_name.getText().toString(), eTvehicle_number.getText().toString(), eTvehicle_cc.getText().toString(),eTvehicle_year.getText().toString(),eTvehicle_type.getText().toString()eTvehicle_fuel.getText().toString()eTvehicle_date.getText().toString());

                    }

                    if (isInserted) {
                        // show success message
                        showMessage("Success", "Data inserted/updated successfully!!!");
                    }  else {
                        // show failure message
                        showMessage("Error", "Failed to insert data!!!");
                       }

                } else {
                    Toast.makeText(AddVehicle.this, "All the fields are mandatory", Toast.LENGTH_LONG).show();
                    showMessage("Error", "All the fields are necessary");
                }
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddVehicle.this);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AddVehicle.this, VehicleInformation.class));
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
