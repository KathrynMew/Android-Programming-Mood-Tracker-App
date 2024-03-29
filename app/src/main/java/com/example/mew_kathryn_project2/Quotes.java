package com.example.mew_kathryn_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class Quotes extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String[] themes = {"--- Select a theme ---", "Creativity", "Disney", "Life"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                themes
        );
        // Apply the adapter to the spinner.
        spinner.setAdapter(adapter);

        RecyclerView rv = findViewById(R.id.quoteRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(Quotes.this));
        rv.setAdapter(new quoteAdapter(new ArrayList<>()));

        // Set listener on the activity that implements AdapterView
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long row) {
        if (pos > 0) {
            Database quoteDB = new Database(Quotes.this);
            ArrayList<String> quotes = quoteDB.selectQuery(3, "quotes", "theme",
                    parent.getItemAtPosition(pos).toString());
            RecyclerView rv = findViewById(R.id.quoteRecyclerView);
            rv.setLayoutManager(new LinearLayoutManager(Quotes.this));
            rv.setAdapter(new quoteAdapter(quotes));
            // For debugging
            Log.d("Theme", "====="+parent.getItemAtPosition(pos).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toHome = new Intent(getApplicationContext(), Home.class);
        startActivity(toHome);
    }
}

