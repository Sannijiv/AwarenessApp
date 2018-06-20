package com.example.sanjiv.awarenessapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class LampAdapter extends RecyclerView.Adapter<LampAdapter.LampviewHolder> {

    List<LampModel> lampList;
    Context ctx;
    int unique;
    String TAG = "LampAdapter";

    public LampAdapter() {
        // Required Empty Constructor
    }

    public LampAdapter(List<LampModel> lampList, Context ctx) {
        this.lampList = lampList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public LampviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_lamp_adapter, parent, false);
        LampviewHolder holder = new LampviewHolder(v, ctx, lampList);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LampviewHolder holder, int position) {
        LampModel lampModel = lampList.get(position);


        //Check voor decibelniveau
        if (lampModel.getHuidigeDecibel() > lampModel.getMaxDecibel()) {
            holder.lampCards.setCardBackgroundColor(Color.parseColor("#ff0000"));
        }


        holder.lampNaam.setText("Naam:" + lampModel.getNaam());
        holder.lampHuidigeDecibel.setText("Huidige geluidsniveau: " + lampModel.getHuidigeDecibel() + "db");
        holder.lampMaxDecibel.setText("Maximale geluidsniveau: " + lampModel.getMaxDecibel() + "db");


    }

    @Override
    public int getItemCount() {
        return lampList.size();
    }


    public static class LampviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView lampNaam, lampBrightness, lampHuidigeDecibel, lampMaxDecibel, lampPixel2, lampPixel3;
        CardView lampCards;
        List<LampModel> lampList;
        Context ctx;

        public LampviewHolder(View v, Context ctx, List<LampModel> lampList) {
            super(v);
            this.lampList = lampList;
            this.ctx = ctx;
            v.setOnClickListener(this);
            lampNaam = v.findViewById(R.id.lampNaam);
            lampBrightness = v.findViewById(R.id.lampBrightness);
            lampHuidigeDecibel = v.findViewById(R.id.lampHuidigeDecibel);
            lampMaxDecibel = v.findViewById(R.id.lampMaxDecibel);
            lampCards = v.findViewById(R.id.Lamp);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            LampModel lampModel = this.lampList.get(position);
            Intent intent = new Intent(this.ctx, LampDetails.class);

            String brightnessString, pixel0String, pixel1String, pixel2String, pixel3String, huidigeDecibelString, maxDecibelString,notifyOnString;

            huidigeDecibelString = String.valueOf(lampModel.getHuidigeDecibel());
            maxDecibelString = String.valueOf(lampModel.getMaxDecibel());
            notifyOnString = String.valueOf(lampModel.isNotifyOn());

            intent.putExtra("naam", lampModel.getNaam());
            intent.putExtra("key", lampModel.getKey());
            intent.putExtra("huidigeDecibel", huidigeDecibelString);
            intent.putExtra("maxDecibel", maxDecibelString);
            intent.putExtra("notifyOn", notifyOnString);

            this.ctx.startActivity(intent);

        }
    }


}
