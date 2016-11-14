package com.skiefboom.cocktailexplorerwithapi.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wittig on 14/11/16.
 */

public class PersistentHelper {

    private static final String PREFERENCES_NAME = "CocktailExplorer";
    private static final String KEY_DRINK_COUNT = "drinkCount";

    private static SharedPreferences preferences;

    public static void initialize(Context context) {

        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static Integer getDrinkCount() {

        return preferences.getInt(KEY_DRINK_COUNT, 0);
    }

    public static void setDrinkCount(Integer count) {

        preferences.edit().putInt(KEY_DRINK_COUNT, count).apply();
    }
}
