package com.example.jien;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PhysicalsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="physicals.db";
    private static final int DATABASE_VERSION= 3;

    private static final String CREATE_PHYSICAL_DATA = "CREATE TABLE Physical_Data ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "activity TEXT,"
            + "time_from TEXT,"
            + "time_to TEXT,"
            + "day TEXT)";


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

}
