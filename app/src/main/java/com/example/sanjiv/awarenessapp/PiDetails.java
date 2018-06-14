package com.example.sanjiv.awarenessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PiDetails extends AppCompatActivity implements View.OnClickListener {
    private TextView naam, password, newdata, userid;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_details);
        naam = findViewById(R.id.naamPi);
        password = findViewById(R.id.passwordPi);
        newdata = findViewById(R.id.dataPi);
        userid = findViewById(R.id.userPi);

        edit = findViewById(R.id.editPi);
        edit.setOnClickListener(this);


        naam.setText(getIntent().getStringExtra("piName"));
        password.setText(getIntent().getStringExtra("piPassword"));
        newdata.setText(getIntent().getStringExtra("newData"));
        userid.setText(getIntent().getStringExtra("userId"));



    }

    @Override
    public void onClick(View v) {
        if(v == edit){
            Intent intent = new Intent(this, PiEdit.class);

            intent.putExtra("piName", getIntent().getStringExtra("piName"));
            intent.putExtra("piPassword",getIntent().getStringExtra("piPassword"));
            intent.putExtra("newData", getIntent().getStringExtra("newData"));
            intent.putExtra("userId",getIntent().getStringExtra("userId"));
            intent.putExtra("key",getIntent().getStringExtra("key"));

            this.startActivity(intent);
        };
    }
}
