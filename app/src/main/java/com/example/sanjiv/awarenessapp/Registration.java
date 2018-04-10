package com.example.sanjiv.awarenessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText passwordVerify;
    private Button confirmation;
    private TextView backToLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private String password, email, verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        emailInput = (EditText) findViewById(R.id.registEmail);
        passwordInput = (EditText) findViewById(R.id.registPassword);
        passwordVerify = (EditText) findViewById(R.id.registConfirmPassword);
        confirmation = (Button) findViewById(R.id.confirm);
        backToLogin = (TextView) findViewById(R.id.backToLogin);


        confirmation.setOnClickListener(this);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {

        email = emailInput.getText().toString().trim();
        password = passwordInput.getText().toString().trim();
        verify = passwordVerify.getText().toString().trim();



        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Emailveld is leeg!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Wachtwoordveld is leeg!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(verify)) {
            Toast.makeText(this, "Wachtwoorden komen niet overeen!", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Wachtwoord is te kort", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Bezig met registreren");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registration.this, "Registratie succesvol", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registration.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Registration.this, "Registratie niet gelukt!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }



    @Override
    public void onClick(View v) {
        if (v == confirmation) {
            registerUser();
        }

    }


}