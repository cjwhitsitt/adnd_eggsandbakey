package com.jaywhitsitt.eggsandbakey.widgets;

import android.content.Context;
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
    private List<Ingredient> mData = new ArrayList<>();
    private Context mContext;

    public IngredientRemoteViewsFactory(Context context) {
        mContext = context;
    }

    void setData(List<Ingredient> ingredients) {
        mData = ingredients;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
        return rv;
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).id;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
