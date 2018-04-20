package com.example.sanjiv.awarenessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLamp extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;
    EditText lampNaam, lampMaxDecibel;
    Button addLamp;
    String naam;
    int maxDecibel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lamp);

        lampNaam = findViewById(R.id.inputLampNaam);
        lampMaxDecibel = findViewById(R.id.inputMaxDecibel);
        addLamp = findViewById(R.id.addLamp);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        addLamp.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == addLamp) {
            naam = lampNaam.getText().toString().trim();
            maxDecibel = Integer.parseInt(lampMaxDecibel.getText().toString());

            AddLamp(naam, maxDecibel);

        }
    }

    public void AddLamp(String naam, int maxDecibel) {


        if (TextUtils.isEmpty(naam)) {
            Toast.makeText(this, "Naamveld is leeg!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(String.valueOf(maxDecibel))) {
            Toast.makeText(this, "Max decibel veld is leeg!", Toast.LENGTH_LONG).show();
            return;
        }

        String key = mDatabase.push().getKey();
        LampModel lamp = new LampModel(naam, maxDecibel);
        mDatabase.child("lampen").child(key).setValue(lamp);
        Toast.makeText(this, "Lamp toegevoegd", Toast.LENGTH_LONG).show();

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

}
