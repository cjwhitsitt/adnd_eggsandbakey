package com.jaywhitsitt.eggsandbakey.widgets;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.jaywhitsitt.eggsandbakey.R;
import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Ingredient;

import java.util.List;

public class IngredientsWidgetService extends RemoteViewsService {

    public static final String EXTRA_RECIPE_ID = "recipeId";
    public static final String EXTRA_WIDGET_ID = "widgetId";
    private LiveData<List<Ingredient>> mData;
    private Observer<List<Ingredient>> mObserver;
    private IngredientRemoteViewsFactory mFactory;

    public IngredientsWidgetService() {}

    @Override
    public void onCreate() {
        super.onCreate();
    }

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

        mFactory = new IngredientRemoteViewsFactory(getApplicationContext());
        mData = AppDatabase.getInstance().ingredientDao().getIngredientsForRecipe(recipeId);
        mObserver = new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                mFactory.setData(ingredients);
                Context context = getApplicationContext();
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.notifyAppWidgetViewDataChanged(widgetId, R.id.list_widget_ingredients);
            }
        };
        mData.observeForever(mObserver);
        return mFactory;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mData.removeObserver(mObserver);
    }
}
