package com.example.sanjiv.awarenessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class LampDetails extends AppCompatActivity {

    private TextView naam, brightness, pixel0, pixel1, pixel2, pixel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_details);

        naam = findViewById(R.id.naamLamp);
        brightness = findViewById(R.id.brightnessLamp);
        pixel0 = findViewById(R.id.pixel0Lamp);
        pixel1 = findViewById(R.id.pixel1Lamp);
        pixel2 = findViewById(R.id.pixel2Lamp);
        pixel3 = findViewById(R.id.pixel3Lamp);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .95), (int) (height * .9));

        naam.setText(getIntent().getStringExtra("naam"));
        brightness.setText(getIntent().getStringExtra("brightness"));
        pixel0.setText(getIntent().getStringExtra("pixel0"));
        pixel1.setText(getIntent().getStringExtra("pixel1"));
        pixel2.setText(getIntent().getStringExtra("pixel2"));
        pixel3.setText(getIntent().getStringExtra("pixel3"));

    }
}
