package com.example.jien;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class User {
    private String name;
    private String email;
    private String password;

    private static final String SHARED_PREFS_KEY = "user_prefs";
    private static final String NAME_KEY = "name";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";

    private static User instance;

    private User() {
        // Private constructor to prevent instantiation outside the class
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    // Getters and setters for name, email, and password
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, name);
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);
        editor.apply();
    }

    // Load user data from SharedPreferences
    public void loadUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(NAME_KEY, "");
        email = sharedPreferences.getString(EMAIL_KEY, "");
        password = sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public boolean isValidLogin(String email, String password) {
        if (TextUtils.isEmpty(this.email) || TextUtils.isEmpty(this.password)) {
            return false;
        }
        return this.email.equals(email) && this.password.equals(password);
    }

    public void deleteUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        // After deleting the data from SharedPreferences, you should also reset the instance to null.
        instance = null;
    }
}

