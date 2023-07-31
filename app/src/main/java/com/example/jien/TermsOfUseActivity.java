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
            Intent intent = new Intent(TermsOfUseActivity.this, DashboardActivity.class);
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