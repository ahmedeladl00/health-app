package com.example.jien;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StepDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sensors.db";
    private static final int DATABASE_VERSION = 3;
    private static final String CREATE_STEP_DATA = "CREATE TABLE Step_Data ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "step_count INTEGER,"
//            + "latitude DOUBLE,"
//            + "longitude DOUBLE,"
            + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public StepDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STEP_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Step_Data");
        onCreate(db);
    }
}
