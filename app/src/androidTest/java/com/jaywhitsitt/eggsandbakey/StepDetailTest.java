package com.jaywhitsitt.eggsandbakey;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.jaywhitsitt.eggsandbakey.data.AppDatabase;
import com.jaywhitsitt.eggsandbakey.data.Step;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.registerIdlingResources;
import static androidx.test.espresso.Espresso.unregisterIdlingResources;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasPackage;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.jaywhitsitt.eggsandbakey.VideoActivity.EXTRA_VIDEO_URL;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

public class StepDetailTest {

    @Rule
    public IntentsTestRule<StepDetailActivity> rule = new IntentsTestRule<>(StepDetailActivity.class, false, false);

    private static final int stepId = 1;
    private static final int recipeId = -1;
    private static final String shortDescription = "Prep the cookie crust.";
    private static final String description = "2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.";
    private static final String videoUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4";
    @Before
    public void setupIncomingExtras() {
        Step step = new Step(stepId,
                recipeId,
                shortDescription,
                description,
                videoUrl,
                "");
        Intent intent = new Intent();
        intent.putExtra(StepDetailFragment.ARG_STEP, step);
        rule.launchActivity(intent);
    }

    @Test
    public void stepDetail_Data() {
        onView(withText(description)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_play)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void stepDetail_PlayVideo() {
        onView(withId(R.id.fab_play)).perform(click());
        Intents.intended(allOf(
                hasComponent(VideoActivity.class.getName()),
                hasExtra(EXTRA_VIDEO_URL, videoUrl)
        ));
    }

}
