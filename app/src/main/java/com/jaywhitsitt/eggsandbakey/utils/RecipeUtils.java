package com.jaywhitsitt.eggsandbakey.utils;

import android.util.Log;

import com.jaywhitsitt.eggsandbakey.data.Ingredient;
import com.jaywhitsitt.eggsandbakey.data.Recipe;
import com.jaywhitsitt.eggsandbakey.data.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeUtils {

    private final static String TAG = RecipeUtils.class.getSimpleName();

    private final static String ID_KEY = "id";
    private final static String NAME_KEY = "name";
    private final static String INGREDIENTS_KEY = "ingredients";
    private final static String STEPS_KEY = "steps";
    private final static String SERVINGS_KEY = "servings";
//    private final static String IMAGE_KEY = "image";

    private final static String SHORT_DESC_KEY = "shortDescription";
    private final static String DESC_KEY = "description";
    private final static String VIDEO_URL_KEY = "videoURL";
    private final static String THUMBNAIL_URL_KEY = "thumbnailURL";

    private final static String QUANTITY_KEY = "quantity";
    private final static String MEASURE_KEY = "measure";
    private final static String INGREDIENT_KEY = "ingredient";

    public static List<Recipe> getRecipesFromJson(String jsonString) {
        List<Recipe> recipes = new ArrayList<>();

        if (jsonString != null && jsonString.length() > 0) {
            try {
                JSONArray array = new JSONArray(jsonString);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject obj = array.getJSONObject(i);
                        int id = obj.getInt(ID_KEY);
                        String name = obj.getString(NAME_KEY);
                        List<Ingredient> ingredients = getIngredientsFromJsonObject(obj.getJSONArray(INGREDIENTS_KEY), id);
                        List<Step> steps = getStepsFromJsonObject(obj.getJSONArray(STEPS_KEY), id);
                        int servings = obj.getInt(SERVINGS_KEY);
//                    String imageUrl = obj.getString(IMAGE_KEY); // Always empty - can't support until valid data given

                        recipes.add(new Recipe(id, name, ingredients, steps, servings, null));


                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing recipe " + i);
                    }
                }

            } catch (JSONException e) {
                Log.e(TAG, "Unable to parse json");
            }
        }

        return recipes;
    }

    private static List<Step> getStepsFromJsonObject(JSONArray array, int recipeId) {
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt(ID_KEY);
                String shortDescription = obj.getString(SHORT_DESC_KEY);
                String description = obj.getString(DESC_KEY);
                String videoUrl = obj.getString(VIDEO_URL_KEY);
                String thumbnailUrl = obj.getString(THUMBNAIL_URL_KEY);
                steps.add(new Step(id, recipeId, shortDescription, description, videoUrl, thumbnailUrl));
            } catch (JSONException e) {
                Log.e(TAG, "Unable to parse step json");
            }
        }
        return steps;
    }

    private static List<Ingredient> getIngredientsFromJsonObject(JSONArray array, int recipeId) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                int quantity = obj.getInt(QUANTITY_KEY);
                String unit = obj.getString(MEASURE_KEY);
                String name = obj.getString(INGREDIENT_KEY);
                ingredients.add(new Ingredient(recipeId, quantity, unit, name));
            } catch (JSONException e) {
                Log.e(TAG, "Unable to parse ingredient json");
            }
        }
        return ingredients;
    }

}
