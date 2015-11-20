package com.iamliakos.macbook.myenergyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.iamliakos.macbook.myenergyapp.database;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Random;

public class AddActivity extends AppCompatActivity {

    database db = new database(this);



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button SaveButton = (Button) findViewById(R.id.savebutton);
        SaveButton.setOnClickListener(saveButtonListener);

        Button ShowButton = (Button) findViewById(R.id.showbutton);
        ShowButton.setOnClickListener(showButtonListener);

        Button DeleteButton = (Button) findViewById(R.id.deleteButton);
        DeleteButton.setOnClickListener(deleteButtonListener);

//        EditText editName = (EditText) findViewById(R.id.editNameButton);
//        EditText editConsumption = (EditText) findViewById(R.id.editConsumptionButton);
//        EditText editStatus = (EditText) findViewById(R.id.editStatusButton);
//        SQLiteDatabase db = openOrCreateDatabase("Electrical_Devices", Context.MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS device(name VARCHAR,consumption VARCHAR,status VARCHAR);");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Consumption is in Watts", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }


    public View.OnClickListener deleteButtonListener = new View.OnClickListener(){
        @Override
        public void onClick (View v) {
            SQLiteDatabase db = openOrCreateDatabase("DevicesManager", Context.MODE_PRIVATE, null);
            //db.execSQL("CREATE TABLE IF NOT EXISTS device(name VARCHAR,consumption VARCHAR,status VARCHAR);");
            EditText editName = (EditText) findViewById(R.id.editNameButton);
            if (editName.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Error, Please enter right value", Toast.LENGTH_SHORT).show();
                    return;
                }
            Cursor c=db.rawQuery("SELECT Name FROM Electrical_Devices WHERE Name='"+editName.getText().toString()+" '", null);
            if(c.moveToNext())
                {   // Deleting record if found
                    db.execSQL("DELETE FROM Electrical_Devices WHERE Name='"+editName.getText().toString()+"'");
                    Toast.makeText(getApplicationContext(), "Success! Device deleted!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error, Nothing happened! Sorry!", Toast.LENGTH_SHORT).show();
                }

        }
    };



    public View.OnClickListener showButtonListener = new View.OnClickListener(){

        public void onClick(View v) {
            showList();

        }

    };

    public View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            EditText editName = (EditText) findViewById(R.id.editNameButton);
            EditText editConsumption = (EditText) findViewById(R.id.editConsumptionButton);
            //EditText editStatus = (EditText) findViewById(R.id.editStatusButton);
            String name = editName.getText().toString();
            String watts = editConsumption.getText().toString();
            Devices new_device = new Devices(name, watts);
            //Toast.makeText(getApplicationContext(), "DB created", Toast.LENGTH_SHORT).show();
            // Adding a record
            // Checking empty fields
            if(
                    editName.getText().toString().trim().length()==0||
                    editConsumption.getText().toString().trim().length()==0) // ||
                    //editStatus.getText().toString().trim().length()==0)
            {
                    Toast.makeText(getApplicationContext(), "Error, Please enter all values", Toast.LENGTH_SHORT).show();
                    return;
            }
            // Inserting record
            db.addDevice(new_device);
            Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();


//            // Deleting a record
//            if(view==btnDelete)
//            {
//                // Checking empty roll number
//                if(editRollno.getText().toString().trim().length()==0)
//                {
//                    showMessage("Error", "Please enter Rollno");
//                    return;
//                }
//                // Searching roll number
//                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
//                if(c.moveToFirst())
//                {
//                    // Deleting record if found
//                    db.execSQL("DELETE FROM student WHERE rollno='"+editRollno.getText()+"'");
//                    ("Success", "Record Deleted");
//                }
//                else
//                {
//                    showMessage("Error", "Invalid Rollno");
//                }
//                clearText();
//            }
//// Modifying a record
//            if(view==btnModify)
//            {
//                // Checking empty roll number
//                if(editRollno.getText().toString().trim().length()==0)
//                {
//                    showMessage("Error", "Please enter Rollno");
//                    return;
//                }
//                // Searching roll number
//                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
//                if(c.moveToFirst())
//                {
//                    // Modifying record if found
//                    db.execSQL("UPDATE student SET name='"+editName.getText()+"',marks='"+editMarks.getText()+
//                            "' WHERE rollno='"+editRollno.getText()+"'");
//                    showMessage("Success", "Record Modified");
//                }
//                else
//                {
//                    showMessage("Error", "Invalid Rollno");
//                }
//                clearText();
//            }
//// Viewing a record
//            if(view==btnView)
//            {
//                // Checking empty roll number
//                if(editRollno.getText().toString().trim().length()==0)
//                {
//                    showMessage("Error", "Please enter Rollno");
//                    return;
//                }
//                // Searching roll number
//                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
//                if(c.moveToFirst())
//                {
//                    // Displaying record if found
//                    editName.setText(c.getString(1));
//                    editMarks.setText(c.getString(2));
//                }
//                else
//                {
//                    showMessage("Error", "Invalid Rollno");
//                    clearText();
//                }
//            }
//// Viewing all records
//            if(view==btnViewAll)
//            {
//                // Retrieving all records
//                Cursor c=db.rawQuery("SELECT * FROM student", null);
//                // Checking if no records found
//                if(c.getCount()==0)
//                {
//                    showMessage("Error", "No records found");
//                    return;
//                }
//                // Appending records to a string buffer
//                StringBuffer buffer=new StringBuffer();
//                while(c.moveToNext())
//                {
//                    buffer.append("Rollno: "+c.getString(0)+"\n");
//                    buffer.append("Name: "+c.getString(1)+"\n");
//                    buffer.append("Marks: "+c.getString(2)+"\n\n");
//                }
//                // Displaying all records
//                showMessage("Student Details", buffer.toString());
//            }
//// Displaying info
//            if(view==btnShowInfo)
//            {
//                showMessage("Student Management Application", "Developed By Azim");
//            }
        }
    };

    public void showList(){
    //Showing the list of the saved devices
    if(db != null) {
        List<Devices> data_display = db.getAllDevices();
        ArrayAdapter<Devices> adapter = new ArrayAdapter<Devices>(this, android.R.layout.simple_list_item_1 , data_display);
        ListView myList = (ListView) findViewById(R.id.listView3);
        myList.setAdapter(adapter);

    }
}



}
