package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {
    private TextView questionTextView;
    private SeekBar seekBar;
    private Button yesButton;
    private Button noButton;
    private Button nextButton;
    private Spinner spinner;
    private List<String> MDBF = new ArrayList<>();
    private List<String> Event_Appraisal = new ArrayList<>();
    private List<String> Social_Context = new ArrayList<>();
    private List<String> Social_Situation = new ArrayList<>();
    private List<String> Context = new ArrayList<>();
    private List<String> Rumination = new ArrayList<>();
    private List<String> Self_Esteem = new ArrayList<>();
    private List<String> Impulsivity = new ArrayList<>();
    private Map<String, List<String>> allQuestions = new LinkedHashMap<>();

    private int currentArrayIndex = 0;
    private int currentQuestionIndex = 0;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        dbHelper = new DatabaseHelper(this);

        questionTextView = findViewById(R.id.questionTextView);
        seekBar = findViewById(R.id.seekBar);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        nextButton = findViewById(R.id.nextButton);
        Spinner spinner = findViewById(R.id.spinner);

        currentArrayIndex = 0;
        addInitialQuestionsToDatabase();
        getQuestionsFromDatabase();
        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Social_Situation);
        ArrayAdapter<String> contextAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Context);
        questionTextView.setText("Start The Test");

        showSeekBar();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentArrayIndex < allQuestions.size()) {
                    Map.Entry<String, List<String>> currentEntry = allQuestions.entrySet().stream().skip(currentArrayIndex).findFirst().get();
                    String tableName = currentEntry.getKey();
                    List<String> questions = currentEntry.getValue();
                    if(tableName == "Context"){
                        spinner.setVisibility(View.VISIBLE);
                        spinner.setAdapter(contextAdapter);
                        currentArrayIndex++;
                    }
                    if (currentQuestionIndex < questions.size()) {
                        int response = seekBar.getProgress();
                        saveResponseToDatabase(tableName, currentQuestionIndex + 1, response);
                        questionTextView.setText(questions.get(currentQuestionIndex));
                        currentQuestionIndex++;
                    } else {
                        currentQuestionIndex = 0;

                        currentArrayIndex++;
                        if (currentArrayIndex < allQuestions.size()) {
                            Map.Entry<String, List<String>> nextEntry = allQuestions.entrySet().stream().skip(currentArrayIndex).findFirst().get();
                            String nextTableName = nextEntry.getKey();
                            List<String> nextQuestions = nextEntry.getValue();

                            if (nextTableName == "Social_Situation"){
                                spinner.setVisibility(View.VISIBLE);
                                spinner.setAdapter(socialAdapter);
                                currentArrayIndex++;
                            } else if (!nextQuestions.isEmpty()) {
                                questionTextView.setText(nextQuestions.get(0));
                                currentQuestionIndex++;
                            }
                        }
                    }
                }
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResponseToDatabase("Event_Appraisal",currentQuestionIndex, 100);

                currentQuestionIndex++;

                if (currentQuestionIndex < Event_Appraisal.size()) {
                    questionTextView.setText(Event_Appraisal.get(currentQuestionIndex));
                    showSeekBar();
                } else {
                    // All questions answered
                    // Show final message or navigate to next activity
                }
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResponseToDatabase("Event_Appraisal",currentQuestionIndex, 0);

                if (currentQuestionIndex == 0) {
                    currentQuestionIndex += 2;
                } else {
                    currentQuestionIndex++;
                }

                if (currentQuestionIndex < Event_Appraisal.size()) {
                    questionTextView.setText(Event_Appraisal.get(currentQuestionIndex));
                    showSeekBar();
                } else {
                    // All questions answered
                    // Show final message or navigate to next activity
                }
            }
        });

    }

    private void addInitialQuestionsToDatabase() {
        dbHelper.insertQuestion("MDBF","In the last 24 hours I felt: Satisfied/Unsatisfied");
        dbHelper.insertQuestion("MDBF","In the last 24 hours I felt: Restless/Calm");
        dbHelper.insertQuestion("MDBF","In the last 24 hours I felt: Well/Unwell");
        dbHelper.insertQuestion("MDBF","In the last 24 hours I felt: Relaxed/Tense");
        dbHelper.insertQuestion("MDBF","In the last 24 hours I felt: Full of Energy/Without Energy");
        dbHelper.insertQuestion("MDBF","In the last 24 hours I felt: Tired/Awake");

        dbHelper.insertQuestion("Event_Appraisal","Since the last questionnaire, did you have one or more negative experiences?");
        dbHelper.insertQuestion("Event_Appraisal","How intense was the most negative event?");
        dbHelper.insertQuestion("Event_Appraisal","How intense was the most positive event?");

        dbHelper.insertQuestion("Social_Context","Are you alone at the moment?");
        dbHelper.insertQuestion("Social_Context","When you think of the people you are around you right now, how does the following statement apply? “I do not like these people”");

        dbHelper.insertQuestion("Social_Situation","Partner, Family, Friends, Coworkers, Strangers, Other");

        dbHelper.insertQuestion("Context","At home, School/University, Work, Sport, other recreational Activity, Shopping, Visiting, Other");

        dbHelper.insertQuestion("Rumination","My attention is often focused on characteristics of myself that I don’t want to think about.");
        dbHelper.insertQuestion("Rumination","I often have the feeling that things resurface in my head that I have said or done in the past.");
        dbHelper.insertQuestion("Rumination","Sometimes I have a hard time to stop thinking about myself.");
        dbHelper.insertQuestion("Rumination","For a long time after I got into a fight or a disagreement is settled, my thoughts about these events resurface.");

        dbHelper.insertQuestion("Self_Esteem","Currently I’m satisfied with myself.");
        dbHelper.insertQuestion("Self_Esteem","Currently I think about myself that I am a failure.");

        dbHelper.insertQuestion("Impulsivity","Since the last questionnaire, I reacted impulsive – meaning I acted without thinking about potential consequences.");
        dbHelper.insertQuestion("Impulsivity","Since the last questionnaire, I have been angry or aggressive to another person, which I regretted later on.");
    }

    private void getQuestionsFromDatabase() {
        MDBF = dbHelper.getAllQuestions("MDBF");
        allQuestions.put("MDBF",MDBF);
        Event_Appraisal = dbHelper.getAllQuestions("Event_Appraisal");
        allQuestions.put("Event_Appraisal",Event_Appraisal);
        Social_Context = dbHelper.getAllQuestions("Social_Context");
        allQuestions.put("Social_Context",Social_Context);
        Social_Situation = stringToArrayList(dbHelper.getAllQuestions("Social_Situation").get(0));
        allQuestions.put("Social_Situation",Social_Situation);
        Context = stringToArrayList(dbHelper.getAllQuestions("Context").get(0));
        allQuestions.put("Context",Context);
        Rumination = dbHelper.getAllQuestions("Rumination");
        allQuestions.put("Rumination",Rumination);
        Self_Esteem = dbHelper.getAllQuestions("Self_Esteem");
        allQuestions.put("Self_Esteem",Self_Esteem);
        Impulsivity = dbHelper.getAllQuestions("Impulsivity");
        allQuestions.put("Impulsivity",Impulsivity);
    }

    private void saveResponseToDatabase(String TableName,int questionId, int response) {
        dbHelper.saveResponse(TableName,questionId, response);
    }

    private void showSeekBar() {
        seekBar.setVisibility(View.VISIBLE);
        yesButton.setVisibility(View.GONE);
        noButton.setVisibility(View.GONE);
    }

    private void showYesNoButtons() {
        seekBar.setVisibility(View.GONE);
        yesButton.setVisibility(View.VISIBLE);
        noButton.setVisibility(View.VISIBLE);
    }

    private ArrayList stringToArrayList(String str){
        String[] arr = str.split(", ");
        return new ArrayList<>(Arrays.asList(arr));
    }


}
