package com.example.sanjiv.awarenessapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PiAdapter extends RecyclerView.Adapter<PiAdapter.PiviewHolder> {

    List<PiModel> piList;
    Context ctx;
    int unique;
    String TAG = "PiAdapter";

    public PiAdapter() {
        // Required Empty Constructor
    }

    public PiAdapter(List<PiModel> piList, Context ctx) {
        this.piList = piList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public PiviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pi_adapter, parent, false);
        PiviewHolder holder = new PiviewHolder(v, ctx, piList);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PiviewHolder holder, int position) {
        PiModel piModel = piList.get(position);
        holder.piNaam.setText("Naam:" + piModel.getApName());
        holder.piPassword.setText("Code:" + piModel.getApPassword() );
        holder.piData.setText("newData:" + piModel.getNewdata());
        holder.piUser.setText("userId:" +  piModel.getUserid());

    }

    @Override
    public int getItemCount() {
        return piList.size();
    }


    public static class PiviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView piNaam, piPassword, piData, piUser;
        CardView piCards;
        List<PiModel> piList;
        Context ctx;

        public PiviewHolder(View v, Context ctx, List<PiModel> piList) {
            super(v);
            this.piList = piList;
            this.ctx = ctx;
            v.setOnClickListener(this);
            piNaam = v.findViewById(R.id.piNaam);
            piPassword = v.findViewById(R.id.piPassword);
            piData = v.findViewById(R.id.piData);
            piUser = v.findViewById(R.id.piUser);
            piCards = v.findViewById(R.id.Pi);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            PiModel piModel = this.piList.get(position);
            Intent intent = new Intent(this.ctx, PiDetails.class);
            String newData = String.valueOf(piModel.getNewdata());

            intent.putExtra("piName", piModel.getApName());
            intent.putExtra("piPassword", piModel.getApPassword());
            intent.putExtra("newData", newData);
            intent.putExtra("userId", piModel.getUserid());
            intent.putExtra("key", piModel.getKey());

            this.ctx.startActivity(intent);
        }
    }

}
