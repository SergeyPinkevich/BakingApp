package com.innopolis.sergeypinkevich.bakingapp.feature.detail.list;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;
import com.innopolis.sergeypinkevich.domain.entity.Step;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Sergey Pinkevich
 */
@InjectViewState
public class RecipeDetailPresenter extends MvpPresenter<RecipeDetailView> {

    @Inject
    public RecipeDetailPresenter() {}

    public void prepareData(Bundle arguments) {
        Recipe recipe = arguments.getParcelable(MainActivity.RECIPE_KEY);
        List<String> data = new ArrayList<>();
        data.add("Ingredients");
        int i = 1;
        for (Step step : recipe.getSteps()) {
            data.add(String.valueOf(i) + " " + step.getShortDescription());
            i++;
        }
        getViewState().showList(data);
    }
}
