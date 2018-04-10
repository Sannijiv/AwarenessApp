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

public class LampAdapter extends RecyclerView.Adapter<LampAdapter.LampviewHolder> {

    List<LampModel> lampList;
    Context ctx;

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

        holder.lampNaam.setText("Lamp");
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

            intent.putExtra("naam", "Lamp");
            intent.putExtra("brightness", lampModel.getBrightness());
            intent.putExtra("pixel0", lampModel.getPixel0());
            intent.putExtra("pixel1", lampModel.getPixel1());
            intent.putExtra("pixel2", lampModel.getPixel2());
            intent.putExtra("pixel3", lampModel.getPixel3());
            this.ctx.startActivity(intent);

        }
    }


}
