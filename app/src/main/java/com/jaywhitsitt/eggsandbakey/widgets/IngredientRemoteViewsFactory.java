package com.jaywhitsitt.eggsandbakey.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.Handler;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.jaywhitsitt.eggsandbakey.R;
import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public static final int INVALID_RECIPE_ID = -1;

    private Context mContext;
    private int mRecipeId;
    private int mWidgetId;
    private LiveData<List<Ingredient>> mData;
    private Observer<List<Ingredient>> mObserver;

    public IngredientRemoteViewsFactory(Context context, int recipeId, int widgetId) {
        mContext = context;
        mRecipeId = recipeId;
        mWidgetId = widgetId;
    }

    @Override
    public void onCreate() {
        mData = AppDatabase.getInstance(mContext).ingredientDao().getIngredientsForRecipe(mRecipeId);
        mObserver = new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                AppWidgetManager manager = AppWidgetManager.getInstance(mContext);
                manager.notifyAppWidgetViewDataChanged(mWidgetId, R.id.list_widget_ingredients);
                IngredientsWidget.updateAppWidget(mContext, manager, mWidgetId, false);
            }
        };
        mData.observeForever(mObserver);
    }

    @Override
    public void onDestroy() {
        if (mData != null && mObserver != null) {
            // Get a handler that can be used to post to the main thread
            Handler mainHandler = new Handler(mContext.getMainLooper());

            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    mData.removeObserver(mObserver);
                }
            };
            mainHandler.post(myRunnable);
        }
    }

    private List<Ingredient> getIngredients() {
        return mData.getValue();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public void onDataSetChanged() {}

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return getIngredients() == null ? 0 : getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
        return rv;
    }

    @Override
    public long getItemId(int position) {
        return getIngredients().get(position).id;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
