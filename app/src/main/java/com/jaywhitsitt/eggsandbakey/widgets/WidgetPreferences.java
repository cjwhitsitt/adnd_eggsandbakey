package com.jaywhitsitt.eggsandbakey.widgets;

import android.content.Context;
import android.content.SharedPreferences;

import com.jaywhitsitt.eggsandbakey.R;

public class WidgetPreferences {

    private static final String PREFS_NAME = "com.jaywhitsitt.eggsandbakey.widgets.IngredientsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_recipeId_";
    public static final int NO_PREF_VALUE = -1;

    public static void saveRecipeIdPref(Context context, int appWidgetId, int recipeId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId, recipeId);
        prefs.apply();
    }

    static int getRecipeIdPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getInt(PREF_PREFIX_KEY + appWidgetId, NO_PREF_VALUE);
    }

    static void deleteRecipeIdPref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

}
