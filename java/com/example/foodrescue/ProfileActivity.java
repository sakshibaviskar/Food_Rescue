package com.example.foodrescue;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    TextView tvEmail;
    Button btnLogout;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        tvEmail = findViewById(R.id.tvEmail);
        btnLogout = findViewById(R.id.btnLogout);

        // Show logged-in user's email
        if (mAuth.getCurrentUser() != null) {
            tvEmail.setText(mAuth.getCurrentUser().getEmail());
        }

        // Logout
        btnLogout.setOnClickListener(v -> {

            mAuth.signOut();

            Toast.makeText(
                    ProfileActivity.this,
                    "Logged out successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
        });
    }
}