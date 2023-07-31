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
import java.util.Calendar;

public class Water {
    private static final String PREF_NAME = "WaterPref";
    private static final String KEY_GOAL = "goal";
    private static final String KEY_CONSUMED = "consumed";
    private static final String KEY_LAST_RESET_TIMESTAMP = "lastResetTimestamp";

    private static Water instance;
    private SharedPreferences preferences;

    private Water(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static Water getInstance(Context context) {
        if (instance == null) {
            instance = new Water(context);
        }
        return instance;
    }

    public int getGoal() {
        return preferences.getInt(KEY_GOAL, 0);
    }

    public void setGoal(int goal) {
        preferences.edit().putInt(KEY_GOAL, goal).apply();
    }

    public int getConsumed() {
        return preferences.getInt(KEY_CONSUMED, 0);
    }

    public void addWater(Context context, int amount) {
        int consumed = getConsumed() + amount;
        preferences.edit().putInt(KEY_CONSUMED, consumed).apply();
        saveResetTimestampIfNeeded(context);
    }

    public int getRemaining() {
        return getGoal() - getConsumed();
    }

    public void resetConsumption(Context context) {
        preferences.edit().putInt(KEY_CONSUMED, 0).apply();
        saveResetTimestamp(context);
    }

    private long getLastResetTimestamp() {
        return preferences.getLong(KEY_LAST_RESET_TIMESTAMP, 0);
    }

    private void saveResetTimestamp(Context context) {
        long currentTimestamp = Calendar.getInstance().getTimeInMillis();
        preferences.edit().putLong(KEY_LAST_RESET_TIMESTAMP, currentTimestamp).apply();
    }

    private void saveResetTimestampIfNeeded(Context context) {
        long lastResetTimestamp = getLastResetTimestamp();
        long currentTimestamp = Calendar.getInstance().getTimeInMillis();
        if (!isSameDay(lastResetTimestamp, currentTimestamp)) {
            resetConsumption(context);
        }
    }

    private boolean isSameDay(long timestamp1, long timestamp2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTimeInMillis(timestamp1);
        cal2.setTimeInMillis(timestamp2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}