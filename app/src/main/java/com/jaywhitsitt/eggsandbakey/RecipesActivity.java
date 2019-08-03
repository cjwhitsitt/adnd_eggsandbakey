package com.jaywhitsitt.eggsandbakey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Recipe;
import com.jaywhitsitt.eggsandbakey.data.Step;
import com.jaywhitsitt.eggsandbakey.utils.NetworkUtils;
import com.jaywhitsitt.eggsandbakey.utils.RecipeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.OnClickHandler {

    @BindView(R.id.gv_recipes)
    GridView mGridView;
    RecipesAdapter mAdapter;
    AppDatabase mDb;

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

        if (savedInstanceState == null) {
            new RecipesFetchTask(mDb).execute();
        }
    }

    private static class RecipesFetchTask extends AsyncTask<Void, Void, List<Recipe>> {

        private static final String TAG = RecipesFetchTask.class.getSimpleName();
        private AppDatabase mDb;

        public RecipesFetchTask(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            String response = NetworkUtils.recipesResponse();
            return RecipeUtils.getRecipesFromJson(response);
        }

        @Override
        protected void onPostExecute(final List<Recipe> recipes) {
            super.onPostExecute(recipes);

            if (recipes == null) {
                Log.i(TAG, "No data received. Falling back to cached data.");
                return;
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mDb.clearAllTables();
                    mDb.recipeDao().insertAll(recipes);
                    for (Recipe recipe: recipes) {
                        mDb.stepDao().addSteps(recipe.steps);
                        mDb.ingredientDao().addIngredients(recipe.ingredients);
                    }
                }
            });
            thread.start();
        }

    }

    @Override
    public void onClickRecipe(int position) {
        Recipe recipe = mAdapter.getItem(position);

        Intent intent = new Intent(this, StepListActivity.class);
        intent.putExtra(StepListActivity.EXTRA_RECIPE_ID, recipe.id);
        startActivity(intent);
    }
}
