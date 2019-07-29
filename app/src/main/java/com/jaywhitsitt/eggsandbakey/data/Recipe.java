package com.jaywhitsitt.eggsandbakey.data;

public class Recipe {

    private int id;
    private String name;
//    private List<Ingredient> ingredients;
//    private List<Step> steps;
    private int servings;
    private String imageUrlString;

    public Recipe(int id, String name, /* List<Ingredient> ingredients, List<Step> steps, */ int servings, String imageUrlString) {
        this.id = id;
        this.name = name;
//        this.ingredients = ingredients;
//        this.steps = steps;
        this.servings = servings;
        this.imageUrlString = imageUrlString;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImageUrlString() {
        return imageUrlString;
    }

}
