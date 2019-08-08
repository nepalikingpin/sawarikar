package com.example.sawarikar.sql;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sawarikar.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";


    public static final String VEHICLES_TABLE_NAME ="vehicles";
    public static final String VEHICLES_COLUMN_ID = "vehicle_id";
    public static final String VEHICLES_COLUMN_NAME = "vehicle_name";
    public static final String VEHICLES_COLUMN_NUMBER = "vehicle_number";
    public static final String VEHICLES_COLUMN_CC = "vehicle_cc";
    public static final String VEHICLES_COLUMN_YEAR = "vehicle_year";
    public static final String VEHICLES_COLUMN_TYPE = "vehicle_type";
    public static final String VEHICLES_COLUMN_FUEL = "vehicle_fuel";
    public static final String VEHICLES_COLUMN_DATE = "vehicle_date";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";


    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);

        db.execSQL(
                "create table " +VEHICLES_TABLE_NAME +"(vehicle_id integer primary key autoincrement,vehicle_name text,vehicle_number text,vehicle_cc text,vehicle_year text,vehicle_type text,vehicle_fuel text,vehicle_date text)"
        );

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        db.execSQL("DROP TABLE IF EXISTS vehicles");


        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean addvehicle(String vehicle_name ,String vehicle_number,String vehicle_cc,String vehicle_year,String vehicle_type,String vehicle_fuel,String vehicle_date){
        SQLiteDatabase sq =this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(VEHICLES_COLUMN_NAME,vehicle_name);
        contentValues.put(VEHICLES_COLUMN_NUMBER,vehicle_number);
        contentValues.put(VEHICLES_COLUMN_CC,vehicle_cc);
        contentValues.put(VEHICLES_COLUMN_YEAR,vehicle_year);
        contentValues.put(VEHICLES_COLUMN_TYPE,vehicle_type);
        contentValues.put(VEHICLES_COLUMN_FUEL,vehicle_fuel);
        contentValues.put(VEHICLES_COLUMN_DATE,vehicle_date);

        long reslt = sq.insert(VEHICLES_TABLE_NAME,null,contentValues);

        if(reslt== -1){
            return false;
        }

        else{
            return true;
        }
    }

    //get db data

    public ArrayList<HashMap<String,String>> getDbData(){
        SQLiteDatabase sq = this.getReadableDatabase();
        Cursor cur = sq.rawQuery("select * from " + VEHICLES_TABLE_NAME,null);
        ArrayList<HashMap<String,String>>arrayListVehicles =new ArrayList<>();
        while(cur.moveToNext()){
            HashMap<String,String> hashMapVehicleDetails = new HashMap<>();
            hashMapVehicleDetails.put("name",cur.getString(cur.getColumnIndex(VEHICLES_COLUMN_NAME)));
            hashMapVehicleDetails.put("number",cur.getString(cur.getColumnIndex(VEHICLES_COLUMN_NUMBER)));
            hashMapVehicleDetails.put("cc",cur.getString(cur.getColumnIndex(VEHICLES_COLUMN_CC)));
            hashMapVehicleDetails.put("year",cur.getString(cur.getColumnIndex(VEHICLES_COLUMN_YEAR)));
            hashMapVehicleDetails.put("type",cur.getString(cur.getColumnIndex(VEHICLES_COLUMN_TYPE)));
            hashMapVehicleDetails.put("fuel",cur.getString(cur.getColumnIndex(VEHICLES_COLUMN_FUEL)));
            hashMapVehicleDetails.put("date",cur.getString(cur.getColumnIndex(VEHICLES_COLUMN_DATE)));

            arrayListVehicles.add(hashMapVehicleDetails);
        }

        return arrayListVehicles;
    }

    public boolean updateData(Integer vehicle_id,String vehicle_name, String vehicle_number,String vehicle_cc,String vehicle_year,String vehicle_type,String vehicle_fuel,String vehicle_date){
        //update data in database
        SQLiteDatabase sq = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(VEHICLES_COLUMN_NAME,vehicle_name);
        contentValues.put(VEHICLES_COLUMN_NUMBER,vehicle_number);
        contentValues.put(VEHICLES_COLUMN_CC,vehicle_cc);
        contentValues.put(VEHICLES_COLUMN_YEAR,vehicle_year);
        contentValues.put(VEHICLES_COLUMN_TYPE,vehicle_year);
        contentValues.put(VEHICLES_COLUMN_FUEL,vehicle_fuel);
        contentValues.put(VEHICLES_COLUMN_DATE,vehicle_date);

        sq.update(VEHICLES_TABLE_NAME,contentValues,"vehicle_id=?",new String[]{String.valueOf(vehicle_id)});

        return true;
    }

    public boolean deleteData(Integer vehicle_id){
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        int res = sqLiteDatabase.delete(VEHICLES_TABLE_NAME,"vehicle_id =?",new String[] {String.valueOf(vehicle_id)});

        if(res == 0){
            return false;
        }

        else{
            return true;
        }
    }

    public Integer deleteVehicle(Integer vehicle_id){
        SQLiteDatabase db  = this.getWritableDatabase();
        return db.delete(VEHICLES_TABLE_NAME,"vehicle_id =?",new String[]{Integer.toString(vehicle_id)});
    }

    //Get vehicle details
    public ArrayList<HashMap<String,String>>GetVehicles(){
        SQLiteDatabase sq =this.getWritableDatabase();
        ArrayList<HashMap<String,String>>vehicleList = new ArrayList<>();
        String query = "SELECT * FROM "+ VEHICLES_TABLE_NAME;
        Cursor cursor = sq.rawQuery(query,null);
        while(cursor.moveToNext()){
            HashMap<String,String> vehicle =new HashMap<>();
            vehicle.put("name",cursor.getString(cursor.getColumnIndex(VEHICLES_COLUMN_NAME)));
            vehicle.put("number",cursor.getString(cursor.getColumnIndex(VEHICLES_COLUMN_NUMBER)));
            vehicle.put("cc",cursor.getString(cursor.getColumnIndex(VEHICLES_COLUMN_CC)));
            vehicle.put("year",cursor.getString(cursor.getColumnIndex(VEHICLES_COLUMN_YEAR)));
            vehicle.put("type",cursor.getString(cursor.getColumnIndex(VEHICLES_COLUMN_TYPE)));
            vehicle.put("fuel",cursor.getString(cursor.getColumnIndex(VEHICLES_COLUMN_FUEL)));
            vehicle.put("date",cursor.getString(cursor.getColumnIndex(VEHICLES_COLUMN_DATE)));
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }




    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();


        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}