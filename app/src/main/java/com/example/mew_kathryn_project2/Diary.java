package com.example.mew_kathryn_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DONE: A field to enter notes (must be scrollable and allow multiple lines).
 *      You can choose an appropriate max length (optional)
 * DONE: Activities done on that day. Include a checkbox for different activities.
 *   For example, exercise, social interaction, etc. This field should have at least 5
 *   types of activities to choose from.
 */
public class Diary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ArrayList<String> activities = new ArrayList<>();
        Database diaryTBL = new Database(Diary.this);

        EditText input_note = findViewById(R.id.editNotes);
        input_note.setMovementMethod(new ScrollingMovementMethod());

        CheckBox exercise = findViewById(R.id.checkBox);
        exercise.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String cbText = buttonView.getText().toString();
            if(buttonView.isChecked()) {
                activities.add(cbText);
                Log.d("Add.CB", "====="+cbText);
            } else {
                activities.remove(cbText);
                Log.d("Remove.CB", "====="+cbText);
            }
        });

        CheckBox social = findViewById(R.id.checkBox2);
        social.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String cbText = buttonView.getText().toString();
            if(buttonView.isChecked()) {
                activities.add(cbText);
                Log.d("Add.CB", "====="+cbText);
            } else {
                activities.remove(cbText);
                Log.d("Remove.CB", "====="+cbText);
            }
        });

        CheckBox care = findViewById(R.id.checkBox3);
        care.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String cbText = buttonView.getText().toString();
            if(buttonView.isChecked()) {
                activities.add(cbText);
                Log.d("Add.CB", "====="+cbText);
            } else {
                activities.remove(cbText);
                Log.d("Remove.CB", "====="+cbText);
            }
        });

        CheckBox media = findViewById(R.id.checkBox4);
        media.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String cbText = buttonView.getText().toString();
            if(buttonView.isChecked()) {
                activities.add(cbText);
                Log.d("Add.CB", "====="+cbText);
            } else {
                activities.remove(cbText);
                Log.d("Remove.CB", "====="+cbText);
            }
        });

        CheckBox read = findViewById(R.id.checkBox5);
        read.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String cbText = buttonView.getText().toString();
            if(buttonView.isChecked()) {
                activities.add(cbText);
                Log.d("Add.CB", "====="+cbText);
            } else {
                activities.remove(cbText);
                Log.d("Remove.CB", "====="+cbText);
            }
        });

        Button submit = findViewById(R.id.submitDailyBtn);
        submit.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("current_user", MODE_PRIVATE);

            if(!activities.isEmpty()) {
                String noteEntry = input_note.getText().toString();
                String dateEntry = new SimpleDateFormat("MM-dd-yyyy",
                        Locale.getDefault()).format(new Date());
                if (activities.size() == 1) {
                    diaryTBL.addDaily(sp.getString("username", "user"),
                            dateEntry, noteEntry, activities.get(0));
                    Toast.makeText(Diary.this, "Diary Entry Added!",
                            Toast.LENGTH_LONG).show();
                    Log.d("Diary.Add", "=== User: "+sp.getString("username", "user"));
                    // Reset EditText and Checkboxes
                    input_note.setText("");
                    exercise.setChecked(false);
                    social.setChecked(false);
                    care.setChecked(false);
                    media.setChecked(false);
                    read.setChecked(false);
                } else {
                    String activityEntry = String.join(", ", activities);
                    diaryTBL.addDaily(sp.getString("username", "user"),
                            dateEntry, noteEntry, activityEntry);
                    Toast.makeText(Diary.this, "Diary Entry Added!",
                            Toast.LENGTH_LONG).show();
                    Log.d("Diary.Add", "=== User: "+sp.getString("username", "user"));
                    /*StringBuilder activityEntry = new StringBuilder();
                    for(String activity : activities) {
                        activityEntry.append(", ").append(activity);
                    }*/
                    // Reset EditText and Checkboxes
                    input_note.setText("");
                    exercise.setChecked(false);
                    social.setChecked(false);
                    care.setChecked(false);
                    media.setChecked(false);
                    read.setChecked(false);
                }
            } else {
                Toast.makeText(Diary.this,
                        "Please check at least one activity.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}