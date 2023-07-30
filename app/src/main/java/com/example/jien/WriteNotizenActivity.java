package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

public class WriteNotizenActivity extends AppCompatActivity {
    private static final String WriteNote = "note";
    EditText editText ;
    Button saveBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_notizen);
        new TopBarHelper(this);

        editText = findViewById(R.id.ediText);
        saveBtn = findViewById(R.id.saveBtn);
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        String savedNote = sharedPreferences.getString(WriteNote, "");
        editText.setText(savedNote);

        saveBtn.setOnClickListener(view -> {
            String note = editText.getText().toString();
            saveNote(WriteNote, note);
        });
    }

    public void saveNote(String key,String note ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,note);
        editor.apply();

    }

}