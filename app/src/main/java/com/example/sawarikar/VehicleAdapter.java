package com.example.sawarikar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sawarikar.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class VehicleAdapter extends BaseAdapter {


    private Context myContext;
    ArrayList<HashMap<String,String>>vehicleDataList;
    DatabaseHelper dbHelper;

    public VehicleAdapter(Context context,ArrayList<HashMap<String,String>>arrayList){
        this.myContext = context;
        this.vehicleDataList=arrayList;
        dbHelper = new DatabaseHelper(myContext);
    }


    @Override
    public int getCount() {
        return vehicleDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicleDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
        final ViewHolder myViewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.vehicle_list_view,null,true);
            myViewHolder = new ViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }
        else {
            myViewHolder =(ViewHolder)convertView.getTag();
        }

        //setting data to textviews
        myViewHolder.vehicle_name.setText(vehicleDataList.get(position).get("vehicle_name"));
        myViewHolder.vehicle_number.setText(vehicleDataList.get(position).get("vehicle_number"));
        myViewHolder.vehicle_cc.setText(vehicleDataList.get(position).get("vehicle_cc"));
        myViewHolder.vehicle_year.setText(vehicleDataList.get(position).get("vehicle_year"));
        myViewHolder.vehicle_type.setText(vehicleDataList.get(position).get("vehicle_type"));
        myViewHolder.vehicle_fuel.setText(vehicleDataList.get(position).get("vehicle_fuel"));
        myViewHolder.vehicle_date.setText(vehicleDataList.get(position).get("vehicle_date"));

        myViewHolder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myContext, "You pressed delete for "+vehicleDataList.get(position).get("name"), Toast.LENGTH_SHORT).show();
                showMessage("Delete Contact", "Are you sure you want to delete this contact?", vehicleDataList.get(position).get("id"));
            }
        });

        myViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform edit operation
                Intent editVehicle = new Intent(myContext, AddVehicle.class);
                editVehicle.putExtra("ID", vehicleDataList.get(position).get("vehicle_id"));
                editVehicle.putExtra("NAME", vehicleDataList.get(position).get("vehicle_name"));
                editVehicle.putExtra("NUMBER", vehicleDataList.get(position).get("vehicle_number"));
                editVehicle.putExtra("CC", vehicleDataList.get(position).get("vehicle_cc"));
                editVehicle.putExtra("YEAR", vehicleDataList.get(position).get("vehicle_year"));
                editVehicle.putExtra("TYPE", vehicleDataList.get(position).get("vehicle_type"));
                editVehicle.putExtra("FUEL", vehicleDataList.get(position).get("vehicle_fuel"));
                editVehicle.putExtra("DATE", vehicleDataList.get(position).get("vehicle_date"));

                myContext.startActivity(editVehicle);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        protected TextView vehicle_name,vehicle_number,vehicle_cc,vehicle_year,vehicle_type,vehicle_fuel,vehicle_date;
        protected Button editButton, delButton;

        public ViewHolder(View view) {
            vehicle_name = view.findViewById(R.id.vehicle_name);
            vehicle_number = view.findViewById(R.id.vehicle_number);
            vehicle_cc = view.findViewById(R.id.vehicle_cc);
            vehicle_year = view.findViewById(R.id.vehicle_year);
            vehicle_type = view.findViewById(R.id.vehicle_type);
            vehicle_fuel = view.findViewById(R.id.vehicle_fuel);
            vehicle_date = view.findViewById(R.id.vehicle_date);


            editButton = view.findViewById(R.id.editButton);
            delButton = view.findViewById(R.id.deleteButton);
        }
    }

    public void showMessage(String title, String message, final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);

        // setting positive button
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // perform action on Confirm
                dbHelper.deleteVehicle(Integer.valueOf(id));

            }
        });

        // setting negative button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // perform action on Cancel
                dialog.dismiss();
            }
        });

        builder.show();
    }
}
