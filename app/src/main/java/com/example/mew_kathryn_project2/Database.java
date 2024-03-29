package com.example.mew_kathryn_project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Mood Tracker Database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME_USERS = "Users";
    private static final String TABLE_NAME_DIARY = "DailyDiary";
    private static final String TABLE_NAME_TRACK = "MoodTracker";

    // For Users table
    private static final String NAME_COL = "name";
    private static final String AGE_COL = "age";
    private static final String GENDER_COL = "gender";
    private static final String USERNAME_COL = "username";
    private static final String PASSWORD_COL = "password";

    // For Daily Diary table
    private static final String DATE_COL = "date";
    private static final String NOTES_COL = "notes";
    private static final String ACTIVITY_COL = "activities";

    // For Tracker
    // DATE_COL
    private static final String MOOD_COL = "mood";
    private static final String ANXIETY_COL = "anxiety";
    private static final String MEDICATION_COL = "meds";

    // Bonus Points
    private static final String TABLE_NAME_QUOTES = "MotivationalQuotes";
    private static final String THEME_COL = "theme";
    private static final String QUOTE_COL = "quotes";

    // Stores all table names
    private static final String[] TABLE_NAMES
            = {TABLE_NAME_USERS,
            TABLE_NAME_DIARY,
            TABLE_NAME_TRACK,
            TABLE_NAME_QUOTES};

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for Users
        String createUsers = "CREATE TABLE " + TABLE_NAME_USERS + " ("
                + NAME_COL + " TEXT, "
                + AGE_COL + " TEXT, "
                + GENDER_COL + " TEXT, "
                + USERNAME_COL + " TEXT, "
                + PASSWORD_COL + " BLOB)";

        // Create table for Daily Diary
        String createDiary = "CREATE TABLE " + TABLE_NAME_DIARY + " ("
                + USERNAME_COL + " TEXT, "
                + DATE_COL + " TEXT, "
                + NOTES_COL + " TEXT, "
                + ACTIVITY_COL + " TEXT)";

        // Create table for Tracker
        String createTracker = "CREATE TABLE " + TABLE_NAME_TRACK + " ("
                + USERNAME_COL + " TEXT, "
                + DATE_COL + " TEXT, "
                + MOOD_COL + " TEXT, "
                + ANXIETY_COL + " INTEGER, "
                + MEDICATION_COL + " TEXT)";

        // Create table for motivational quotes
        String createQuote = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_QUOTES + " ("
                + THEME_COL + " TEXT, "
                + QUOTE_COL + " TEXT)";

        db.execSQL(createUsers);
        db.execSQL(createDiary);
        db.execSQL(createTracker);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUOTES);
        db.execSQL(createQuote);
    }

    /**
     * Inserts new entry into "Users" table
     * @param name String, name of user
     * @param age String, age of user
     * @param gender
     * @param username
     * @param password
     */
    public void addUser(String name, String age, String gender, String username, byte[] password) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(NAME_COL, name);
        values.put(AGE_COL, age);
        values.put(GENDER_COL, gender);
        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);
        db.insert(TABLE_NAME_USERS, null, values);
        db.close();
    }

    /**
     * Inserts new entry into "Daily Diary"
     * @param date
     * @param note
     * @param activity
     */
    public void addDaily(String username, String date, String note, String activity) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(USERNAME_COL, username);
        values.put(DATE_COL, date);
        values.put(NOTES_COL, note);
        values.put(ACTIVITY_COL, activity);
        db.insert(TABLE_NAME_DIARY, null, values);
        db.close();
    }

    /**
     * Inserts new entry into "Mood Tracker"
     * @param date
     * @param mood
     * @param anxiety
     * @param med
     */
    public void addMood(String username, String date, String mood, String anxiety, String med) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(USERNAME_COL, username);
        values.put(DATE_COL, date);
        values.put(MOOD_COL, mood);
        values.put(ANXIETY_COL, Integer.parseInt(anxiety));
        values.put(MEDICATION_COL, med);
        db.insert(TABLE_NAME_TRACK, null, values);
        db.close();
    }

    public void addQuotes(Context context){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // Reset Quotes Table
            db.execSQL("DELETE FROM " + TABLE_NAME_QUOTES);

            // Read Quotes.txt file
            Scanner reader = new Scanner(new DataInputStream(context.getAssets().open("Quotes.txt")));
            while(reader.hasNextLine()) {
                String[] theme_quote = reader.nextLine().split(": ");
                ContentValues values = new ContentValues();
                values.put(THEME_COL, theme_quote[0]);
                values.put(QUOTE_COL, theme_quote[1]);
                db.insert(TABLE_NAME_QUOTES, null, values);
            }
            reader.close();
            db.close();
        } catch(FileNotFoundException e) {
            Log.e("", "Failure to open or find Quotes.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> selectQuery(int table, String col,
                              @Nullable String filterCol, @Nullable String filterBy) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> contents = new ArrayList<>();
        String query;
        if (filterCol == null && filterBy == null) {
            query = "SELECT " + col + " FROM " + TABLE_NAMES[table];
        } else {
            query = "SELECT " + col
                    + " FROM " + TABLE_NAMES[table]
                    + " WHERE " + filterCol + "='" + filterBy + '\'';
        }
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            while(c.moveToNext()) {
                String q = c.getString(0);
                contents.add(q);
            }
        }
        c.close();
        return contents;
    }

    public byte[] getPasswordEntry(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + PASSWORD_COL
                + " FROM " + TABLE_NAME_USERS
                + " WHERE " + USERNAME_COL + "='" + user + '\'';
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        byte[] p = c.getBlob(0);
        c.close();
        return p;
    }

    public void deleteEntry(String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_DIARY, NOTES_COL + "=?", new String[]{note});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DIARY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRACK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUOTES);
        onCreate(db);
    }
}
