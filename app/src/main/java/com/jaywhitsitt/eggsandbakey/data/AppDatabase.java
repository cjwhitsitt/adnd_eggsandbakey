package com.jaywhitsitt.eggsandbakey.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class, Step.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase getInstance(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "RecipeDatabase").build();
    }

    public abstract RecipeDao recipeDao();
    public abstract StepDao stepDao();

}
