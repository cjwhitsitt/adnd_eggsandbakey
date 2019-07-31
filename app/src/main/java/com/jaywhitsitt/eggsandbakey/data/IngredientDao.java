package com.jaywhitsitt.eggsandbakey.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM Ingredient WHERE recipeId = :recipeId")
    LiveData<List<Ingredient>> getIngredientsForRecipe(int recipeId);

    @Insert
    void addIngredients(List<Ingredient> ingredients);

}
