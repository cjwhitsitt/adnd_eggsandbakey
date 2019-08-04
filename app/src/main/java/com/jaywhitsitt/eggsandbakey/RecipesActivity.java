package com.jaywhitsitt.eggsandbakey;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.espresso.IdlingResource;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Recipe;
import com.jaywhitsitt.eggsandbakey.data.Step;
import com.jaywhitsitt.eggsandbakey.testhelpers.RecipeFetchIdlingResource;
import com.jaywhitsitt.eggsandbakey.utils.NetworkUtils;
import com.jaywhitsitt.eggsandbakey.utils.RecipeUtils;
import com.jaywhitsitt.eggsandbakey.widgets.IngredientsWidget;
import com.jaywhitsitt.eggsandbakey.widgets.WidgetPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.OnClickHandler {

    @BindView(R.id.gv_recipes)
    GridView mGridView;
    RecipesAdapter mAdapter;
    AppDatabase mDb;

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    private RecipeFetchIdlingResource mIdlingResource;
    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipeFetchIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        mAdapter = new RecipesAdapter(this, this);
        mGridView.setAdapter(mAdapter);

        mDb = AppDatabase.getInstance();
        LiveData<List<Recipe>> recipes = mDb.recipeDao().getAll();
        recipes.observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                mAdapter.setData(recipes);
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
            mAppWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (savedInstanceState == null && mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            new RecipesFetchTask(mDb).execute();
        } else {
            setResult(RESULT_CANCELED);
            setTitle("Choose a Recipe"); // TODO: localize
        }
    }

    private class RecipesFetchTask extends AsyncTask<Void, Void, List<Recipe>> {

        private final String TAG = RecipesFetchTask.class.getSimpleName();
        private AppDatabase mDb;

        public RecipesFetchTask(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getIdlingResource();
            RecipesActivity.this.mIdlingResource.setIdleState(false);
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            String response = NetworkUtils.recipesResponse();
            return RecipeUtils.getRecipesFromJson(response);
        }

        @Override
        protected void onPostExecute(final List<Recipe> recipes) {
            super.onPostExecute(recipes);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (recipes == null) {
                        Log.i(TAG, "No data received. Falling back to cached data.");
                    } else {
                        mDb.clearAllTables();
                        mDb.recipeDao().insertAll(recipes);
                        for (Recipe recipe : recipes) {
                            mDb.stepDao().addSteps(recipe.steps);
                            mDb.ingredientDao().addIngredients(recipe.ingredients);
                        }
                        IngredientsWidget.updateAllInstances(getApplicationContext());
                    }
                    RecipesActivity.this.mIdlingResource.setIdleState(true);
                }
            });
            thread.start();
        }

    }

    @Override
    public void onClickRecipe(int position) {
        Recipe recipe = mAdapter.getItem(position);

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            Intent intent = new Intent(this, StepListActivity.class);
            intent.putExtra(StepListActivity.EXTRA_RECIPE_ID, recipe.id);
            startActivity(intent);
        } else {
            WidgetPreferences.saveRecipeIdPref(this, mAppWidgetId, recipe.id);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            IngredientsWidget.updateAppWidget(this, appWidgetManager, mAppWidgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    }
}
