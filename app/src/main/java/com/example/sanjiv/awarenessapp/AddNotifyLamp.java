package com.example.sanjiv.awarenessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNotifyLamp extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private FirebaseAuth auth;

    String key;
    String lampNaam;
    int lampBrightness, lampMaxDecibel, lampHuidigeDecibel;
    long lampPixel0, lampPixel1, lampPixel2, lampPixel3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notify_lamp);

        lampNaam = getIntent().getStringExtra("naam");
        lampBrightness = Integer.parseInt(getIntent().getStringExtra("brightness"));
        lampMaxDecibel = Integer.parseInt(getIntent().getStringExtra("maxDecibel"));
        lampHuidigeDecibel = Integer.parseInt(getIntent().getStringExtra("huidigeDecibel"));
        lampPixel0 = Long.parseLong(getIntent().getStringExtra("pixel0"));
        lampPixel1 = Long.parseLong(getIntent().getStringExtra("pixel1"));
        lampPixel2 = Long.parseLong(getIntent().getStringExtra("pixel2"));
        lampPixel3 = Long.parseLong(getIntent().getStringExtra("pixel3"));
        key = getIntent().getStringExtra("key");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        LampModel lamp = new LampModel(lampNaam, lampBrightness, lampMaxDecibel, lampHuidigeDecibel, lampPixel0, lampPixel1, lampPixel2, lampPixel3, key);
        mDatabase.child("users").child(user.getUid()).child("lampnotificaties").child(key).setValue(lamp);

        Toast.makeText(this, "Lamp toegevoegd aan notificatielijst", Toast.LENGTH_LONG).show();

        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
