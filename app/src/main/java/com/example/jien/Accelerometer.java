package com.example.jien;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Accelerometer<T extends IABase> {
    private String interActName;
    private float x;
    private float y;
    private float z;
    private String timeStamp;

    public Accelerometer(T interActName, float x, float y, float z) {
        this.interActName = interActName.getName();
        this.x = x;
        this.y = y;
        this.z = z;
        SimpleDateFormat tempTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        this.timeStamp = tempTime.format(date);
    }

    public String getInterActName() {
        return interActName;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
