package com.example.jien;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PhysicalsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="physicals.db";
    private static final int DATABASE_VERSION= 4;

    private static final String CREATE_PHYSICAL_DATA = "CREATE TABLE Physical_Data ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "activity TEXT,"
            + "time_from TEXT,"
            + "time_to TEXT,"
            + "day TEXT)";

    private static final String CREATE_ACCELEROMETER = "CREATE TABLE Accelerometer ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "activity TEXT,"
            + "x REAL,"
            + "y REAL,"
            + "z REAL,"
            + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public PhysicalsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PHYSICAL_DATA);
        db.execSQL(CREATE_ACCELEROMETER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Physical_Data");
        db.execSQL("DROP TABLE IF EXISTS Accelerometer");
        onCreate(db);
    }
    public void insertActivity(PhysicalActivities physicalActivities) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("activity", physicalActivities.getName());
        values.put("time_from", physicalActivities.getTimeFrom());
        values.put("time_to", physicalActivities.getTimeTo());
        values.put("day", physicalActivities.getDay().toString());
        db.insert("Physical_Data", null, values);
        db.close();
    }

    public void insertAccelerometer (Accelerometer<PhysicalActivities> accelerometer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("activity",accelerometer.getInterActName());
        values.put("x",accelerometer.getX());
        values.put("y",accelerometer.getY());
        values.put("z",accelerometer.getZ());
        values.put("timestamp",accelerometer.getTimeStamp());

        db.insert("Accelerometer", null, values);

        db.close();
    }

}
