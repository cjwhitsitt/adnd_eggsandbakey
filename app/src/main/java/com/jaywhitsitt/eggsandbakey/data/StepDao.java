package com.jaywhitsitt.eggsandbakey.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StepDao {

    @Query("SELECT * FROM Step WHERE recipeId = :recipeId")
    List<Step> getStepsForRecipe(int recipeId);

    @Insert
    void addSteps(List<Step> steps);

    @Query("DELETE FROM Step")
    void deleteAll();

}
