package com.example.jien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Terms_of_use extends AppCompatActivity {

    private CheckBox checkBox;
    private Button button;
    private MaterialAlertDialogBuilder materialAlertDialogBuilder;

    private static final int REQUEST_CODE_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use_page);

      //  getSupportActionBar().hide();

        //termsCheckbox//check_id
        //nextbutton//button_id

        checkBox = findViewById(R.id.termsCheckbox);
        button = findViewById(R.id.nextbutton);

        materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);

        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Terms_of_use.this, MainActivity.class);
                startActivity(intent);
            }
        });



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    materialAlertDialogBuilder.setTitle("Terms And Conditions");
                    materialAlertDialogBuilder.setMessage("Description");
                    materialAlertDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            button.setEnabled(true);
                            dialogInterface.dismiss();
                            ActivityCompat.requestPermissions(Terms_of_use.this,
                                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_CODE_PERMISSION);

                        }
                    });
                    materialAlertDialogBuilder.setNegativeButton("You must agree; otherwise, you are not welcome in our JIEN community.XD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            checkBox.setChecked(false);

                        }
                    });
                    materialAlertDialogBuilder.show();
                }else {
                    button.setEnabled(false);
                }
            }
        });


    }
}