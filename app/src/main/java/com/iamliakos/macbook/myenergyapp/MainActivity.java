package com.iamliakos.macbook.myenergyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutButton = (Button)findViewById(R.id.aboutbutton);
        aboutButton.setOnClickListener(aboutButtonListener);
        Button addButton = (Button)findViewById(R.id.addbutton);
        addButton.setOnClickListener(addButtonListener);

    }

    public View.OnClickListener aboutButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            aboutcall();
        }
    };

    public View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addcall();
        }
    };

    public void aboutcall(){
        Intent about = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(about);

    }
    public void addcall(){
        Intent add = new Intent(MainActivity.this, AddActivity.class);
        startActivity(add);

    }
}
