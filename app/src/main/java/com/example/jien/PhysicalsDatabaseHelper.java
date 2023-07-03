package com.example.jien;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;


public class PhysicalsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="physicals.db";
    private static final int DATABASE_VERSION= 1;
    private static final String CREATE_PHYSICAL_DATA = "CREATE TABLE Physical_Data ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "day DATE,"
            + "timeFrom LONG,"
            + "timeTo LONG,"
            + "activity VARCHAR(25) )";


    public PhysicalsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PHYSICAL_DATA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Physical_Data");
        onCreate(sqLiteDatabase);
    }
    public void insertActivity(String activityName, long timeFrom, long timeTo, Date day) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("activity", activityName);
        values.put("timeFrom", timeFrom);
        values.put("timeTo", timeTo);
        values.put("day", day.getTime());
        db.insert("Physical_Data", null, values);
        db.close();
    }

}
