package com.example.sanjiv.awarenessapp;

public class LampModel {
    private int maxDecibel, huidigeDecibel;
    private String naam, key;
    boolean notifyOn;

    public LampModel() {

    }

    public LampModel(String naam, int maxDecibel, String key) {
        this.naam = naam;
        this.maxDecibel = maxDecibel;
        this.huidigeDecibel = 0;
        this.key = key;
        this.notifyOn = false;
    }

    public LampModel(String naam, int maxDecibel, int huidigeDecibel, String key) {
        this.huidigeDecibel = huidigeDecibel;
        this.maxDecibel = maxDecibel;
        this.naam = naam;
        this.key = key;
        this.notifyOn = false;
    }

    public LampModel(String naam, int maxDecibel, int huidigeDecibel,String key, boolean notifyOn) {
        this.huidigeDecibel = huidigeDecibel;
        this.maxDecibel = maxDecibel;
        this.naam = naam;
        this.key = key;
        this.notifyOn = notifyOn;
    }

    public boolean isNotifyOn() {
        return notifyOn;
    }

    public void setNotifyOn(boolean notifyOn) {
        this.notifyOn = notifyOn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public int getMaxDecibel() {
        return maxDecibel;
    }

    public void setMaxDecibel(int maxDecibel) {
        this.maxDecibel = maxDecibel;
    }

    public int getHuidigeDecibel() {
        return huidigeDecibel;
    }

    public void setHuidigeDecibel(int huidigeDecibel) {
        this.huidigeDecibel = huidigeDecibel;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }


}
