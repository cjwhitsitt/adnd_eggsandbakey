package com.jaywhitsitt.eggsandbakey.data;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe implements Serializable {

    @PrimaryKey
    public final Integer id;
    public final String name;
//    private List<Ingredient> ingredients;
    @Ignore
    public List<Step> steps;
    public final int servings;
    public final String imageUrlString;

    public Recipe(Integer id, String name, int servings, String imageUrlString) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrlString = imageUrlString;
    }

    @Ignore
    public Recipe(Integer id, String name, /* List<Ingredient> ingredients,*/ List<Step> steps, int servings, String imageUrlString) {
        this(id, name, servings, imageUrlString);
//        this.ingredients = ingredients;
        this.steps = steps;
    }

}
