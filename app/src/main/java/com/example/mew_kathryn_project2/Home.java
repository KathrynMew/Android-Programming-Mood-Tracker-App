package com.example.mew_kathryn_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;

/**
 * DONE: Your picture
 * DONE: Show a welcome message with the preferred name given by the user during
 *  registration (use select query to extract the name field from the registration
 *  table in the database)
 * DONE: Link/button for daily diary
 * DONE: View my notes
 * DONE: Tracker
 * DONE: Helpful Links
 * DONE: Logout
 */
public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sp = getSharedPreferences("current_user", MODE_PRIVATE);

        Database db = new Database(Home.this);

        ImageView userImage = findViewById(R.id.userPhoto);
        // Set general image if new user
        userImage.setImageResource(R.drawable.snowball);
        String username = sp.getString("username", "user");
        // Set specific image if initial user
        if(username.equals("km4tq")) {
            userImage.setImageResource(R.drawable.km4tqpic);
        } else if (username.equals("zand4")) {
            userImage.setImageResource(R.drawable.lk7rgpic);
        } else if (username.equals("cp9ie")) {
            userImage.setImageResource(R.drawable.cp3dspic);
        } else if (username.equals("ka7hd")) {
            userImage.setImageResource(R.drawable.ka7hdpic);
        }

        TextView message = findViewById(R.id.welcomeMessage);
        String wm = message.getText().toString() + " "
                + sp.getString("name", "user") + "!";
        message.setText(wm);

        Button diary = findViewById(R.id.dailyDiaryBtn);
        diary.setOnClickListener(v -> {
            Intent goToDiary = new Intent(getApplicationContext(), Diary.class);
            startActivity(goToDiary);
        });

        Button notes = findViewById(R.id.notesBtn);
        notes.setOnClickListener(v -> {
            Intent goToNotes = new Intent(getApplicationContext(), Notes.class);
            startActivity(goToNotes);
        });

        Button track = findViewById(R.id.trackerBtn);
        track.setOnClickListener(v -> {
            Intent goToTracker = new Intent(getApplicationContext(), Tracker.class);
            startActivity(goToTracker);
        });

        Button links = findViewById(R.id.helpLinksBtn);
        links.setOnClickListener(v -> {
            Intent goToLinks = new Intent(getApplicationContext(), Links.class);
            startActivity(goToLinks);
        });

        Button motiv_quotes = findViewById(R.id.quotesBtn);
        motiv_quotes.setOnClickListener(v -> {
            Intent goToQuotes = new Intent(getApplicationContext(), Quotes.class);
            startActivity(goToQuotes);
        });

        Button logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(v -> {
            sp.edit().clear().apply();
            Log.d("Logout.SP", "=== contains User:"+ sp.contains("username"));
            Log.d("Logout.SP", "=== contains Name:"+ sp.contains("name"));
            Intent goToLogin = new Intent(getApplicationContext(), Login.class);
            startActivity(goToLogin);
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        // When "back" button is clicked, does nothing
    }
}