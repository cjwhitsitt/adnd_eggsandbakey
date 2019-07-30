package com.jaywhitsitt.eggsandbakey.utils;

import android.util.Log;

import com.jaywhitsitt.eggsandbakey.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeUtils {

    private final static String TAG = RecipeUtils.class.getSimpleName();

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
//    private final static String INGREDIENTS_KEY = "ingredients";
//    private final static String STEPS_KEY = "steps";
    private final static String SERVINGS_KEY = "servings";
//    private final static String IMAGE_KEY = "image";

    public static List<Recipe> getRecipesFromJson(String jsonString) {
        List<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    int id = obj.getInt(ID_KEY);
                    String name = obj.getString(NAME_KEY);

//                    JSONArray ingredientsArray = obj.getJSONArray(INGREDIENTS_KEY);
//                    JSONArray stepsArray = obj.getJSONArray(STEPS_KEY);

                    int servings = obj.getInt(SERVINGS_KEY);
//                    String imageUrl = obj.getString(IMAGE_KEY); // Always empty - can't support until valid data given

                    recipes.add(new Recipe(id, name, servings, null));

                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing recipe " + i);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "Unable to parse json");
        }

        return recipes;
    }

}
