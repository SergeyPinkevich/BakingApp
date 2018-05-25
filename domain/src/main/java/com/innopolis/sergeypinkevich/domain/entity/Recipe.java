package com.innopolis.sergeypinkevich.domain.entity;

import java.util.List;

/**
 * @author Sergey Pinkevich
 */
public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> mIngredients;
    private List<Step> steps;
    private int servings;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings) {
        this.id = id;
        this.name = name;
        this.mIngredients = ingredients;
        this.steps = steps;
        this.servings = servings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
