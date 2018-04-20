package com.example.sanjiv.awarenessapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Lampen extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FloatingActionButton add;
    List<LampModel> lampList;
    LampAdapter adapter;


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
        if(v == add){
            Intent intent = new Intent(this.getContext(), AddLamp.class);
            this.getContext().startActivity(intent);
        }
    }
}
