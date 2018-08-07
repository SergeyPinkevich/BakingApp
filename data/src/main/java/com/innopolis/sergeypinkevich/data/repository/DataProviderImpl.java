package com.innopolis.sergeypinkevich.data.repository;

import com.innopolis.sergeypinkevich.domain.entity.Recipe;
import com.innopolis.sergeypinkevich.domain.repository.DataProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

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
    public static final String THUMBNAIL_URL = "thumbnailURL";

    BakingService service;

    @Inject
    public DataProviderImpl(BakingService service) {
        this.service = service;
    }

    @Override
    public Single<List<Recipe>> getRecipesList() {
        return service.getRecipes();
    }
}
