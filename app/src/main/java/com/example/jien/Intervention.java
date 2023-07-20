package com.example.jien;

import java.time.LocalDate;

public class Intervention extends  IABase{

    public Intervention() {
        super();
    }

    public Intervention(String name, LocalDate day) {
        super(name, day);
    }

    public Intervention(int id, String name, String timeFrom, String timeTo, LocalDate day) {
        super(id, name, timeFrom, timeTo, day);
    }
}
