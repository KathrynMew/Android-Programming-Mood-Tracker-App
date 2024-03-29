package com.example.mew_kathryn_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.*;
import android.content.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DONE: Ask user about their mood. You can choose different ways/widgets to ask this
 *  question like rating, spinner, buttons etc. Options are-- neutral, sad, very sad,
 *  happy, very happy.
 * DONE: Anxiety
 *  i. Ask user to mark their anxiety level. The range is from 0 to 5; 0
 *  indicates not anxious, and 5 indicates the highest level of anxiousness.
 * DONE: Medication adherence
 *  i. This will ask the user if they are currently on any medication2. If so, ask
 *  a question—“Did you take your medication2 on time today?”. Also,
 *  include an option for “Not applicable”.
 */
public class Tracker extends AppCompatActivity
        implements medication1.OnMedicationCheckedListener,
                   medication2.OnMedicationTodayCheckedListener {

    private static final String MEDS_TODAY_YES = "yes, on time";
    private static final String MEDS_TODAY_NO = "yes, late";
    private static final String MEDS_TODAY_NA = "yes, not applicable";

    private static String currentMood = "";
    private static int medicationsCheckedID = -1;
    private static String medsToday = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        TextView showFeeling = findViewById(R.id.moodTextView);

        ImageButton very_sad = findViewById(R.id.verySad);
        very_sad.setOnClickListener(v -> {
            currentMood = "very sad";
            showFeeling.setText(R.string.very_sad);
            Log.d("Mood", "=== "+ very_sad.getContentDescription() + " | " + currentMood);
        });

        ImageButton sad = findViewById(R.id.sad);
        sad.setOnClickListener(v -> {
            currentMood = "sad";
            showFeeling.setText(R.string.sad);
            Log.d("Mood", "=== "+ sad.getContentDescription() + " | " + currentMood);
        });

        ImageButton neutral = findViewById(R.id.neutral);
        neutral.setOnClickListener(v -> {
            currentMood = "neutral";
            showFeeling.setText(R.string.neutral);
            Log.d("Mood", "=== "+ neutral.getContentDescription() + " | " + currentMood);
        });

        ImageButton happy = findViewById(R.id.happy);
        happy.setOnClickListener(v -> {
            currentMood = "happy";
            showFeeling.setText(R.string.happy);
            Log.d("Mood", "=== "+ happy.getContentDescription() + " | " + currentMood);
        });

        ImageButton very_happy = findViewById(R.id.veryHappy);
        very_happy.setOnClickListener(v -> {
            currentMood = "very happy";
            showFeeling.setText(R.string.very_happy);
            Log.d("Mood", "=== "+ very_happy.getContentDescription() + " | " + currentMood);
        });

        RadioGroup anxietyLevel = findViewById(R.id.anxietyGroup);
        // Uncheck or reset the radio buttons for anxietyGroup initially
        anxietyLevel.clearCheck();
        Log.d("Anxiety.Click", "=== CLEAR");

        // Loads medication1 into FrameLayout
        loadFragment(new medication1());

        Button submitMood = findViewById(R.id.submitTrackerBtn);
        submitMood.setOnClickListener(v -> {
            SharedPreferences userSP = getSharedPreferences("current_user", MODE_PRIVATE);
            Database trackDB = new Database(Tracker.this);

            // Get Current System Date
            String dateEntry = new SimpleDateFormat("MM-dd-yyyy",
                    Locale.getDefault()).format(new Date());

            // Retrieve user responses if all fields have a response
            if((anxietyLevel.getCheckedRadioButtonId() != -1) && (medicationsCheckedID != -1)) {

                // Get corresponding checked RadioButton in ANXIETY LEVEL RadioGroup
                RadioButton AnxRB = anxietyLevel.findViewById(anxietyLevel.getCheckedRadioButtonId());
                Log.d("Anxiety.Button", "=== "+AnxRB.getText().toString());

                // medsGroup.getCheckedRadioButtonId() == R.id.yesMeds
                if(medicationsCheckedID == 1){

                    if (!medsToday.isEmpty()) {

                        // Add entry to mood table
                        trackDB.addMood(userSP.getString("username", "user"),
                                dateEntry, currentMood, AnxRB.getText().toString(), medsToday);
                        Toast.makeText(Tracker.this, "Mood Tracker Added!",
                                Toast.LENGTH_LONG).show();
                        Log.d("Tracker.Add", "=== Yes; reached!");

                        // Reset meds_today file and refresh page via `recreate()`
                        AnxRB.setChecked(false);
                        reset();
                    } else {
                        Toast.makeText(Tracker.this,
                                "Please answer whether you've taken your meds on time today!",
                                Toast.LENGTH_LONG).show();
                        Log.d("MedsToday.Error", "===== EMPTY!!!");
                    }
                } else {
                    trackDB.addMood(userSP.getString("username", "user"),
                            dateEntry, currentMood, AnxRB.getText().toString(), "no");
                    Toast.makeText(Tracker.this, "Mood Tracker Added!",
                            Toast.LENGTH_LONG).show();
                    Log.d("Tracker.Add", "=== No; reached!");
                    AnxRB.setChecked(false);
                    reset();
                }
            } else {
                Toast.makeText(Tracker.this,
                        "Responses required for button questionnaires!",
                        Toast.LENGTH_LONG).show();
                Log.d("Anxiety.id", "=== "+anxietyLevel.getCheckedRadioButtonId());
                Log.d("Meds.id", "=== "+medicationsCheckedID);
            }
        });
    }

    /***
     * Replaces FrameLayout with Fragment
     * @param fragment Fragment corresponding fragment class.
     */
    private void loadFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }

    /***
     * Implementation for medication1.OnMedicationCheckedListener (interface)
     * @param val int represents user response; 0 = "no", 1 = "yes"
     */
    @Override
    public void onMedicationClick(int val) {
        medicationsCheckedID = val;
        if(val == 1) {
            loadFragment(new medication2());
            Log.d("onMedicationClick", "===== LOAD MEDICATION2");
        }
    }

    /***
     * Implementation for medication2.OnMedicationTodayCheckedListener
     * @param val int represents user response; 0 = "no", 1 = "yes", -1 = "na"
     */
    @Override
    public void onMedTodayClick(int val) {
        switch(val) {
            case 1:
                medsToday = MEDS_TODAY_YES;
                Log.d("onMedTodayClick", "=== ON TIME");
                break;
            case 0:
                medsToday = MEDS_TODAY_NO;
                Log.d("onMedTodayClick", "=== LATE");
                break;
            case -1:
                medsToday = MEDS_TODAY_NA;
                Log.d("onMedTodayClick", "=== NOT APPLICABLE");
                break;
        }
    }

    /***
     * Resets the values of the app
     */
    private void reset() {
        Log.d("Tracker.Reset", "===== REACHED!");
        currentMood = "";
        medicationsCheckedID = -1;
        medsToday = "";

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