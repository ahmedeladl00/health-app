package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView qTitleTextView;
    private TextView questionTextView;
    private Slider slider;
    private Button startButton;
    private Button yesButton;
    private Button noButton;
    private Button nextButton;
    private Spinner spinner;
    private LinearLayout control;
    private LinearLayout yesNoQuestion;
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
    private int yesNoCounter = 0;

    private String tableName;
    private String nextTableName;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        dbHelper = new DatabaseHelper(this);

        qTitleTextView = findViewById(R.id.qTitleTextView);
        questionTextView = findViewById(R.id.questionTextView);
        startButton = findViewById(R.id.startButton);
        slider = findViewById(R.id.slider);
        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        nextButton = findViewById(R.id.nextButton);
        spinner = findViewById(R.id.spinner);
        control = findViewById(R.id.control);
        yesNoQuestion = findViewById(R.id.yesNoQuestion);

        currentArrayIndex = 0;
//        addInitialQuestionsToDatabase();
        getQuestionsFromDatabase();
        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Social_Situation);
        ArrayAdapter<String> contextAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Context);
        qTitleTextView.setText("Are you ready to start your well-being test ?");
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) qTitleTextView.getLayoutParams();
                params.verticalBias = 0.0f;
                qTitleTextView.setLayoutParams(params);
                qTitleTextView.setText("MDBF Questions :");
                questionTextView.setVisibility(View.VISIBLE);
                control.setVisibility(View.VISIBLE);
                slider.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.GONE);
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentArrayIndex < allQuestions.size()) {
                    Map.Entry<String, List<String>> currentEntry = allQuestions.entrySet().stream().skip(currentArrayIndex).findFirst().get();
                    tableName = currentEntry.getKey();
                    List<String> questions = currentEntry.getValue();

                    if (tableName == "Event_Appraisal") {
                        showSlider();
                    }

                    if (tableName == "Social_Context") {
                        qTitleTextView.setText("Social Context Questions :");
                    }

                    if (tableName == "Social_Context" && currentQuestionIndex != 0){
                        showSlider();
                    }

                    if (tableName == "Context"){
                        qTitleTextView.setText("Context Question:");
                        yesNoQuestion.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                        spinner.setAdapter(contextAdapter);
                        currentQuestionIndex = 0;
                        currentArrayIndex++;
                    }

                    if (tableName == "Rumination"){
                        qTitleTextView.setText("Rumination Question:");
                        spinner.setVisibility(View.GONE);
                        slider.setVisibility(View.VISIBLE);
                    }


//                    if(tableName == "Social_Situation") {
//                        qTitleTextView.setText("Here");
//                    }
//                    if(tableName == "Social_Situation"){
//                        qTitleTextView.setText("Social Situation Questions :");
//                        spinner.setVisibility(View.VISIBLE);
//                        yesNoQuestion.setVisibility(View.GONE);
//                        spinner.setAdapter(socialAdapter);
//                        currentArrayIndex++;
//                    }
//
//                    if(tableName == "Context"){
//                        spinner.setVisibility(View.VISIBLE);
//                        spinner.setAdapter(contextAdapter);
//                        currentArrayIndex++;
//                    }
                    if (currentQuestionIndex < questions.size()) {
                        int response = (int) slider.getValue();
                        saveResponseToDatabase(tableName, currentQuestionIndex + 1, response);
                        questionTextView.setText(questions.get(currentQuestionIndex));
                        currentQuestionIndex++;
                        if (tableName == "Context"){
                            questionTextView.setText("Where are you right now?");
                            currentQuestionIndex = 0;
                        }

                    } else {
                        currentQuestionIndex = 0;

                        currentArrayIndex++;
                        if (currentArrayIndex < allQuestions.size()) {
                            Map.Entry<String, List<String>> nextEntry = allQuestions.entrySet().stream().skip(currentArrayIndex).findFirst().get();
                            nextTableName = nextEntry.getKey();
                            List<String> nextQuestions = nextEntry.getValue();
                            if (nextTableName == "Event_Appraisal"){
                                qTitleTextView.setText("Event Appraisal Questions :");
                                showYesNoButtons();
//                                control.setVisibility(View.GONE);
                            }

                            if (nextTableName == "Social_Context") {
                                qTitleTextView.setText("Social Context Questions :");
                                showYesNoButtons();
                            }

                            if (nextTableName == "Self_Esteem") {
                                qTitleTextView.setText("Self-Esteem Question:");
                                slider.setVisibility(View.VISIBLE);
                                slider.setValueTo(9);
                            }

                            if (nextTableName == "Impulsivity"){
                                qTitleTextView.setText("Impulsivity Question:");
                                slider.setValueTo(6);
                            }

                            if(nextTableName == "Social_Situation"){
                                qTitleTextView.setText("Social Situation Question:");
                                questionTextView.setText("Who is around you right now?");
                                slider.setVisibility(View.GONE);
                                spinner.setVisibility(View.VISIBLE);
                                spinner.setAdapter(socialAdapter);
                                currentArrayIndex++;
//                                showYesNoButtons();
//                                control.setVisibility(View.GONE);
//                                currentArrayIndex++;
                            } else if (!nextQuestions.isEmpty()) {
                                questionTextView.setText(nextQuestions.get(0));
                                currentQuestionIndex++;
                            }
                        }
                    }
                }else {
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) qTitleTextView.getLayoutParams();
                    params.verticalBias = 0.5f;
                    qTitleTextView.setText("Your Mood is:");
                    slider.setVisibility(View.GONE);
                    //TODO : calculate the mood form the database answers and then git it here
                    questionTextView.setText("65%");
                    nextButton.setText("Finish");
                }
            }
        });

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesNoCounter == 0){
                    saveResponseToDatabase("Event_Appraisal",currentQuestionIndex, 100);
                } else {
                    saveResponseToDatabase("Social_Context",currentQuestionIndex, 100);
                    currentArrayIndex += 2;
                    tableName = "Context";
                }
                yesNoCounter++;
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesNoCounter == 0){
                    saveResponseToDatabase("Event_Appraisal",currentQuestionIndex, 0);
                    saveResponseToDatabase("Event_Appraisal",currentQuestionIndex+1, 0);
                    saveResponseToDatabase("Event_Appraisal",currentQuestionIndex+2, 0);
                    currentQuestionIndex = 0;
                    currentArrayIndex++;
                    tableName = "Social_Context";
                } else {
                    saveResponseToDatabase("Social_Context",currentQuestionIndex, 0);
                }
                yesNoCounter++;
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

    private void showSlider() {
        slider.setVisibility(View.VISIBLE);
        yesNoQuestion.setVisibility(View.GONE);
    }

    private void showYesNoButtons() {
        yesNoQuestion.setVisibility(View.VISIBLE);
        slider.setVisibility(View.GONE);
    }

    private ArrayList stringToArrayList(String str){
        String[] arr = str.split(", ");
        return new ArrayList<>(Arrays.asList(arr));
    }


}
