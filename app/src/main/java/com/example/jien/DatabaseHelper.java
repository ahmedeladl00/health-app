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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Questionnaire.db";
    private static final int DATABASE_VERSION = 33;

    private static final String CREATE_TABLE_MDBF = "CREATE TABLE MDBF ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "negative_question TEXT,"
            + "positive_question TEXT,"
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
            + "response TEXT DEFAULT NULL)";
    public static final String CREATE_TABLE_CONTEXT = "CREATE TABLE Context ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "question_text TEXT,"
            + "response TEXT DEFAULT NULL)";
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

    public static final String CREATE_TABLE_INTERVENTION = "CREATE TABLE Intervention ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "intervention TEXT,"
            + "time_from TEXT,"
            + "time_to TEXT,"
            + "day TEXT)";

    private static final String CREATE_STEP_DATA = "CREATE TABLE Step_Data ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "step_count INTEGER,"
            + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

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
        db.execSQL(CREATE_TABLE_INTERVENTION);
        db.execSQL(CREATE_STEP_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MDBF");
        db.execSQL("DROP TABLE IF EXISTS Event_Appraisal");
        db.execSQL("DROP TABLE IF EXISTS Social_Context");
        db.execSQL("DROP TABLE IF EXISTS Social_Situation");
        db.execSQL("DROP TABLE IF EXISTS Context");
        db.execSQL("DROP TABLE IF EXISTS Rumination");
        db.execSQL("DROP TABLE IF EXISTS Self_Esteem");
        db.execSQL("DROP TABLE IF EXISTS Impulsivity");
        db.execSQL("DROP TABLE IF EXISTS Intervention");
        db.execSQL("DROP TABLE IF EXISTS Step_Data");
        onCreate(db);
    }

    public void insertQuestion(String TableName,String questionText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("question_text", questionText);
        db.insert(TableName, null, values);
        db.close();
    }

    public void insertMDBFQuestion(String negative, String positive){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("negative_question", negative);
        values.put("positive_question", positive);
        db.insert("MDBF",null, values);
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

    public List<List<String>> getMDBFQuestions() {
        List<List<String>> questionsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"negative_question", "positive_question"};

        Cursor cursor = db.query("MDBF", columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int negative = cursor.getColumnIndex("negative_question");
            int positive = cursor.getColumnIndex("positive_question");
            do {
                String negativeQuestion = cursor.getString(negative);
                String positiveQuestion = cursor.getString(positive);

                List<String> pair = new ArrayList<>();
                pair.add(negativeQuestion);
                pair.add(positiveQuestion);
                questionsList.add(pair);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return questionsList;
    }

    public void saveResponse(String TableName,int questionId, int response) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("response", response);
        db.update(TableName, values, "id = ?", new String[]{String.valueOf(questionId)});
        db.close();
    }

    public void saveResponse(String TableName,int questionId, String response) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("response", response);
        db.update(TableName, values, "id = ?", new String[]{String.valueOf(questionId)});
        db.close();
    }

    public void insertIntervention(Intervention intervention) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("intervention", intervention.getName());
        values.put("time_from", intervention.getTimeFrom());
        values.put("time_to", intervention.getTimeTo());
        values.put("day", intervention.getDay().toString());

        db.insert("Intervention", null, values);

        db.close();
    }
    public boolean areTablesEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM MDBF", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count == 0;
    }

    public int avgCalculator(String tableName, int sliderMax) {
        SQLiteDatabase db = this.getReadableDatabase();
        String columnName = "response";
        double avg = 0;

        Cursor cursor = db.rawQuery("SELECT AVG(" + columnName + ") / " + sliderMax + " * 100 FROM " + tableName, null);

        if (cursor != null && cursor.moveToFirst()) {
            avg = cursor.getDouble(0);
            cursor.close();
        }

        db.close();
        return (int) avg;
    }

    public int moodCalculator(){
        return (avgCalculator("MDBF",100) +
                avgCalculator("Self_Esteem",9) +
                avgCalculator("Impulsivity",6)) / 3;
    }
    public void insertStepCounts(int stepCount, String timestamp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("step_count", stepCount);
        values.put("timestamp", timestamp);
        db.insert("Step_Data",null, values);
        db.close();
    }


}
