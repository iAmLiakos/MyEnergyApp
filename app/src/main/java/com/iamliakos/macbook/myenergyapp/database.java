package com.iamliakos.macbook.myenergyapp;

/**
 * Created by macbook on 10/11/15.
 */

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListView;


class database extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DevicesManager";
    private static String DB_PATH = "/data/data/com.iamliakos.macbook.myenergyapp/databases/";
    // Table Names
    private static final String TABLE_DEVICES = "Electrical_Devices";


    // Common column names

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_WATTS = "Watts";
    private static final String KEY_CREATED_AT = "created_at";

    // Table Create Statements

    Context helperContext;

    database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        helperContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required table
        String CREATE_TABLE_DEVICES = "CREATE TABLE " + TABLE_DEVICES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_WATTS + " TEXT" + ")";
        db.execSQL(CREATE_TABLE_DEVICES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);

        // create new table again
        onCreate(db);
    }

    public void addDevice(Devices device) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_ID, device.getId()); No need
        values.put(KEY_NAME, device.getName());
        values.put(KEY_WATTS, device.getPower());

        db.insert(TABLE_DEVICES, null, values);
        db.close();
    }

    public void devicedel(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DEVICES, KEY_NAME + "=\"" + name + "\"", null);
        db.close();
    }

    public Devices getDevice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DEVICES, new String[]{KEY_ID,
                        KEY_NAME, KEY_WATTS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Devices device = new Devices(cursor.getString(1), cursor.getString(2));
        return device;
    }

    public List<Devices> getAllDevices() {
        List<Devices> deviceList = new ArrayList<Devices>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Devices device = new Devices();
                device.setName(cursor.getString(1));//name
                device.setPower(cursor.getString(2));//watts
                // Adding device to list
                deviceList.add(device);
            } while (cursor.moveToNext());
        }

        // return devices list
        return deviceList;
    }

}

