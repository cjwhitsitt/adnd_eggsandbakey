package com.jaywhitsitt.eggsandbakey;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaywhitsitt.eggsandbakey.data.Ingredient;
import com.jaywhitsitt.eggsandbakey.data.Step;

import java.security.InvalidParameterException;
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

        Bundle args = getArguments();
        if (args == null) {
            throw new InvalidParameterException("No arguments provided for detail display");

        } else if (args.containsKey(ARG_STEP)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mStep = (Step) args.getSerializable(ARG_STEP);
            if (mStep != null) {
                title = mStep.shortDescription;
            }

        } else if (args.containsKey(ARG_INGREDIENTS)) {
            mIngredients = (List<Ingredient>) args.getSerializable(ARG_INGREDIENTS);
            title = "Recipe Ingredients";
        }

        Activity activity = this.getActivity();
        if (activity != null) {
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(title);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        TextView textView = rootView.findViewById(R.id.step_detail);
        String text;

        if (mStep != null) {
            text = mStep.description;

        } else if (mIngredients != null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mIngredients.size() - 1; i++) {
                Ingredient ingredient = mIngredients.get(i);
                builder.append(getString(R.string.ingredient_format, ingredient.quantity, ingredient.unit, ingredient.name));
                if (i < mIngredients.size() - 2) {
                    builder.append("\n");
                }
            }
            text = builder.toString();
        } else {
            throw new InvalidParameterException("No data provided to display");
        }
        textView.setText(text);

        return rootView;
    }
}
