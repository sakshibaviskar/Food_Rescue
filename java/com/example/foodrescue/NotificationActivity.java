package com.example.foodrescue;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ListView listNotifications;
    ArrayList<String> notificationList;
    ArrayAdapter<String> adapter;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        listNotifications = findViewById(R.id.listNotifications);

        notificationList = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notificationList
        );

        listNotifications.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadNotifications();
    }

    private void loadNotifications() {

        db.collection("notifications")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    notificationList.clear();

                    for (var document : queryDocumentSnapshots) {

                        String message =
                                document.getString("message");

                        if (message != null) {
                            notificationList.add("🔔 " + message);
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (notificationList.isEmpty()) {
                        Toast.makeText(
                                NotificationActivity.this,
                                "No notifications yet.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            NotificationActivity.this,
                            "Failed to load notifications.",
                            Toast.LENGTH_SHORT
                    ).show();
                });
    }
}