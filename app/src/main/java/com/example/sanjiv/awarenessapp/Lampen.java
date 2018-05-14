package com.example.sanjiv.awarenessapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lampen extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private FloatingActionButton add;
    private List<LampModel> lampList;
    private LampAdapter adapter;
    private String userRole;

    public Lampen() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lampen, null);

        add = v.findViewById(R.id.fab);
        add.setOnClickListener(this);
        recyclerView = (RecyclerView) v.findViewById(R.id.lampView);
        recyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

        lampList = new ArrayList<>();

        adapter = new LampAdapter(lampList, this.getContext());
        recyclerView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();
        mDatabase = database.getReference();

        database.getReference("lampen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lampList.removeAll(lampList);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LampModel lamps = snapshot.getValue(LampModel.class);
                    lampList.add(lamps);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        if (v == add) {
            retrieveUserRole();
        }
    }

    public void retrieveUserRole() {
        DatabaseReference ref_userRole = mDatabase.child("users").child(user.getUid()).child("rollen");

        ref_userRole.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userM = dataSnapshot.getValue(UserModel.class);
                userRole = userM.getUserRole();
                Log.d("Lamps", "Value from database: " + userRole);
                checkAllowed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d("Lamps", "credentialsUserRoleGet:failure", databaseError.toException());
            }
        });

    }

    public void checkAllowed() {
        if (userRole != null) {
            if (userRole.equalsIgnoreCase("admin")) {
                Intent intent = new Intent(this.getContext(), AddLamp.class);
                this.getContext().startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Geen rechten om een lamp toe te voegen", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Geen rol verbonden aan user", Toast.LENGTH_SHORT).show();
        }
    }
}
