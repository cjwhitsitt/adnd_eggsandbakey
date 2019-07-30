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
    public int id;
    public String name;
//    private List<Ingredient> ingredients;
    @Ignore
    public List<Step> steps;
    public int servings;
    public String imageUrlString;

    public Recipe(int id, String name, int servings, String imageUrlString) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrlString = imageUrlString;
    }

    public Recipe(int id, String name, /* List<Ingredient> ingredients,*/ List<Step> steps, int servings, String imageUrlString) {
        new Recipe(id, name, servings, imageUrlString);
//        this.ingredients = ingredients;
        this.steps = steps;
    }

}
