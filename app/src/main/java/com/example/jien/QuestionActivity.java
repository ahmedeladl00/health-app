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

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity implements SensorEventListener {

    private TextView qTitleTextView;
    private TextView questionTextView;
    private TextView negativeTxt;
    private TextView positiveTxt;

    private Slider slider0;
    private EditText otherTxt;

    private Spinner socialSpinner;
    private Spinner contextSpinner;

    private FloatingActionButton startButton;
    private FloatingActionButton finishButton;

    private LinearLayout control;
    private ConstraintLayout mainSlider0;
    private ConstraintLayout mainSlider1;
    private ConstraintLayout mainSlider2;
    private LinearLayout yesNoQuestion;

    private List<List<String>> MDBF = new ArrayList<>();
    private List<String> Event_Appraisal = new ArrayList<>();
    private List<String> Social_Context = new ArrayList<>();
    private List<String> Social_Situation = new ArrayList<>();
    private List<String> Context = new ArrayList<>();
    private List<String> Rumination = new ArrayList<>();
    private List<String> Self_Esteem = new ArrayList<>();
    private List<String> Impulsivity = new ArrayList<>();

    private int currentArrayIndex = 0;
    private int currentQuestionIndex = 0;
    private int backQuestion = 0;
    private int yesNoCounter = 0;
    private int response;
    private String selectedItem = "";

    private static SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int totalSteps;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        new TopBarHelper(this);

        dbHelper = new DatabaseHelper(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        response = 0;

        slider0 = findViewById(R.id.slider0);
        Slider slider1 = findViewById(R.id.slider1);
        Slider slider2 = findViewById(R.id.slider2);

        control = findViewById(R.id.control);
        mainSlider0 = findViewById(R.id.mainSlider0);
        mainSlider1 = findViewById(R.id.mainSlider1);
        mainSlider2 = findViewById(R.id.mainSlider2);
        yesNoQuestion = findViewById(R.id.yesNoQuestion);

        qTitleTextView = findViewById(R.id.qTitleTextView);
        questionTextView = findViewById(R.id.questionTextView);
        negativeTxt = findViewById(R.id.negativeTxt0);
        positiveTxt = findViewById(R.id.positiveTxt0);

        otherTxt = findViewById(R.id.otherTxt);

        socialSpinner = findViewById(R.id.socialSpinner);
        contextSpinner = findViewById(R.id.contextSpinner);

        startButton = findViewById(R.id.startButton);
        FloatingActionButton nextButton = findViewById(R.id.nextButton);
        FloatingActionButton backButton = findViewById(R.id.backButton);
        FloatingActionButton yesButton = findViewById(R.id.yesButton);
        FloatingActionButton noButton = findViewById(R.id.noButton);
        finishButton = findViewById(R.id.finishButton);

        if (dbHelper.areTablesEmpty()){
            addInitialQuestionsToDatabase();
        }
        MDBF = dbHelper.getMDBFQuestions();
        Event_Appraisal = dbHelper.getAllQuestions("Event_Appraisal");
        Social_Context = dbHelper.getAllQuestions("Social_Context");
        Social_Situation = stringToArrayList("Partner, Family, Friends, Coworkers, Strangers, Other");
        Context = stringToArrayList("At home, School/University, Work, Sport, other recreational Activity, Shopping, Visiting, Other");
        Rumination = dbHelper.getAllQuestions("Rumination");
        Self_Esteem = dbHelper.getAllQuestions("Self_Esteem");
        Impulsivity = dbHelper.getAllQuestions("Impulsivity");

        ArrayAdapter<String> socialAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Social_Situation);
        ArrayAdapter<String> contextAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Context);

        qTitleTextView.setVisibility(View.VISIBLE);
        qTitleTextView.setText(R.string.are_you_ready_to_start_your_well_being_test);

        startButton.setOnClickListener(v -> {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) qTitleTextView.getLayoutParams();
            params.verticalBias = 0.0f;
            qTitleTextView.setLayoutParams(params);
            qTitleTextView.setText(R.string.mdbf_questions);
            questionTextView.setVisibility(View.VISIBLE);
            questionTextView.setText("In the last 24 hours I felt:");
            control.setVisibility(View.VISIBLE);
            mainSlider0.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.GONE);
            List<String> questionPair = MDBF.get(currentQuestionIndex);
            negativeTxt.setText(questionPair.get(0));
            positiveTxt.setText(questionPair.get(1));
            currentQuestionIndex++;
        });

        nextButton.setOnClickListener(v -> {
            if (currentArrayIndex == 0){
                List<String> questionPair = MDBF.get(currentQuestionIndex);
                negativeTxt.setText(questionPair.get(0));
                positiveTxt.setText(questionPair.get(1));
                response = (int) slider0.getValue();
                saveResponseToDatabase("MDBF",currentQuestionIndex,response);
                currentQuestionIndex++;

                if (currentQuestionIndex > MDBF.size()-1){
                    backQuestion = currentQuestionIndex;
                    currentQuestionIndex = 0;
                    currentArrayIndex++;
                }
            } else if (currentArrayIndex == 1) {
                if (currentQuestionIndex == 0){
                    response = (int) slider0.getValue();
                    saveResponseToDatabase("MDBF",MDBF.size(),response);
                }
                qTitleTextView.setText(R.string.event_appraisal_questions);
                yesNoQuestion.setVisibility(View.VISIBLE);
                mainSlider0.setVisibility(View.GONE);
                questionTextView.setText(Event_Appraisal.get(currentQuestionIndex));

                if (currentQuestionIndex >= 1){
                    yesNoQuestion.setVisibility(View.GONE);
                    negativeTxt.setText("0 (not intense at all)");
                    positiveTxt.setText("100 (very intense)");
                    mainSlider0.setVisibility(View.VISIBLE);
                    if (currentQuestionIndex > 1){
                        response = (int) slider0.getValue();
                        saveResponseToDatabase("Event_Appraisal",currentQuestionIndex,response);
                    }
                }
                currentQuestionIndex++;

                if (currentQuestionIndex > Event_Appraisal.size()-1){
                    backQuestion = currentQuestionIndex;
                    currentQuestionIndex = 0;
                    currentArrayIndex++;
                }
            } else if (currentArrayIndex == 2) {
                if (currentQuestionIndex == 0){
                    response = (int) slider0.getValue();
                    saveResponseToDatabase("Event_Appraisal",Event_Appraisal.size(),response);
                }
                qTitleTextView.setText(R.string.social_context_questions);
                yesNoQuestion.setVisibility(View.VISIBLE);
                mainSlider0.setVisibility(View.GONE);
                questionTextView.setText(Social_Context.get(currentQuestionIndex));

                if (currentQuestionIndex >= 1){
                    yesNoQuestion.setVisibility(View.GONE);
                    negativeTxt.setText("0 (do not agree)");
                    positiveTxt.setText("(fully agree) 100");
                    mainSlider0.setVisibility(View.VISIBLE);
                }

                currentQuestionIndex++;
                if (currentQuestionIndex > Social_Context.size()-1){
                    backQuestion = currentQuestionIndex;
                    currentQuestionIndex = 0;
                    currentArrayIndex++;
                }
            } else if (currentArrayIndex == 3) {
                if (currentQuestionIndex == 0){
                    response = (int) slider0.getValue();
                    saveResponseToDatabase("Social_Context",currentQuestionIndex+2,response);
                }
                qTitleTextView.setText(R.string.social_situation_question);
                questionTextView.setText(dbHelper.getAllQuestions("Social_Situation").get(0));
                yesNoQuestion.setVisibility(View.GONE);
                mainSlider0.setVisibility(View.GONE);
                socialSpinner.setAdapter(socialAdapter);
                socialSpinner.setVisibility(View.VISIBLE);
                currentArrayIndex++;
            } else if (currentArrayIndex == 4) {
                dbHelper.saveResponse("Social_Situation",1,selectedItem);
                otherTxt.setVisibility(View.GONE);
                qTitleTextView.setText(R.string.context_question);
                questionTextView.setText(dbHelper.getAllQuestions("Context").get(0));
                yesNoQuestion.setVisibility(View.GONE);
                mainSlider0.setVisibility(View.GONE);
                socialSpinner.setVisibility(View.GONE);
                contextSpinner.setAdapter(contextAdapter);
                contextSpinner.setVisibility(View.VISIBLE);
                currentArrayIndex++;
            } else if (currentArrayIndex == 5) {
                if (currentQuestionIndex == 0){
                    dbHelper.saveResponse("Context",1,selectedItem);
                    otherTxt.setVisibility(View.GONE);
                }
                qTitleTextView.setText(R.string.rumination_question);
                questionTextView.setText(Rumination.get(currentQuestionIndex));
                contextSpinner.setVisibility(View.GONE);
                otherTxt.setVisibility(View.GONE);
                mainSlider0.setVisibility(View.VISIBLE);
                negativeTxt.setText("0 (do not agree)");
                positiveTxt.setText("(fully agree) 100");
                response = (int) slider0.getValue();
                saveResponseToDatabase("Rumination",currentQuestionIndex,response);
                currentQuestionIndex++;
                if (currentQuestionIndex > Rumination.size()-1){
                    backQuestion = currentQuestionIndex;
                    currentQuestionIndex = 0;
                    currentArrayIndex++;
                }
            } else if (currentArrayIndex == 6) {
                if (currentQuestionIndex == 0){
                    response = (int) slider0.getValue();
                    saveResponseToDatabase("Rumination",Rumination.size(),response);
                }
                qTitleTextView.setText(R.string.self_esteem_question);
                questionTextView.setText(Self_Esteem.get(currentQuestionIndex));
                mainSlider0.setVisibility(View.GONE);
                mainSlider1.setVisibility(View.VISIBLE);
                currentQuestionIndex++;
                if (currentQuestionIndex > Self_Esteem.size()-1){
                    backQuestion = currentQuestionIndex;
                    currentQuestionIndex = 0;
                    currentArrayIndex++;
                }
            } else if (currentArrayIndex == 7) {
                qTitleTextView.setText(R.string.impulsivity_question);
                questionTextView.setText(Impulsivity.get(currentQuestionIndex));
                mainSlider1.setVisibility(View.GONE);
                mainSlider2.setVisibility(View.VISIBLE);
                currentQuestionIndex++;
                if (currentQuestionIndex > Impulsivity.size()-1){
                    backQuestion = currentQuestionIndex;
                    currentQuestionIndex = 0;
                    currentArrayIndex++;
                }
            } else {
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) qTitleTextView.getLayoutParams();
                params.verticalBias = 0.5f;
                mainSlider2.setVisibility(View.GONE);
                control.setVisibility(View.GONE);
                qTitleTextView.setText(R.string.your_mood_is);
                int mood = dbHelper.moodCalculator();
                questionTextView.setText(mood + "%");
                finishButton.setVisibility(View.VISIBLE);
                if (mood < 50){
                    Random random = new Random();
                    boolean showExerciseIntervention = random.nextBoolean();
                    if (showExerciseIntervention) {
                        new MaterialAlertDialogBuilder(QuestionActivity.this)
                                .setTitle((R.string.intervention_suggestion))
                                .setMessage((R.string.as_your_mood_is_below_50_we_suggest_that_you_do_some_intervention_a_random_one_is_selected_to_you))
                                .setPositiveButton("GO", (dialog, which) -> {
                                    Intent intent = new Intent(QuestionActivity.this, InterventionActivity.class);
                                    intent.putExtra("videoName", "random");
                                    startActivity(intent);
                                })
                                .setNegativeButton((R.string.cancel), (dialog, which) -> {

                                })
                                .show();
                    }
                }
            }
            slider0.setValue(0.0f);
        });

        yesButton.setOnClickListener(v -> {
            if (yesNoCounter == 0){
                saveResponseToDatabase("Event_Appraisal",1, 100);
            } else {
                saveResponseToDatabase("Social_Context",1, 100);
                currentArrayIndex += 2;
            }
            yesNoCounter++;
            nextButton.performClick();
        });

        noButton.setOnClickListener(v -> {
            if (yesNoCounter == 0){
                saveResponseToDatabase("Event_Appraisal",1, 0);
                currentQuestionIndex = 0;
                currentArrayIndex++;
            } else {
                saveResponseToDatabase("Social_Context",1, 0);
            }
            yesNoCounter++;
            nextButton.performClick();
        });

        socialSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == Social_Situation.size()-1){
                    otherTxt.setVisibility(View.VISIBLE);
                } else {
                    selectedItem = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // This method is called when nothing is selected.
            }
        });

        contextSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == Context.size()-1){
                    otherTxt.setVisibility(View.VISIBLE);
                } else {
                    selectedItem = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // This method is called when nothing is selected.
            }
        });

        otherTxt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                selectedItem = otherTxt.getText().toString();
                hideKeyboard(otherTxt);
                return true;
            }
            return false;
        });

        slider1.addOnChangeListener((slider, value, fromUser) -> {
            response = (int) value;
            saveResponseToDatabase("Self_Esteem",((currentQuestionIndex+1)%2)+1,response);
        });

        slider2.addOnChangeListener((slider, value, fromUser) -> {
            response = (int) value;
            saveResponseToDatabase("Impulsivity",((currentQuestionIndex+1)%2)+1,response);
        });

        finishButton.setOnClickListener(v -> {
            dbHelper.insertStepCounts(totalSteps,getCurrentTimestamp());
            if (ContextCompat.checkSelfPermission(QuestionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(QuestionActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            } else {
                User user = User.getInstance();
                user.loadUserData(QuestionActivity.this);
                String userName = user.getName();
                new DBSender(QuestionActivity.this,"Questionnaire.db",userName).uploadFile();
            }
            boolean isNoon = getIntent().getBooleanExtra("isNoon", false);
            Intent intent;
            if (isNoon) {
                intent = new Intent(QuestionActivity.this, DigitalSpanActivity.class);
            } else {
                intent = new Intent(QuestionActivity.this, DashboardActivity.class);
            }
            startActivity(intent);
        });

        backButton.setOnClickListener(v->{
            if (currentQuestionIndex == 0 || currentQuestionIndex == 1){
                if (currentArrayIndex == 0){
                    return;
                }
                currentArrayIndex--;
                currentQuestionIndex = backQuestion-1;
            } else {
                currentQuestionIndex -= 2;
            }
            nextButton.performClick();
        });

    }


    private void addInitialQuestionsToDatabase(){
        dbHelper.insertMDBFQuestion("Unsatisfied","Satisfied");
        dbHelper.insertMDBFQuestion("Restless","Calm");
        dbHelper.insertMDBFQuestion("Unwell","Well");
        dbHelper.insertMDBFQuestion("Tense","Relaxed");
        dbHelper.insertMDBFQuestion("Without Energy","Full of Energy");
        dbHelper.insertMDBFQuestion("Tired","Awake");

        dbHelper.insertQuestion("Event_Appraisal","Since the last questionnaire, did you have one or more negative experiences?");
        dbHelper.insertQuestion("Event_Appraisal","How intense was the most negative event?");
        dbHelper.insertQuestion("Event_Appraisal","How intense was the most positive event?");

        dbHelper.insertQuestion("Social_Context","Are you alone at the moment?");
        dbHelper.insertQuestion("Social_Context","When you think of the people you are around you right now, how does the following statement apply? “I do not like these people”");

        dbHelper.insertQuestion("Social_Situation","Who is around you right now?");

        dbHelper.insertQuestion("Context","Where are you right now?");

        dbHelper.insertQuestion("Rumination","My attention is often focused on characteristics of myself that I don’t want to think about.");
        dbHelper.insertQuestion("Rumination","I often have the feeling that things resurface in my head that I have said or done in the past.");
        dbHelper.insertQuestion("Rumination","Sometimes I have a hard time to stop thinking about myself.");
        dbHelper.insertQuestion("Rumination","For a long time after I got into a fight or a disagreement is settled, my thoughts about these events resurface.");

        dbHelper.insertQuestion("Self_Esteem","Currently I’m satisfied with myself.");
        dbHelper.insertQuestion("Self_Esteem","Currently I think about myself that I am a failure.");

        dbHelper.insertQuestion("Impulsivity","Since the last questionnaire, I reacted impulsive – meaning I acted without thinking about potential consequences.");
        dbHelper.insertQuestion("Impulsivity","Since the last questionnaire, I have been angry or aggressive to another person, which I regretted later on.");
    }

    private ArrayList stringToArrayList(String str){
        String[] arr = str.split(", ");
        return new ArrayList<>(Arrays.asList(arr));
    }

    private void saveResponseToDatabase(String TableName,int questionId, int response) {
        dbHelper.saveResponse(TableName,questionId, response);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            totalSteps = (int) event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}