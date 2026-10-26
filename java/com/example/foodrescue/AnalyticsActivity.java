package com.example.foodrescue;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AnalyticsActivity extends AppCompatActivity {

    TextView tvTotal, tvAvailable, tvRequested, tvCollected;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        tvTotal = findViewById(R.id.tvTotal);
        tvAvailable = findViewById(R.id.tvAvailable);
        tvRequested = findViewById(R.id.tvRequested);
        tvCollected = findViewById(R.id.tvCollected);

        db = FirebaseFirestore.getInstance();

        loadAnalytics();
    }

    private void loadAnalytics() {

        db.collection("donations")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    int total = queryDocumentSnapshots.size();
                    int available = 0;
                    int requested = 0;
                    int collected = 0;

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                        String status = document.getString("status");

                        if (status == null) {
                            continue;
                        }

                        if (status.equals("Available")) {
                            available++;
                        } else if (status.equals("Requested")) {
                            requested++;
                        } else if (status.equals("Collected")) {
                            collected++;
                        }
                    }

                    tvTotal.setText("Total Donations: " + total);
                    tvAvailable.setText("Available: " + available);
                    tvRequested.setText("Requested: " + requested);
                    tvCollected.setText("Collected: " + collected);
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            AnalyticsActivity.this,
                            "Error loading analytics: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}