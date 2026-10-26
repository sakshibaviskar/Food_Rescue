package com.example.foodrescue;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView tvWelcome;

    Button btnDonateFood;
    Button btnFindFood;
    Button btnMyDonations;
    Button btnVolunteer;
    Button btnFoodQuality;
    Button btnAnalytics;
    Button btnProfile;
    Button btnLogout;
    Button btnNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tvWelcome);

        btnDonateFood = findViewById(R.id.btnDonateFood);
        btnFindFood = findViewById(R.id.btnFindFood);
        btnMyDonations = findViewById(R.id.btnMyDonations);
        btnVolunteer = findViewById(R.id.btnVolunteer);
        btnFoodQuality = findViewById(R.id.btnFoodQuality);
        btnAnalytics = findViewById(R.id.btnAnalytics);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnNotifications.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
        });

        tvWelcome.setText("Welcome to Food Rescue!");

        btnDonateFood.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, DonateActivity.class));
        });

        btnFindFood.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FindFoodActivity.class));
        });

        btnMyDonations.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MyDonationsActivity.class));
        });

        btnVolunteer.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, VolunteerActivity.class));
        });

        btnFoodQuality.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FoodQualityActivity.class));
        });

        btnAnalytics.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AnalyticsActivity.class));
        });

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            finish();
        });
    }

    // Phone Back Button
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Exit Food Rescue?")
                .setMessage("Do you want to exit the app?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    finishAffinity();
                })
                .setNegativeButton("No", null)
                .show();
    }
}