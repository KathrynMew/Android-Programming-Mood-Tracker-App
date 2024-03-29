package com.example.mew_kathryn_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;

/**
 * DONE: This page should have helpful links (resources) for mental health awareness.
 * DONE: The links (minimum 2) should navigate to relevant web pages.
 */
public class Links extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);

        Button aada = findViewById(R.id.btn_link1);
        aada.setOnClickListener(v -> {
            Intent launch = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://adaa.org/tips"));
            startActivity(launch);
        });

        Button retk = findViewById(R.id.btn_link2);
        retk.setOnClickListener(v -> {
            Intent launch = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.rethink.org/advice-and-information/"));
            startActivity(launch);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toHome = new Intent(getApplicationContext(), Home.class);
        startActivity(toHome);
    }
}