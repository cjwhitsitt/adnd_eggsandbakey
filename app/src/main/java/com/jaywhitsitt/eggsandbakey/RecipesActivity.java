package com.jaywhitsitt.eggsandbakey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        mAdapter = new RecipesAdapter(this, this);
        mGridView.setAdapter(mAdapter);

        new RecipesFetchTask().execute();
    }

    private class RecipesFetchTask extends AsyncTask<Void, Void, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            String response = NetworkUtils.recipesResponse();
            return RecipeUtils.getRecipesFromJson(response);
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            mAdapter.setData(recipes);
        }

    }

    @Override
    public void onClickRecipe(int position) {
        Recipe recipe = mAdapter.getItem(position);

        Intent intent = new Intent(this, StepListActivity.class);
        intent.putExtra(StepListActivity.EXTRA_STEPS, (ArrayList<Step>) recipe.getSteps());
        startActivity(intent);
    }
}
