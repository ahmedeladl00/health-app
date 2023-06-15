package com.example.jien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Questionnaire.db";
    private static final int DATABASE_VERSION = 20;

    private static final String CREATE_TABLE_MDBF = "CREATE TABLE MDBF ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";
    public static final String CREATE_TABLE_EVENT_APPRAISAL = "CREATE TABLE Event_Appraisal ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";
    public static final String CREATE_TABLE_SOCIAL_CONTEXT = "CREATE TABLE Social_Context ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";
    public static final String CREATE_TABLE_SOCIAL_SITUATION = "CREATE TABLE Social_Situation ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";
    public static final String CREATE_TABLE_CONTEXT = "CREATE TABLE Context ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";
    public static final String CREATE_TABLE_RUMINATION = "CREATE TABLE Rumination ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";
    public static final String CREATE_TABLE_SELF_ESTEEM = "CREATE TABLE Self_Esteem ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";
    public static final String CREATE_TABLE_IMPULSIVITY = "CREATE TABLE Impulsivity ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response INTEGER DEFAULT 0)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MDBF);
        db.execSQL(CREATE_TABLE_EVENT_APPRAISAL);
        db.execSQL(CREATE_TABLE_SOCIAL_CONTEXT);
        db.execSQL(CREATE_TABLE_SOCIAL_SITUATION);
        db.execSQL(CREATE_TABLE_CONTEXT);
        db.execSQL(CREATE_TABLE_RUMINATION);
        db.execSQL(CREATE_TABLE_SELF_ESTEEM);
        db.execSQL(CREATE_TABLE_IMPULSIVITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if needed and recreate them
        db.execSQL("DROP TABLE IF EXISTS MDBF");
        db.execSQL("DROP TABLE IF EXISTS Event_Appraisal");
        db.execSQL("DROP TABLE IF EXISTS Social_Context");
        db.execSQL("DROP TABLE IF EXISTS Social_Situation");
        db.execSQL("DROP TABLE IF EXISTS Context");
        db.execSQL("DROP TABLE IF EXISTS Rumination");
        db.execSQL("DROP TABLE IF EXISTS Self_Esteem");
        db.execSQL("DROP TABLE IF EXISTS Impulsivity");
        onCreate(db);
    }

    public void insertQuestion(String TableName,String questionText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question_text", questionText);
        db.insert(TableName, null, values);
        db.close();
    }

    public List<String> getAllQuestions(String TableName) {
        List<String> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TableName, new String[]{"question_text"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String questionText = cursor.getString(cursor.getColumnIndexOrThrow("question_text"));
            questions.add(questionText);
        }
        cursor.close();
        db.close();
        return questions;
    }

    public void saveResponse(String TableName,int questionId, int response) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("response", response);
        db.update(TableName, values, "id = ?", new String[]{String.valueOf(questionId)});
        db.close();
    }

}
