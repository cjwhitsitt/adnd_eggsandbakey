package com.jaywhitsitt.eggsandbakey.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jaywhitsitt.eggsandbakey.MyApplication;

@Database(entities = {Recipe.class, Step.class, Ingredient.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase singleton;

    public static AppDatabase getInstance() {
        if (singleton == null) {
            singleton = getInstance(MyApplication.getInstance());
        }
        return singleton;
    }

    public static AppDatabase getInstance(Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                "RecipeDatabase").build();
    }

    public abstract RecipeDao recipeDao();
    public abstract StepDao stepDao();
    public abstract IngredientDao ingredientDao();

}
