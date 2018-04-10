package com.example.sanjiv.awarenessapp;

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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPage extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText emailInput;
    private Button confirm;
    private TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_page);

        mAuth = FirebaseAuth.getInstance();

        emailInput = (EditText) findViewById(R.id.resetEmail);
        confirm = (Button) findViewById(R.id.confirmReset);
        backToLogin = (TextView) findViewById(R.id.resetToLogin);

        confirm.setOnClickListener(this);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPage.this, Login.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == confirm) {
            passwordReset();
        }
    }


    private void passwordReset() {
        String email = emailInput.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Emailveld is leeg!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPage.this, "Resetmail verzonden",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ResetPage.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ResetPage.this, "Reset mislukt!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}