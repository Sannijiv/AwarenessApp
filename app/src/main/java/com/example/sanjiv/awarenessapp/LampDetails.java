package com.example.sanjiv.awarenessapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LampDetails extends AppCompatActivity implements View.OnClickListener {

    private TextView naam, brightness, pixel0, pixel1, pixel2, pixel3, maxDecibel, huidigeDecibel;
    private Button edit, notify, removeNotify;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String key, lampNaam, userRole;
    private boolean isNotifyOn;
    private Lampen lamp;
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
        notify = findViewById(R.id.addNotify);
        removeNotify = findViewById(R.id.removeNotify);

        edit.setOnClickListener(this);
        notify.setOnClickListener(this);
        removeNotify.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        mDatabase = database.getReference();


        //Scherm in het fragment, backPress brengt je terug naar Lampen fragment
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

        isNotifyOn = Boolean.parseBoolean(getIntent().getStringExtra("notifyOn"));

        Log.d("ADebugTag", "notifyOn = " + isNotifyOn);

        edit.setVisibility(View.GONE);
        notify.setVisibility(View.GONE);
        removeNotify.setVisibility(View.GONE);

        if (isNotifyOn) {
            removeNotify.setVisibility(View.VISIBLE);
        } else {
            notify.setVisibility(View.VISIBLE);
        }

        userIsAdmin();

    }

    @Override
    public void onClick(View v) {
        if (v == edit) {
            Intent intent = new Intent(this, EditLamp.class);

            intent.putExtra("naam", getIntent().getStringExtra("naam"));
            intent.putExtra("brightness", getIntent().getStringExtra("brightness"));
            intent.putExtra("key", getIntent().getStringExtra("key"));
            intent.putExtra("pixel0", getIntent().getStringExtra("pixel0"));
            intent.putExtra("pixel1", getIntent().getStringExtra("pixel1"));
            intent.putExtra("pixel2", getIntent().getStringExtra("pixel2"));
            intent.putExtra("pixel3", getIntent().getStringExtra("pixel3"));
            intent.putExtra("huidigeDecibel", getIntent().getStringExtra("huidigeDecibel"));
            intent.putExtra("maxDecibel", getIntent().getStringExtra("maxDecibel"));

            this.startActivity(intent);
        }

        if (v == notify) {
            Intent intent = new Intent(this, AddNotifyLamp.class);

            intent.putExtra("naam", getIntent().getStringExtra("naam"));
            intent.putExtra("brightness", getIntent().getStringExtra("brightness"));
            intent.putExtra("key", getIntent().getStringExtra("key"));
            intent.putExtra("pixel0", getIntent().getStringExtra("pixel0"));
            intent.putExtra("pixel1", getIntent().getStringExtra("pixel1"));
            intent.putExtra("pixel2", getIntent().getStringExtra("pixel2"));
            intent.putExtra("pixel3", getIntent().getStringExtra("pixel3"));
            intent.putExtra("huidigeDecibel", getIntent().getStringExtra("huidigeDecibel"));
            intent.putExtra("maxDecibel", getIntent().getStringExtra("maxDecibel"));

            this.startActivity(intent);


        }

        if (v == removeNotify) {
            Intent intent = new Intent(this, DeleteNotifyLamp.class);
            intent.putExtra("key", getIntent().getStringExtra("key"));

            this.startActivity(intent);
        }
    }


    public void userIsAdmin() {

        DatabaseReference ref_userRole = mDatabase.child("users").child(user.getUid()).child("rollen");

        ref_userRole.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userM = dataSnapshot.getValue(UserModel.class);
                userRole = userM.getUserRole();
                Log.d("ADebugTag", "Value from database: " + userRole);
                updateButtons();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("Lamps", "credentialsUserRoleGet:failure", databaseError.toException());
            }
        });

    }

    public void updateButtons() {
        if (userRole != null) {
            if (userRole.equalsIgnoreCase("admin")) {
                Log.d("ADebugTag", "Value from updateButtons: " + userRole);
                edit.setVisibility(View.VISIBLE);
            }
        }
    }


}

