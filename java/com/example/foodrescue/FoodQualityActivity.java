package com.example.foodrescue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodQualityActivity extends AppCompatActivity {

    Spinner spinnerCondition;
    EditText etPreparedTime, etExpiryTime, etNotes;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_quality);

        spinnerCondition = findViewById(R.id.spinnerCondition);
        etPreparedTime = findViewById(R.id.etPreparedTime);
        etExpiryTime = findViewById(R.id.etExpiryTime);
        etNotes = findViewById(R.id.etNotes);
        btnVerify = findViewById(R.id.btnVerify);

        String[] conditions = {
                "Select Food Condition",
                "Fresh",
                "Good",
                "Not Good"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                conditions
        );

        spinnerCondition.setAdapter(adapter);

        btnVerify.setOnClickListener(v -> {

            String condition = spinnerCondition.getSelectedItem().toString();
            String preparedTime = etPreparedTime.getText().toString().trim();
            String expiryTime = etExpiryTime.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();

            if (condition.equals("Select Food Condition")) {
                Toast.makeText(
                        FoodQualityActivity.this,
                        "Please select food condition",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (preparedTime.isEmpty()) {
                etPreparedTime.setError("Enter preparation time");
                return;
            }

            if (expiryTime.isEmpty()) {
                etExpiryTime.setError("Enter expiry/use-by time");
                return;
            }

            if (condition.equals("Not Good")) {

                Toast.makeText(
                        FoodQualityActivity.this,
                        "Food is not suitable for donation ❌",
                        Toast.LENGTH_LONG
                ).show();

            } else {

                Toast.makeText(
                        FoodQualityActivity.this,
                        "Food quality verified successfully! ✅",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}