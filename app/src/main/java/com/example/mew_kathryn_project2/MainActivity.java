package com.example.mew_kathryn_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * DONE: Main page with options for “User registration” and “Login”
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database db = new Database(MainActivity.this);
        db.addQuotes(MainActivity.this);

        Button register = findViewById(R.id.register_btn);
        register.setOnClickListener(v -> {
            Intent next = new Intent(getApplicationContext(), UserRegistration.class);
            startActivity(next);
        });

        Button login = findViewById(R.id.login_btn);
        login.setOnClickListener(v -> {
            Intent next = new Intent(getApplicationContext(), Login.class);
            startActivity(next);
        });

    }
}