package com.example.sanjiv.awarenessapp;

public class LampModel {
    private int brightness, maxDecibel, huidigeDecibel;
    private String naam;
    private long pixel0, pixel1, pixel2, pixel3;

    public LampModel() {

    }

    public LampModel(int brightness, int huidigeDecibel, int maxDecibel, String naam, long pixel0, long pixel1, long pixel2, long pixel3) {
        this.brightness = brightness;
        this.huidigeDecibel = huidigeDecibel;
        this.maxDecibel = maxDecibel;
        this.naam = naam;
        this.pixel0 = pixel0;
        this.pixel1 = pixel1;
        this.pixel2 = pixel2;
        this.pixel3 = pixel3;
    }

    public int getBrightness() {
        return brightness;
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

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public long getPixel0() {
        return pixel0;
    }

    public void setPixel0(long pixel0) {
        this.pixel0 = pixel0;
    }

    public long getPixel1() {
        return pixel1;
    }

    public void setPixel1(long pixel1) {
        this.pixel1 = pixel1;
    }

    public long getPixel2() {
        return pixel2;
    }

    public void setPixel2(long pixel2) {
        this.pixel2 = pixel2;
    }

    public long getPixel3() {
        return pixel3;
    }

    public void setPixel3(long pixel3) {
        this.pixel3 = pixel3;
    }
}
