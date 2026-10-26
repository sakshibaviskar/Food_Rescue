package com.example.foodrescue;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FindFoodActivity extends AppCompatActivity {

    ListView listFood;
    ArrayList<String> foodList;
    ArrayList<String> donationIds;
    ArrayAdapter<String> adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_food);

        listFood = findViewById(R.id.listFood);

        foodList = new ArrayList<>();
        donationIds = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                foodList
        );

        listFood.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadAvailableFood();

        listFood.setOnItemClickListener((parent, view, position, id) -> {

            String donationId = donationIds.get(position);

            db.collection("donations")
                    .document(donationId)
                    .update("status", "Requested")
                    .addOnSuccessListener(unused -> {

                        Toast.makeText(
                                FindFoodActivity.this,
                                "Food requested successfully!",
                                Toast.LENGTH_SHORT
                        ).show();

                        loadAvailableFood();
                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                FindFoodActivity.this,
                                "Request failed: " + e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    });
        });
    }

    private void loadAvailableFood() {

        db.collection("donations")
                .whereEqualTo("status", "Available")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    foodList.clear();
                    donationIds.clear();

                    for (var document : queryDocumentSnapshots) {

                        String foodName = document.getString("foodName");
                        String quantity = document.getString("quantity");
                        String location = document.getString("location");

                        String foodDetails =
                                "Food: " + foodName +
                                        "\nQuantity: " + quantity +
                                        "\nLocation: " + location +
                                        "\n\nTap to Request";

                        foodList.add(foodDetails);
                        donationIds.add(document.getId());
                    }

                    adapter.notifyDataSetChanged();

                    if (foodList.isEmpty()) {
                        Toast.makeText(
                                FindFoodActivity.this,
                                "No food available right now.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            FindFoodActivity.this,
                            "Failed to load food: " + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}