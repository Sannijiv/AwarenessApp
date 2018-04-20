package com.example.sanjiv.awarenessapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LampAdapter extends RecyclerView.Adapter<LampAdapter.LampviewHolder> {

    List<LampModel> lampList;
    Context ctx;

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

        if (lampModel.getBrightness() > 50) {
            holder.lampCards.setCardBackgroundColor(Color.parseColor("#ff0000"));
        }

        holder.lampNaam.setText("Naam:" + lampModel.getNaam());
        holder.lampBrightness.setText("Brightness:" + lampModel.getBrightness());
        holder.lampPixel0.setText("Pixel 0:" + lampModel.getPixel0());
        holder.lampPixel1.setText("Pixel 1:" + lampModel.getPixel1());
        holder.lampPixel2.setText("Pixel 2:" + lampModel.getPixel2());
        holder.lampPixel3.setText("Pixel 3:" + lampModel.getPixel3());

    }

    @Override
    public int getItemCount() {
        return lampList.size();
    }


    public static class LampviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView lampNaam, lampBrightness, lampPixel0, lampPixel1, lampPixel2, lampPixel3;
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
            lampPixel0 = v.findViewById(R.id.lampPixel0);
            lampPixel1 = v.findViewById(R.id.lampPixel1);
            lampPixel2 = v.findViewById(R.id.lampPixel2);
            lampPixel3 = v.findViewById(R.id.lampPixel3);
            lampCards = v.findViewById(R.id.Lamp);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            LampModel lampModel = this.lampList.get(position);
            Intent intent = new Intent(this.ctx, LampDetails.class);

            String brightnessString, pixel0String, pixel1String, pixel2String, pixel3String, huidigeDecibelString, maxDecibelString;
            brightnessString = String.valueOf(lampModel.getBrightness());
            pixel0String = String.valueOf(lampModel.getPixel0());
            pixel1String = String.valueOf(lampModel.getPixel1());
            pixel2String = String.valueOf(lampModel.getPixel2());
            pixel3String = String.valueOf(lampModel.getPixel3());
            huidigeDecibelString = String.valueOf(lampModel.getHuidigeDecibel());
            maxDecibelString = String.valueOf(lampModel.getMaxDecibel());


            intent.putExtra("naam", lampModel.getNaam());

            intent.putExtra("brightness", brightnessString);
            intent.putExtra("pixel0", pixel0String);
            intent.putExtra("pixel1", pixel1String);
            intent.putExtra("pixel2", pixel2String);
            intent.putExtra("pixel3", pixel3String);
            intent.putExtra("huidigeDecibel", huidigeDecibelString);
            intent.putExtra("maxDecibel", maxDecibelString);

            this.ctx.startActivity(intent);

        }
    }


}
