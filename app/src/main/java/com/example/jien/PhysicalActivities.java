package com.example.jien;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PhysicalActivities {
    private String aktivityName;
    private String timeFrom;
    private String timeTo;
    LocalDate day;
        public PhysicalActivities(String aktivityName, LocalDate day){
            this.aktivityName=aktivityName;
            this.day=day;


        }


    public String getActivity() {
        return aktivityName;
    }

    public void setActivity(String activity) {
        this.aktivityName = activity;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom() { LocalDateTime currentDateTime = LocalDateTime.now();
        int hour = currentDateTime.getHour();
        int minute = currentDateTime.getMinute();
        int second = currentDateTime.getSecond();
        this.timeFrom = String.format("%02d:%02d:%02d", hour, minute, second);

    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo() { LocalDateTime currentDateTime = LocalDateTime.now();
        int hour = currentDateTime.getHour();
        int minute = currentDateTime.getMinute();
        int second = currentDateTime.getSecond();
        this.timeTo = String.format("%02d:%02d:%02d", hour, minute, second);

    }
}

