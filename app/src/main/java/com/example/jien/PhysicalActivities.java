package com.example.jien;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PhysicalActivities extends IABase{
    public PhysicalActivities() {
        super();
    }

    public PhysicalActivities(String name, LocalDate day) {
        super(name, day);
    }

    public PhysicalActivities(int id, String name, String timeFrom, String timeTo, LocalDate day) {
        super(id, name, timeFrom, timeTo, day);
    }
}

