package com.innopolis.sergeypinkevich.bakingapp.feature.detail;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import javax.inject.Inject;

/**
 * @author Sergey Pinkevich
 */
@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> {

    @Inject
    public DetailPresenter() {}

    public void handleIntentData(Recipe recipe) {
        getViewState().setToolbarTitle(recipe.getName());
        getViewState().showRecipeDetailFragment(recipe);
    }
}
