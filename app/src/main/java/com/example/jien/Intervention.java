package com.example.jien;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Intervention {
    private String interventionName;
    private String timeFrom;
    private String timeTo;
    private LocalDate day;

    public Intervention(String interventionName, LocalDate day) {
        this.interventionName = interventionName;
        this.day = day;
    }

    public String getInterventionName() {
        return interventionName;
    }

    public void setInterventionName(String interventionName) {
        this.interventionName = interventionName;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int hour = currentDateTime.getHour();
        int minute = currentDateTime.getMinute();
        int second = currentDateTime.getSecond();
        this.timeFrom = String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String getTimeTo() { return timeTo; }

    public void setTimeTo() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int hour = currentDateTime.getHour();
        int minute = currentDateTime.getMinute();
        int second = currentDateTime.getSecond();
        this.timeTo = String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
