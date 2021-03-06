package com.innopolis.sergeypinkevich.bakingapp.feature.detail;

import com.arellomobile.mvp.MvpView;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

/**
 * @author Sergey Pinkevich
 */
public interface DetailView extends MvpView {

    void showRecipeDetailFragment(Recipe recipe);

    void setToolbarTitle(String text);
}
