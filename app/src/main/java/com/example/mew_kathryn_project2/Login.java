package com.example.mew_kathryn_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/** DONE: Login Page
 * a. Username
 * b. Password
 *  If credentials are valid, move to home page (read values from database table to decide
 * if the credentials are valid), otherwise, show an appropriate message and stay on the login
 * screen.
 */
public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText input_username = findViewById(R.id.editLoginUsername);
        EditText input_password = findViewById(R.id.editLoginPassword);
        Database moodDb = new Database(Login.this);

        Button login = findViewById(R.id.loginBtn);
        login.setOnClickListener(v -> {
            String username = input_username.getText().toString();
            String password = input_password.getText().toString();

            // Check if entered username exists in the database
            if(UserRegistration.existsUsername(moodDb, username)) {
                try {
                    // Check if entered password is correct
                    if(matches(moodDb, username, password)) {
                        SharedPreferences sp = getSharedPreferences("current_user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", username);
                        // SELECT name FROM Users WHERE username='<username>'
                        editor.putString("name", moodDb.selectQuery(0, "name",
                                "username", username).get(0));
                        editor.apply();

                        Log.d("Login.SP", "=== contains User:"+ sp.contains("username"));
                        Log.d("Login.SP", "=== contains Name:"+ sp.contains("name"));
                        Intent goToHome = new Intent(getApplicationContext(), Home.class);
                        startActivity(goToHome);
                    } else {
                        Log.d("Login.Password", "=====Incorrect Password");
                        Toast.makeText(Login.this, "Incorrect Password!",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Log.d("Login.Username", "=====No match");
                Toast.makeText(Login.this, "No existing user with this username.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public static Boolean matches(Database database, String user, String pass)
            throws NoSuchAlgorithmException {
        byte[] res = database.getPasswordEntry(user);
        byte[] hash_pass = UserRegistration.messageDigest(pass);
        Log.d("Login.DB", "====="+Arrays.toString(res));
        Log.d("Login.Password", "====="+pass);
        return (Arrays.equals(res, hash_pass));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(toMain);
    }
}