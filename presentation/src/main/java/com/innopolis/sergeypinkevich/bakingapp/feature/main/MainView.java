package com.innopolis.sergeypinkevich.bakingapp.feature.main;

import com.arellomobile.mvp.MvpView;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import java.util.List;

/**
 * @author Sergey Pinkevich
 */
public interface MainView extends MvpView {

    void showRecipes(List<Recipe> recipeList);

    void showInformationAboutRecipe(Recipe recipe);
}
