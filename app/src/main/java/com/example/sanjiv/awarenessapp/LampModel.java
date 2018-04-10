package com.example.sanjiv.awarenessapp;

public class LampModel {

    private String brightness, pixel0, pixel1, pixel2, pixel3;

    public LampModel() {

    }

    public LampModel(String brightness,String pixel0,String pixel1,String pixel2, String pixel3) {
        this.brightness = brightness;
        this.pixel0 = pixel0;
        this.pixel1 = pixel1;
        this.pixel2 = pixel2;
        this.pixel3 = pixel3;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getPixel0() {
        return pixel0;
    }

    public void setPixel0(String pixel0) {
        this.pixel0 = pixel0;
    }

    public String getPixel1() {
        return pixel1;
    }

    public void setPixel1(String pixel1) {
        this.pixel1 = pixel1;
    }

    public String getPixel2() {
        return pixel2;
    }

    public void setPixel2(String pixel2) {
        this.pixel2 = pixel2;
    }

    public String getPixel3() {
        return pixel3;
    }

    public void setPixel3(String pixel3) {
        this.pixel3 = pixel3;
    }
}
