package com.example.jien;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InterventionRecordActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private Button startBtn;
    private long timerInterval = 1000;
    private long elapsedTime = 0;
    private boolean isTimerRunning = false;
    private Spinner interventionNames;
    private Intervention intervention;
    private LocalDate currentDate;
    private DatabaseHelper dbHelper;
    private static final int REQUEST_CODE_PERMISSION = 1;
    private FloatingActionButton installButton;
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private ActivityResultCallback<ActivityResult> filePickerCallback;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_record);

        timerTextView = findViewById(R.id.timerTextView);
        startBtn = findViewById(R.id.startBtn);
        interventionNames = findViewById(R.id.interventionNames);
        installButton = findViewById(R.id.installBtn);


        List<String> namesList = loadDataFromRaw();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, namesList);
        interventionNames.setAdapter(adapter);
        interventionNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIntervention = parent.getItemAtPosition(position).toString();
                currentDate = LocalDate.now();
                intervention = new Intervention(selectedIntervention,currentDate);
                startBtn.setText("Start Recording");
                startBtn.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {

                long seconds = elapsedTime / 1000;
                timerTextView.setText(String.format(Locale.getDefault(), "Elapsed time: %02d:%02d", seconds / 60, seconds % 60));
                elapsedTime += timerInterval;
                timerHandler.postDelayed(this, timerInterval);

            }
        };

        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (isTimerRunning){
                    stopTimer();
                    isTimerRunning = false;
                } else {
                    startTimer();
                    isTimerRunning = true;
                }

            }
        });


        installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
        filePickerCallback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri fileUri = data.getData();
                        if (fileUri != null) {
                            installIntervention(fileUri);
                        }
                    }
                }
            }
        };
        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), filePickerCallback);


    }

    private void startTimer() {
        elapsedTime = 0;
        timerHandler.postDelayed(timerRunnable, timerInterval);
        isTimerRunning = true;
        startBtn.setText("Stop Recording");
        intervention.setTimeFrom();
    }

    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
        isTimerRunning = false;
        startBtn.setText("Start Recording Again");
        intervention.setTimeTo();
        dbHelper = new DatabaseHelper(this);
        dbHelper.insertIntervention(intervention);
    }
    /**
     *  Loads data from raw resources and files directory.
     *  @return A list of names representing the data loaded from raw resources and files directory.
     */
    private List<String> loadDataFromRaw() {
        List<String> namesList = new ArrayList<>();

        Field[] rawFields = R.raw.class.getFields();
        for (Field field : rawFields) {
            try {
                namesList.add(field.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File filesDirectory = this.getFilesDir();
        File[] files = filesDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                namesList.add(file.getName());
            }
        }

        return namesList;
    }


    private void openFilePicker() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            startFilePicker();
        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }
    }

    private void startFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        filePickerLauncher.launch(intent);
    }

    private void installIntervention(Uri fileUri) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rename Video");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newFileName = input.getText().toString().trim();
                if (TextUtils.isEmpty(newFileName)) {

                    Toast.makeText(getApplicationContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                } else {

                    saveVideoWithNewName(fileUri, newFileName);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void saveVideoWithNewName(Uri fileUri, String newFileName) {
        try {
            InputStream inputStream = this.getContentResolver().openInputStream(fileUri);
            File outputFile = new File(this.getFilesDir(), newFileName);
            System.out.println(this.getFilesDir());
            OutputStream outputStream = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Toast.makeText(this, "Intervention '" + newFileName + "' installed", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving the file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startFilePicker();
        } else {
            Toast.makeText(this, "Berechtigung zur Dateispeicherung erforderlich", Toast.LENGTH_SHORT).show();
        }
    }




}