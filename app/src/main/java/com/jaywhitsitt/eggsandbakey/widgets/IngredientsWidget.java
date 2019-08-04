package com.jaywhitsitt.eggsandbakey.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.jaywhitsitt.eggsandbakey.R;
import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Ingredient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link com.jaywhitsitt.eggsandbakey.RecipesActivity RecipesActivity}
 */
public class IngredientsWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, boolean loading) {
        int recipeId = WidgetPreferences.getRecipeIdPref(context, appWidgetId);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);

        boolean valid = recipeId != WidgetPreferences.NO_PREF_VALUE;
        views.setViewVisibility(R.id.tv_widget_not_setup, valid ? View.GONE : View.VISIBLE);
        views.setViewVisibility(R.id.list_widget_ingredients, valid && !loading ? View.VISIBLE : View.GONE);
        views.setViewVisibility(R.id.tv_widget_loading, loading ? View.VISIBLE : View.GONE);

        if (valid && loading) {
            Intent intent = new Intent(context, IngredientsWidgetService.class);
            intent.putExtra(IngredientsWidgetService.EXTRA_RECIPE_ID, recipeId);
            intent.putExtra(IngredientsWidgetService.EXTRA_WIDGET_ID, appWidgetId);
            views.setRemoteAdapter(R.id.list_widget_ingredients, intent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, true);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WidgetPreferences.deleteRecipeIdPref(context, appWidgetId);
        }
    }

    public static void updateAllInstances(Context context) {
        Intent intent = new Intent();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(new ComponentName(context, IngredientsWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] ids = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            onUpdate(context, manager, ids);
        }
    }

}
