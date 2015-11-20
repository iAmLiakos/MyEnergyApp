package com.iamliakos.macbook.myenergyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MonitorActivity extends AppCompatActivity {
    database db1 = new database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

}

}
