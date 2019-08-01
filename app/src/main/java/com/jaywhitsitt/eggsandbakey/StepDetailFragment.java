package com.jaywhitsitt.eggsandbakey;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaywhitsitt.eggsandbakey.data.Ingredient;
import com.jaywhitsitt.eggsandbakey.data.Step;

import java.util.List;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_STEP = "step";
    /**
     * The fragment argument for whether or not this step is the last for the recipe.
     */
    public static final String ARG_IS_LAST = "isLast";
    /**
     * The fragment argument representing the list of ingredients for the recipe.
     */
    public static final String ARG_INGREDIENTS = "ingredients";

    /**
     * The dummy content this fragment is presenting.
     */
    private Step mStep;
    /**
     * The list of ingredients to display.
     */
    private List<Ingredient> mIngredients;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = "Step Detail";

        if (getArguments().containsKey(ARG_STEP)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mStep = (Step) getArguments().getSerializable(ARG_STEP);
            title = mStep.shortDescription;

        } else if (getArguments().containsKey(ARG_INGREDIENTS)) {
            mIngredients = (List<Ingredient>) getArguments().getSerializable(ARG_INGREDIENTS);
            title = "Recipe Ingredients";
        }

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(title);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        TextView textView = rootView.findViewById(R.id.step_detail);
        String text = "";

        // Show the dummy content as text in a TextView.
        if (mStep != null) {
            text = mStep.description;
        } else if (mIngredients != null) {
            for (Ingredient ingredient: mIngredients) {
                // TODO: localize
                text += String.valueOf(ingredient.quantity) + " " + ingredient.unit + " of " + ingredient.name + "\n";
            }
        }
        textView.setText(text);

        return rootView;
    }
}
