package com.example.jien;

import java.util.Date;

public class PhysicalActivities {
    private Date day;
    private long timeFrom;
    private long timeTo;
    private String activity;
    public PhysicalActivities(Date day ,int timeFrom,int timeTo,String activity){
        this.day=day;
        this.timeFrom=timeFrom;
        this.timeTo=timeTo;
        this.activity=activity;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(long timeTo) {
        this.timeTo = timeTo;
    }
}

