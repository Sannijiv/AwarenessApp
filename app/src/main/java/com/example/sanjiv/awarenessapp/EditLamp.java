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
        brightness = findViewById(R.id.brightnessLamp);
        maxDecibel = findViewById(R.id.MaxDecibel);
        huidigeDecibel = findViewById(R.id.HuidigeDecibel);
        pixel0 = findViewById(R.id.pixel0Lamp);
        pixel1 = findViewById(R.id.pixel1Lamp);
        pixel2 = findViewById(R.id.pixel2Lamp);
        pixel3 = findViewById(R.id.pixel3Lamp);
        confirmEdit = findViewById(R.id.editLamp);

        confirmEdit.setOnClickListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .95), (int) (height * .9));

        naam.setText(getIntent().getStringExtra("naam"));
        brightness.setText(getIntent().getStringExtra("brightness"));
        maxDecibel.setText(getIntent().getStringExtra("maxDecibel"));
        huidigeDecibel.setText(getIntent().getStringExtra("huidigeDecibel"));
        pixel0.setText(getIntent().getStringExtra("pixel0"));
        pixel1.setText(getIntent().getStringExtra("pixel1"));
        pixel2.setText(getIntent().getStringExtra("pixel2"));
        pixel3.setText(getIntent().getStringExtra("pixel3"));


    }

    @Override
    public void onClick(View v) {
        if (v == confirmEdit) {
            if (TextUtils.isEmpty(naam.getText().toString().trim())) {
                Toast.makeText(this, "Naamveld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(String.valueOf(brightness.getText().toString().trim()))) {
                Toast.makeText(this, "Brightness veld is leeg!", Toast.LENGTH_LONG).show();
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

            if (TextUtils.isEmpty(String.valueOf(pixel0.getText().toString().trim()))) {
                Toast.makeText(this, "Pixel0 veld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(String.valueOf(pixel1.getText().toString().trim()))) {
                Toast.makeText(this, "Pixel1 veld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(String.valueOf(pixel2.getText().toString().trim()))) {
                Toast.makeText(this, "Pixel2 veld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(String.valueOf(pixel3.getText().toString().trim()))) {
                Toast.makeText(this, "Pixel3 veld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            EditLamp();
        }
    }

    public void EditLamp() {
        lampNaam = naam.getText().toString().trim();
        lampBrightness = Integer.parseInt(brightness.getText().toString().trim());
        lampMaxDecibel = Integer.parseInt(maxDecibel.getText().toString().trim());
        lampHuidigeDecibel = Integer.parseInt(huidigeDecibel.getText().toString().trim());
        lampPixel0 = Long.parseLong(pixel0.getText().toString().trim());
        lampPixel1 = Long.parseLong(pixel1.getText().toString().trim());
        lampPixel2 = Long.parseLong(pixel2.getText().toString().trim());
        lampPixel3 = Long.parseLong(pixel3.getText().toString().trim());
        key = getIntent().getStringExtra("key");

        LampModel lamp = new LampModel(lampNaam, lampBrightness, lampMaxDecibel, lampHuidigeDecibel, lampPixel0, lampPixel1, lampPixel2, lampPixel3, key);
        mDatabase.child("lampen").child(key).setValue(lamp);
        Toast.makeText(this, "Lamp bewerkt", Toast.LENGTH_LONG).show();

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


}
