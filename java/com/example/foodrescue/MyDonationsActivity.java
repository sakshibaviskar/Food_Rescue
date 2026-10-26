package com.example.foodrescue;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MyDonationsActivity extends AppCompatActivity {

    ListView listMyDonations;
    ArrayList<String> donationsList;
    ArrayAdapter<String> adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);

        listMyDonations = findViewById(R.id.listMyDonations);

        donationsList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                donationsList
        );

        listMyDonations.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadMyDonations();
    }

    private void loadMyDonations() {

        db.collection("donations")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    donationsList.clear();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                        String foodName = document.getString("foodName");
                        String quantity = document.getString("quantity");
                        String location = document.getString("location");
                        String status = document.getString("status");

                        String donation =
                                "🍱 Food: " + foodName +
                                        "\nQuantity: " + quantity +
                                        "\n📍 Location: " + location +
                                        "\nStatus: " + status;

                        donationsList.add(donation);
                    }

                    adapter.notifyDataSetChanged();

                    if (donationsList.isEmpty()) {
                        Toast.makeText(
                                MyDonationsActivity.this,
                                "No donations found",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            MyDonationsActivity.this,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}