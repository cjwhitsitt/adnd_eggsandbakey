package com.jaywhitsitt.eggsandbakey.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Ingredient implements Serializable {

    @PrimaryKey(autoGenerate = true)
    Integer id = 0;
    public final Integer recipeId;
    public final Integer quantity;
    public final String unit;
    public final String name;

    public Ingredient(Integer recipeId, Integer quantity, String unit, String name) {
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
    }

}
