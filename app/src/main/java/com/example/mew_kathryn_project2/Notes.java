package com.example.mew_kathryn_project2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

/**
 * DONE: This page should list all the notes entered by the user.
 * DONE: Include the delete and filter option in the View Notes page.
 *  Delete should delete the selected record from the table. You may use a
 *  checkbox or a button to implement the delete functionality.
 */
public class Notes extends AppCompatActivity {

    private static String user;
    private static ArrayList<String> userNotesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Database diaryDB = new Database(Notes.this);

        SharedPreferences sp = getSharedPreferences("current_user", MODE_PRIVATE);
        user = sp.getString("username", "user");
        // Get list of notes using selectQuery
        userNotesList = diaryDB.selectQuery(1, "notes", "username", user);

        RecyclerView rv = findViewById(R.id.recyclerView);
        // Check whether user has any diary entries
        if(!userNotesList.isEmpty()) {
            // Display all notes
            rv.setLayoutManager(new LinearLayoutManager(Notes.this));
            rv.setAdapter(new noteAdapter(userNotesList, (notes_list, position) -> {
                diaryDB.deleteEntry(notes_list.get(position));
                Toast.makeText(Notes.this, "Diary Entry Deleted!",
                        Toast.LENGTH_LONG).show();
                reset();
            }));
        } else {
            // If no notes, displays "Diary Empty!" text to user
            rv.setVisibility(View.GONE);
            TextView none = findViewById(R.id.warningNote);
            none.setVisibility(View.VISIBLE);
        }

        Button filter = findViewById(R.id.filterBtn);
        filter.setOnClickListener(f -> {
            EditText kw = findViewById(R.id.editFilterText);
            String word = kw.getText().toString().toLowerCase();

            if(!word.isEmpty()) {
                filterNotes(word);
                // Removes filtered notes
                filter.setVisibility(View.GONE);
                Button undo = findViewById(R.id.undoBtn);
                undo.setVisibility(View.VISIBLE);
                undo.setOnClickListener(u -> {
                    kw.setText("");
                    undo.setVisibility(View.GONE);
                    filter.setVisibility(View.VISIBLE);
                    reset();
                });
            } else {
                Toast.makeText(Notes.this, "Please enter a keyword to filter by!",
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    /***
     * Filters and displays notes that contain the keyword provided by user
     * @param keyword String user-provided
     */
    private void filterNotes(String keyword) {
        Database diaryDB = new Database(Notes.this);

        // Get filtered notes for user
        ArrayList<String> filteredNotesList = new ArrayList<>();
        for(String note : userNotesList) {
            // By-pass case sensitivity of `contains()`
            if((note.toLowerCase()).contains(keyword)) {
                filteredNotesList.add(note);
            }
        }

        // Display filtered notes
        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(Notes.this));
        rv.setAdapter(new noteAdapter(filteredNotesList, (array, position) -> {
            // Delete note from both diaryDB and userNotesList
            diaryDB.deleteEntry(array.get(position));
            userNotesList.remove(array.get(position));
            // Show user delete successful
            Toast.makeText(Notes.this, "Diary Entry Deleted!",
                    Toast.LENGTH_LONG).show();
            filterNotes(keyword);
        }));
    }

    private void reset() {
        Log.d("Notes.Reset", "===== REACHED!");

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toHome = new Intent(getApplicationContext(), Home.class);
        startActivity(toHome);
    }
}