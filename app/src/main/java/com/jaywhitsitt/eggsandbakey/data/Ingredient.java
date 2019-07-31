package com.jaywhitsitt.eggsandbakey.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    Integer id = 0;
    final Integer recipeId;
    final Integer quantity;
    final String unit;
    final String name;

    public Ingredient(Integer recipeId, Integer quantity, String unit, String name) {
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
    }

}
