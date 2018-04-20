package com.example.sanjiv.awarenessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddLamp extends AppCompatActivity implements View.OnClickListener {

    EditText lampNaam, lampMaxDecibel;
    Button addLamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lamp);

        lampNaam = findViewById(R.id.inputLampNaam);
        lampMaxDecibel = findViewById(R.id.inputMaxDecibel);
        addLamp = findViewById(R.id.addLamp);

        addLamp.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == addLamp) {
            AddLamp();

        }
    }

    public void AddLamp() {


    }

}
