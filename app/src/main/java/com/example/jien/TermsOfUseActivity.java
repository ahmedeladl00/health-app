package com.example.jien;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class TermsOfUseActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private Button button;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    private static final int REQUEST_CODE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        checkBox = findViewById(R.id.termsCheckbox);
        button = findViewById(R.id.nextbutton);

        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);

        button.setEnabled(false);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(TermsOfUseActivity.this, MainActivity.class);
            startActivity(intent);
        });

        checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                materialAlertDialogBuilder.setTitle("Terms And Conditions");
                materialAlertDialogBuilder.setMessage("Description");
                materialAlertDialogBuilder.setPositiveButton("Accept", (dialogInterface, i) -> {
                    button.setEnabled(true);
                    dialogInterface.dismiss();
                    ActivityCompat.requestPermissions(TermsOfUseActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_PERMISSION);
                });
                materialAlertDialogBuilder.setNegativeButton("You must agree; otherwise, you are not welcome in our JIEN community.XD", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    checkBox.setChecked(false);
                });
                materialAlertDialogBuilder.show();
            }else {
                button.setEnabled(false);
            }
        });

    }
}