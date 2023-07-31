/*
 * MIT License
 *
 * Copyright (c) 2023 RUB-SE-LAB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    List<IABase> fetchDataFromDatabase(SQLiteOpenHelper dop, String tableName, String mainCol) {
        List<IABase> interActItems = new ArrayList<>();

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
                IABase interActItem = new IABase(id, name, timeFrom, timeTo, day);
                interActItems.add(interActItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return interActItems;
    }

    void removeInterventionFromDatabase(SQLiteOpenHelper dop, String tableName, int id) {
        SQLiteDatabase db = dop.getWritableDatabase();
        db.delete(tableName, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
