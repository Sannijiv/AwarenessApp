package com.example.sanjiv.awarenessapp;

import android.graphics.Point;

public class PointValue {
    long time;
    int decibellevel;

    public PointValue(){

    }

    public PointValue(int decibellevel,long time) {
        this.decibellevel = decibellevel;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDecibellevel() {
        return decibellevel;
    }

    public void setDecibellevel(int decibellevel) {
        this.decibellevel = decibellevel;
    }
}
