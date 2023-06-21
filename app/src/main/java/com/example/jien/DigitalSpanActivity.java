package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DigitalSpanActivity extends AppCompatActivity {

    private static final int MAX_LEVEL = 10;
    private static final int SHOW_DELAY = 500;
    private static final int USER_INPUT_DELAY = 5000;

    private TextView textViewDigits;
    private List<Integer> sequence;
    private int currentLevel;
    private boolean userPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_span);
        startGame();
        for (int i = 0; i < 10; i++) {
            int resId = getResources().getIdentifier("digi" + i, "id", getPackageName());
            FloatingActionButton button = findViewById(resId);
            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userPlaying) {
                        handleUserInput(finalI);
                    }
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showNumberSequence();
            }
        }, SHOW_DELAY);
    }

    private void showNumberSequence() {
        StringBuilder sb = new StringBuilder();

        for (Integer number : sequence) {
            sb.append(number);
            sb.append(" ");
        }

        textViewDigits.setText(sb.toString());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                waitForUserInput();
            }
        }, USER_INPUT_DELAY);
    }

    private void waitForUserInput() {
        userPlaying = true;
        textViewDigits.setText("Your turn");
    }

    private void handleUserInput(int input) {
        int expectedNumber = sequence.remove(0); // Remove the first number from the sequence

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
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            generateNextNumber();
                        }
                    }, SHOW_DELAY);
                }
            }
        } else {
            textViewDigits.setText("Wrong! Your level: " + currentLevel);
            userPlaying = false;
        }
    }
}