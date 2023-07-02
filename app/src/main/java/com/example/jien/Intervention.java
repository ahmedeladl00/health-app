package com.example.jien;

import java.time.LocalDate;
import java.util.Date;

public class Intervention {
    private String interventionName;
    private long timeFrom;
    private long timeTo;
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

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }
}
