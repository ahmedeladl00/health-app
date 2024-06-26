/*
 * MIT License
 *
 * Copyright (c) 2023 RUB-SE-LAB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.jien;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class User {
    private String name;
    private String email;
    private String password;
    private String language;
    private static final String SHARED_PREFS_KEY = "user_prefs";
    private static final String NAME_KEY = "name";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String LANGUAGE = "language";

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
    public String getLanguage(){ return language;}
    public void setLanguage(String language) { this.language = language;}

    public void saveUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, name);
        editor.putString(EMAIL_KEY, email);
        editor.putString(PASSWORD_KEY, password);
        editor.putString(LANGUAGE,language);
        editor.apply();
    }

    public void loadUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(NAME_KEY, "");
        email = sharedPreferences.getString(EMAIL_KEY, "");
        password = sharedPreferences.getString(PASSWORD_KEY, "");
        language = sharedPreferences.getString(LANGUAGE,"");
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
        instance = null;
    }

    public void saveLanguagePreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }
}