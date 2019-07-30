package com.jaywhitsitt.eggsandbakey.data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StepDao {

    @Query("SELECT * FROM Step WHERE recipeId = :recipeId")
    public List<Step> getStepsForRecipe(int recipeId);

}
