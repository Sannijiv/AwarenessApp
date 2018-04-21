package com.example.sanjiv.awarenessapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LampDetails extends AppCompatActivity implements View.OnClickListener {

    private TextView naam, brightness, pixel0, pixel1, pixel2, pixel3, maxDecibel, huidigeDecibel;
    private Button edit;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_details);


        naam = findViewById(R.id.naamLamp);
        brightness = findViewById(R.id.brightnessLamp);
        maxDecibel = findViewById(R.id.MaxDecibel);
        huidigeDecibel = findViewById(R.id.HuidigeDecibel);
        pixel0 = findViewById(R.id.pixel0Lamp);
        pixel1 = findViewById(R.id.pixel1Lamp);
        pixel2 = findViewById(R.id.pixel2Lamp);
        pixel3 = findViewById(R.id.pixel3Lamp);
        edit = findViewById(R.id.editLamp);

        edit.setOnClickListener(this);
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
        if(v == edit){
            Intent intent = new Intent(this,EditLamp.class);

            intent.putExtra("naam", getIntent().getStringExtra("naam"));
            intent.putExtra("brightness",getIntent().getStringExtra("brightness"));
            intent.putExtra("key",getIntent().getStringExtra("key"));
            intent.putExtra("pixel0", getIntent().getStringExtra("pixel0"));
            intent.putExtra("pixel1", getIntent().getStringExtra("pixel1"));
            intent.putExtra("pixel2", getIntent().getStringExtra("pixel2"));
            intent.putExtra("pixel3", getIntent().getStringExtra("pixel3"));
            intent.putExtra("huidigeDecibel",getIntent().getStringExtra("huidigeDecibel"));
            intent.putExtra("maxDecibel",getIntent().getStringExtra("maxDecibel"));

            this.startActivity(intent);
        }
    }
}
