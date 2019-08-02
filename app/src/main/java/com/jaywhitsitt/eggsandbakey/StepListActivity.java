package com.jaywhitsitt.eggsandbakey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.core.app.NavUtils;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;
import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Ingredient;
import com.jaywhitsitt.eggsandbakey.data.Recipe;
import com.jaywhitsitt.eggsandbakey.data.Step;
import com.jaywhitsitt.eggsandbakey.utils.PlaybackUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity implements StepAdapter.ShowDetailListener {

    public static final String EXTRA_RECIPE_ID = "com.jaywhitsitt.eggsandbakey.StepListActivity.recipeId";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe mCurrentRecipe;
    private AppDatabase mDB;
    private StepAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        } else {
            findViewById(R.id.fab_play).setVisibility(View.GONE);
        }

        RecyclerView recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        mAdapter = new StepAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mDB = AppDatabase.getInstance();
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_RECIPE_ID)) {
            int recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, -1);
            mDB.recipeDao().getRecipeById(recipeId).observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(Recipe recipe) {
                    mCurrentRecipe = recipe;
                }
            });
            mDB.stepDao().getStepsForRecipe(recipeId).observe(this, new Observer<List<Step>>() {
                @Override
                public void onChanged(List<Step> steps) {
                    mAdapter.setSteps(steps);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXTRA_RECIPE_ID, mCurrentRecipe.id);
        super.onSaveInstanceState(outState);
    }

    /*
    ShowDetailListener methods
     */

    @Override
    public void showStep(Step step, View view) {
        Bundle extras = new Bundle();
        extras.putSerializable(StepDetailFragment.ARG_STEP, step);
        extras.putBoolean(StepDetailFragment.ARG_IS_LAST, step.stepId >= mCurrentRecipe.steps.size() - 1);

        if (mTwoPane) {
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();

            View playFab = findViewById(R.id.fab_play);
            PlaybackUtils.setupPlayFab(this, step, playFab);

            // TODO: Highlight selected item

        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, StepDetailActivity.class);
            intent.putExtras(extras);
            context.startActivity(intent);
        }
    }

    @Override
    public void showIngredients(View view) {
        Bundle extras = new Bundle();
        extras.putSerializable(StepDetailFragment.ARG_INGREDIENTS, (ArrayList<Ingredient>) mCurrentRecipe.ingredients);

        if (mTwoPane) {
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(extras);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
            findViewById(R.id.fab_play).setVisibility(View.GONE);

        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, StepDetailActivity.class);
            intent.putExtras(extras);
            context.startActivity(intent);
        }
    }

}
