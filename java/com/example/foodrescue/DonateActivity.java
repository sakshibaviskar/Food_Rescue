package com.example.foodrescue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DonateActivity extends AppCompatActivity {

    EditText etFoodName, etQuantity, etLocation, etDescription;
    Button btnSubmitDonation;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        db = FirebaseFirestore.getInstance();

        etFoodName = findViewById(R.id.etFoodName);
        etQuantity = findViewById(R.id.etQuantity);
        etLocation = findViewById(R.id.etLocation);
        etDescription = findViewById(R.id.etDescription);

        btnSubmitDonation = findViewById(R.id.btnSubmitDonation);

        btnSubmitDonation.setOnClickListener(v -> {

            String foodName = etFoodName.getText().toString().trim();
            String quantity = etQuantity.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (foodName.isEmpty()) {
                etFoodName.setError("Enter food name");
                return;
            }

            if (quantity.isEmpty()) {
                etQuantity.setError("Enter quantity");
                return;
            }

            if (location.isEmpty()) {
                etLocation.setError("Enter location");
                return;
            }

            // Save donation
            Map<String, Object> donation = new HashMap<>();

            donation.put("foodName", foodName);
            donation.put("quantity", quantity);
            donation.put("location", location);
            donation.put("description", description);
            donation.put("status", "Available");

            db.collection("donations")
                    .add(donation)
                    .addOnSuccessListener(documentReference -> {

                        // Create notification
                        Map<String, Object> notification = new HashMap<>();

                        notification.put(
                                "message",
                                "Food donation added successfully: " + foodName
                        );

                        notification.put("type", "Donation");
                        notification.put(
                                "timestamp",
                                System.currentTimeMillis()
                        );

                        db.collection("notifications")
                                .add(notification)
                                .addOnSuccessListener(notificationRef -> {

                                    Toast.makeText(
                                            DonateActivity.this, "Food donated successfully!",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    // Clear fields
                                    etFoodName.setText("");
                                    etQuantity.setText("");
                                    etLocation.setText("");
                                    etDescription.setText("");
                                });
                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                DonateActivity.this,
                                "Donation failed: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });
    }
}