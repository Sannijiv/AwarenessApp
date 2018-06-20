package com.example.sanjiv.awarenessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditLamp extends AppCompatActivity implements View.OnClickListener {

    private EditText naam, brightness, pixel0, pixel1, pixel2, pixel3, maxDecibel, huidigeDecibel;
    private Button confirmEdit;
    private DatabaseReference mDatabase;
    String key;
    String lampNaam;
    int lampBrightness, lampMaxDecibel, lampHuidigeDecibel;
    long lampPixel0, lampPixel1, lampPixel2, lampPixel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lamp);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        naam = findViewById(R.id.naamLamp);
        maxDecibel = findViewById(R.id.MaxDecibel);
        huidigeDecibel = findViewById(R.id.HuidigeDecibel);

        confirmEdit = findViewById(R.id.editLamp);

        confirmEdit.setOnClickListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .95), (int) (height * .9));

        naam.setText(getIntent().getStringExtra("naam"));
        maxDecibel.setText(getIntent().getStringExtra("maxDecibel"));
        huidigeDecibel.setText(getIntent().getStringExtra("huidigeDecibel"));



    }

    @Override
    public void onClick(View v) {
        if (v == confirmEdit) {
            if (TextUtils.isEmpty(naam.getText().toString().trim())) {
                Toast.makeText(this, "Naamveld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(String.valueOf(maxDecibel.getText().toString().trim()))) {
                Toast.makeText(this, "Maxdecibel veld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(String.valueOf(huidigeDecibel.getText().toString().trim()))) {
                Toast.makeText(this, "Huidige decibel veld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }



            EditLamp();
        }
    }

    public void EditLamp() {
        lampNaam = naam.getText().toString().trim();
        lampMaxDecibel = Integer.parseInt(maxDecibel.getText().toString().trim());
        lampHuidigeDecibel = Integer.parseInt(huidigeDecibel.getText().toString().trim());
        key = getIntent().getStringExtra("key");

        LampModel lamp = new LampModel(lampNaam, lampMaxDecibel, lampHuidigeDecibel, key);
        mDatabase.child("lamps").child(key).setValue(lamp);
        Toast.makeText(this, "Lamp bewerkt", Toast.LENGTH_LONG).show();

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


}
