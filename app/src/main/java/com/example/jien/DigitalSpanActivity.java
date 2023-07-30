package com.example.jien;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DigitalSpanActivity extends AppCompatActivity {

    private static final int MAX_LEVEL = 10;
    private static final int SHOW_DELAY = 300;
    private static final int USER_INPUT_DELAY = 2000;

    private TextView textViewDigits;
    private List<Integer> sequence;
    private int currentLevel;
    private boolean userPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_span);
        new TopBarHelper(this);

        startGame();
        for (int i = 0; i < 10; i++) {
            int resId = getResources().getIdentifier("digi" + i, "id", getPackageName());
            FloatingActionButton button = findViewById(resId);
            int finalI = i;
            button.setOnClickListener(v -> {
                if (userPlaying) {
                    handleUserInput(finalI);
                }
            });
        }
    }

    private void startGame() {
        sequence = new ArrayList<>();
        currentLevel = 1;
        userPlaying = false;
        textViewDigits = findViewById(R.id.textViewDigits);
        textViewDigits.setText("Level: " + currentLevel);
        generateNextNumber();
    }

    private void generateNextNumber() {
        textViewDigits.setText("Memorize...");
        sequence.clear();

        // Generate a random number sequence
        Random random = new Random();
        for (int i = 0; i < currentLevel; i++) {
            int randomNumber = random.nextInt(10);
            sequence.add(randomNumber);
        }

        Handler handler = new Handler();
        handler.postDelayed(this::showNumberSequence, SHOW_DELAY);
    }

    private void showNumberSequence() {
        StringBuilder sb = new StringBuilder();

        for (Integer number : sequence) {
            sb.append(number);
            sb.append(" ");
        }

        textViewDigits.setText(sb.toString());

        Handler handler = new Handler();
        handler.postDelayed(this::waitForUserInput, USER_INPUT_DELAY);
    }

    private void waitForUserInput() {
        userPlaying = true;
        textViewDigits.setText("Your turn");
    }

    private void handleUserInput(int input) {
        int expectedNumber = sequence.remove(0);

        if (input == expectedNumber) {
            if (sequence.isEmpty()) {
                currentLevel++;

                if (currentLevel > MAX_LEVEL) {
                    textViewDigits.setText("You win!");
                } else {
                    textViewDigits.setText("Correct! Level: " + currentLevel);
                    userPlaying = false;

                    // Generate the next number sequence after a delay
                    Handler handler = new Handler();
                    handler.postDelayed(this::generateNextNumber, SHOW_DELAY);
                }
            }
        } else {
            textViewDigits.setText("Wrong! Your level: " + currentLevel);
            userPlaying = false;
            Handler handler = new Handler();
            handler.postDelayed(this::finish, USER_INPUT_DELAY);
        }
    }
}