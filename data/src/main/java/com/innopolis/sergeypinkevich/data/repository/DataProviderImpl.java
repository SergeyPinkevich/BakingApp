package com.innopolis.sergeypinkevich.data.repository;

import android.content.Context;

import com.innopolis.sergeypinkevich.domain.entity.Ingredient;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;
import com.innopolis.sergeypinkevich.domain.entity.Step;
import com.innopolis.sergeypinkevich.domain.repository.DataProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Sergey Pinkevich
 */
public class DataProviderImpl implements DataProvider {

    public static final String FILE_NAME = "baking.json";

    public static final String RECIPE_ID = "id";
    public static final String NAME = "name";
    public static final String SERVINGS = "servings";
    public static final String STEPS = "steps";
    public static final String INGREDIENTS = "ingredients";
    public static final String STEP_ID = "id";
    public static final String SHORT_DESCRIPTION = "shortDescription";
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "videoURL";
    public static final String QUANTITY = "quantity";
    public static final String MEASURE = "measure";
    public static final String INGREDIENT = "ingredient";

    private Context context;

    @Inject
    public DataProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<Recipe> getRecipesList() {
        return parseJson(readJSONFromAsset());
    }

    private List<Recipe> parseJson(String json) {
        List<Recipe> recipeList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject recipe = array.getJSONObject(i);
                recipeList.add(parseRecipeFromJson(recipe));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return recipeList;
    }

    private Recipe parseRecipeFromJson(JSONObject jsonObject) {
        Recipe recipe = null;
        try {
            int id = jsonObject.getInt(RECIPE_ID);
            String name = jsonObject.getString(NAME);
            int servings = jsonObject.getInt(SERVINGS);
            List<Step> steps = parseRecipeStepsFromJson(jsonObject.getJSONArray(STEPS));
            List<Ingredient> ingredients = parseRecipeIngredientsFromJson(jsonObject.getJSONArray(INGREDIENTS));
            recipe = new Recipe(id, name, ingredients, steps, servings);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return recipe;
    }

    private List<Step> parseRecipeStepsFromJson(JSONArray jsonArray) {
        List<Step> steps = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject step = jsonArray.getJSONObject(i);
                int id = step.getInt(STEP_ID);
                String shortDescription = step.getString(SHORT_DESCRIPTION);
                String description = step.getString(DESCRIPTION);
                String videoURL = step.getString(VIDEO_URL);
                steps.add(new Step(id, shortDescription, description, videoURL));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return steps;
    }

    private List<Ingredient> parseRecipeIngredientsFromJson(JSONArray jsonArray) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject step = jsonArray.getJSONObject(i);
                double quantity = step.getDouble(QUANTITY);
                String measure = step.getString(MEASURE);
                String ingredient = step.getString(INGREDIENT);
                ingredients.add(new Ingredient(quantity, measure, ingredient));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return ingredients;
    }

    private String readJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open(FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
