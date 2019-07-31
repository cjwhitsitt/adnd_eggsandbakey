package com.jaywhitsitt.eggsandbakey.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = Recipe.class,
            parentColumns = "id",
            childColumns = "recipeId"
        ),
        indices = @Index(value = { "recipeId" })
)
public class Ingredient implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public final int id;
    public final int recipeId;
    public final int quantity;
    public final String unit;
    public final String name;

    public Ingredient(int id, int recipeId, int quantity, String unit, String name) {
        this.id = id;
        this.recipeId = recipeId;
        this.quantity = quantity;
        this.unit = unit;
        this.name = name;
    }

    @Ignore
    public Ingredient(int recipeId, int quantity, String unit, String name) {
        this(0, recipeId, quantity, unit, name);
    }

}
