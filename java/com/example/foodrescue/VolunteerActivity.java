package com.example.foodrescue;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class VolunteerActivity extends AppCompatActivity {

    ListView listRequests;
    ArrayList<String> requestsList;
    ArrayList<String> donationIds;

    ArrayAdapter<String> adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

        listRequests = findViewById(R.id.listRequests);

        requestsList = new ArrayList<>();
        donationIds = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                requestsList
        );

        listRequests.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadRequests();

        listRequests.setOnItemClickListener((parent, view, position, id) -> {

            String donationId = donationIds.get(position);

            showCollectionDialog(donationId);
        });
    }

    private void loadRequests() {

        db.collection("donations")
                .whereEqualTo("status", "Requested")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    requestsList.clear();
                    donationIds.clear();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                        String foodName = document.getString("foodName");
                        String quantity = document.getString("quantity");
                        String location = document.getString("location");

                        String request =
                                "🚚 Food Pickup Request\n\n" +
                                        "🍱 Food: " + foodName +
                                        "\nQuantity: " + quantity +
                                        "\n📍 Pickup Location: " + location +
                                        "\n\n👉 Tap to mark as Collected";

                        requestsList.add(request);
                        donationIds.add(document.getId());
                    }

                    adapter.notifyDataSetChanged();

                    if (requestsList.isEmpty()) {
                        Toast.makeText(
                                VolunteerActivity.this,
                                "No pickup requests available",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            VolunteerActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }

    private void showCollectionDialog(String donationId) {

        new AlertDialog.Builder(this)
                .setTitle("Collect Food")
                .setMessage("Have you collected this food donation?")
                .setNegativeButton("NO", null)
                .setPositiveButton("YES", (dialog, which) -> {

                    markAsCollected(donationId);
                })
                .show();
    }

    private void markAsCollected(String donationId) {

        db.collection("donations")
                .document(donationId)
                .update("status", "Collected")
                .addOnSuccessListener(unused -> {

                    Toast.makeText(
                            VolunteerActivity.this,
                            "Food marked as Collected! 🚚❤️",
                            Toast.LENGTH_LONG
                    ).show();

                    loadRequests();
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            VolunteerActivity.this,
                            "Update failed: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}