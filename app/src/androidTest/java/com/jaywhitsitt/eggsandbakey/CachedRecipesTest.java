package com.jaywhitsitt.eggsandbakey;

import android.app.Activity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Step;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.registerIdlingResources;
import static androidx.test.espresso.Espresso.unregisterIdlingResources;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

public class CachedRecipesTest {

    @Rule
    public IntentsTestRule<RecipesActivity> rule = new IntentsTestRule<>(RecipesActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = rule.getActivity().getIdlingResource();
        registerIdlingResources(idlingResource);
    }

    @Test
    public void recipesList_ClickItem() {
        onData(anything()).inAdapterView(withId(R.id.gv_recipes)).atPosition(0).perform(click());
        Intents.intended(allOf(
                hasExtra(StepListActivity.EXTRA_RECIPE_ID, 1)
        ));
    }

    @After
    public void unregisterIdlingResource() {
        unregisterIdlingResources(idlingResource);
    }

}
