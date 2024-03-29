package com.example.mew_kathryn_project2;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;

import static java.lang.Character.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;


/** DONE: User Registration
 * a. Name
 * b. Age
 * c. Gender
 * d. Username (length: 5, must be alphanumeric)
 * e. Password (length: 8, should start with uppercase)
 */
public class UserRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        EditText input_name = findViewById(R.id.editName);
        EditText input_age = findViewById(R.id.editAge);
        EditText input_gender = findViewById(R.id.editGender);
        EditText input_username = findViewById(R.id.editUsername);
        EditText input_password = findViewById(R.id.editPassword);

        Database moodDb = new Database(UserRegistration.this);

        Button submit = findViewById(R.id.submitInfoBtn);
        submit.setOnClickListener(v -> {
            String name = input_name.getText().toString();
            String age = input_age.getText().toString();
            String gender = input_gender.getText().toString();
            String checkUsername = input_username.getText().toString();
            String checkPassword = input_password.getText().toString();

            if ((name.isEmpty()) || (gender.isEmpty()) || (age.isEmpty()) ||
                    (checkUsername.isEmpty()) || (checkPassword.isEmpty())) {
                Log.d("Register.Fields", "===== Field is Empty");
                Toast.makeText(UserRegistration.this,
                        "Please fill all fields before proceeding",
                        Toast.LENGTH_LONG).show();
            } else if (!validUsername(checkUsername)) {
                Log.d("Register.Username", "===== Not Valid");
                Toast.makeText(UserRegistration.this,
                        "Username must be exactly 5 chars and alphanumeric",
                        Toast.LENGTH_LONG).show();
            } else if (existsUsername(moodDb, checkUsername)) {
                Log.d("Register.Username", "===== Existing User");
                Toast.makeText(UserRegistration.this,
                        "Oops! Username is taken!", Toast.LENGTH_LONG).show();
            } else if (!validPassword(checkPassword)) {
                Log.d("Register.Password", "===== Not Valid");
                Toast.makeText(UserRegistration.this,
                        "Password must be exactly 8 chars and start with upper case.",
                        Toast.LENGTH_LONG).show();
            } else {
                try {
                    // Add new user to database
                    moodDb.addUser(name, age, gender, checkUsername, messageDigest(checkPassword));
                    Toast.makeText(UserRegistration.this,
                            "Registration Success!", Toast.LENGTH_LONG).show();
                    Log.d("Register.Add", "=====Add reached!");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                // Reset EditText values
                input_name.setText("");
                input_age.setText("");
                input_gender.setText("");
                input_username.setText("");
                input_password.setText("");
            }
        });
    }

    public static Boolean containsNumeric(String pass) {
        for (char c : pass.toCharArray()) {
            if (isDigit(c)) { return true; }
        }
        return false;
    }

    // Username must be 5 characters (no spaces) and alphanumeric
    public static Boolean validUsername(String user) {
        Log.d("valid.User", "====="+user);
        return (containsNumeric(user)) && (user.length() == 5);
    }

    // Password must be 8 characters (no spaces), and start with upper case
    public static Boolean validPassword(String pass) {
        Log.d("valid.Pass", "====="+pass);
        return (pass.length() == 8) && (pass.charAt(0) == toUpperCase(pass.charAt(0)));
    }

    /**
     * Checks if there is an existing user with the username
     * @param database
     * @param user
     * @return Boolean - Username is available if returns true
     */
    public static Boolean existsUsername(Database database, String user) {
        // SELECT username FROM Users WHERE username='<user>'
        ArrayList<String> res = database.selectQuery(0, "username", "username", user);
        Log.d("exists.User", "====="+user);
        Log.d("exists.DB", "====="+res);
        return (!res.isEmpty());
    }

    public static byte[] messageDigest(String s) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(s.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent toMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(toMain);
    }
}