package com.jaywhitsitt.eggsandbakey.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.jaywhitsitt.eggsandbakey.R;
import com.jaywhitsitt.eggsandbakey.data.Ingredient;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link com.jaywhitsitt.eggsandbakey.RecipesActivity RecipesActivity}
 */
public class IngredientsWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        int recipeId = WidgetPreferences.getRecipeIdPref(context, appWidgetId);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);
        int listViewId = R.id.list_widget_ingredients;
        int errorViewId = R.id.tv_widget_not_setup;

        boolean valid = false; // TODO: recipeId != AppWidgetManager.INVALID_APPWIDGET_ID;
        views.setViewVisibility(R.id.list_widget_ingredients, valid ? View.VISIBLE : View.GONE);
        views.setViewVisibility(R.id.tv_widget_not_setup, valid ? View.GONE : View.VISIBLE);

        if (valid) {
            // TODO: views.setRemoteAdapter(R.id.list_widget_ingredients, intent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WidgetPreferences.deleteRecipeIdPref(context, appWidgetId);
        }
    }

}
