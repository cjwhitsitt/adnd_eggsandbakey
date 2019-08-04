package com.jaywhitsitt.eggsandbakey.widgets;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

import androidx.annotation.CallSuper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.jaywhitsitt.eggsandbakey.R;
import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsWidgetService extends RemoteViewsService {

    public static final String EXTRA_RECIPE_ID = "recipeId";
    public static final String EXTRA_WIDGET_ID = "widgetId";
    public static final String EXTRA_INGREDIENTS = "ingredients";

    public IngredientsWidgetService() {}

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, IngredientRemoteViewsFactory.INVALID_RECIPE_ID);
        if (recipeId == IngredientRemoteViewsFactory.INVALID_RECIPE_ID) {
            return null;
        }

        int widgetId = intent.getIntExtra(EXTRA_WIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            return null;
        }

        IngredientRemoteViewsFactory factory = new IngredientRemoteViewsFactory(getApplicationContext(), recipeId, widgetId);
        List<Ingredient> ingredients = AppDatabase.getInstance(getApplicationContext()).ingredientDao().getIngredientsForRecipe(recipeId).getValue();
        if (intent.hasExtra(EXTRA_INGREDIENTS) && intent.getSerializableExtra(EXTRA_INGREDIENTS) != null) {

        } else {

        }

        return factory;
    }

}
