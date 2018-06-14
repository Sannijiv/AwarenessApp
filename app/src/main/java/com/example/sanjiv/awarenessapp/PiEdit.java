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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PiEdit extends AppCompatActivity implements View.OnClickListener {
    private EditText piNaam, piPassword;
    private String naam, password,key ,userId;
    private Button confirmEdit;
    private DatabaseReference mDatabase;
    private int newData;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_edit);

        piNaam = findViewById(R.id.naamPiEdit);
        piPassword = findViewById(R.id.passwordPiEdit);
        confirmEdit = findViewById(R.id.confirm);
        confirmEdit.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .95), (int) (height * .9));


        piNaam.setText(getIntent().getStringExtra("piName"));
        piPassword.setText(getIntent().getStringExtra("piPassword"));


    }

    @Override
    public void onClick(View v) {
        if (v == confirmEdit) {

            if (TextUtils.isEmpty(piNaam.getText().toString().trim())) {
                Toast.makeText(this, "Naamveld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            if (TextUtils.isEmpty(piPassword.getText().toString().trim())) {
                Toast.makeText(this, "Passwordveld is leeg!", Toast.LENGTH_LONG).show();
                return;
            }

            EditPi();
        }

    }

    private void EditPi() {
        naam = piNaam.getText().toString().trim();
        password = piPassword.getText().toString().trim();

        key = getIntent().getStringExtra("key");
        newData = 1;
        PiModel pi = new PiModel(naam, password, newData, user.getUid(), key);
        mDatabase.child("pis").child(key).setValue(pi);
        Toast.makeText(this, "Pi bewerkt", Toast.LENGTH_LONG).show();

        finish();
        startActivity(new Intent(this, MainActivity.class));

    }
}
