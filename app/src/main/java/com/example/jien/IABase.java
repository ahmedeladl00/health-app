package com.example.jien;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class IABase {
    private int id;
    private String name;
    private String timeFrom;
    private String timeTo;
    private LocalDate day;

    public IABase() {}
    public IABase(String name, LocalDate day) {
        this.name = name;
        this.day = day;
    }

    public IABase (int id, String name, String timeFrom, String timeTo, LocalDate day){
        this.id = id;
        this.name = name;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setTimeFrom (String timeFrom){
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() { return timeTo; }

    public void setTimeTo() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        int hour = currentDateTime.getHour();
        int minute = currentDateTime.getMinute();
        int second = currentDateTime.getSecond();
        this.timeTo = String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public void setTimeTo( String timeTo){
        this.timeTo = timeTo;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    List<Intervention> fetchDataFromDatabase(SQLiteOpenHelper dop, String tableName, String mainCol) {
        List<Intervention> interventions = new ArrayList<>();

        SQLiteDatabase db = dop.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex(mainCol);
            int timeFromIndex = cursor.getColumnIndex("time_from");
            int timeToIndex = cursor.getColumnIndex("time_to");
            int dayIndex = cursor.getColumnIndex("day");
            do {

                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String timeFrom = cursor.getString(timeFromIndex);
                String timeTo = cursor.getString(timeToIndex);
                String dayString = cursor.getString(dayIndex);
                LocalDate day;
                try {
                    day = LocalDate.parse(dayString);
                } catch (DateTimeParseException e) {
                    day = LocalDate.now();
                    continue;
                }
                Intervention intervention = new Intervention(id, name, timeFrom, timeTo, day);
                System.out.println(intervention.getName());
                interventions.add(intervention);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return interventions;
    }

    void removeInterventionFromDatabase(SQLiteOpenHelper dop, String tableName, int id) {
        SQLiteDatabase db = dop.getWritableDatabase();
        db.delete(tableName, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
