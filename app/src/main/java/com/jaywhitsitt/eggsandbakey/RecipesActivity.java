package com.jaywhitsitt.eggsandbakey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.jaywhitsitt.eggsandbakey.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity {

    @BindView(R.id.gv_recipes)
    GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(0, "Test", 2, ""));
        mGridView.setAdapter(new RecipesAdapter(this, recipes));
    }

}
