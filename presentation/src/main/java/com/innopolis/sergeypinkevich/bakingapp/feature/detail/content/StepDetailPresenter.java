package com.innopolis.sergeypinkevich.bakingapp.feature.detail.content;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;
import com.innopolis.sergeypinkevich.domain.entity.Step;

import javax.inject.Inject;

/**
 * @author Sergey Pinkevich
 */
@InjectViewState
public class StepDetailPresenter extends MvpPresenter<StepDetailView> {

    @Inject
    public StepDetailPresenter() {}

    public void handleCurrentStep(Bundle arguments) {
        Recipe recipe = arguments.getParcelable(MainActivity.RECIPE_KEY);
        Step step = recipe.getSteps().get(arguments.getInt(StepDetailFragment.STEP_KEY));
        getViewState().showVideo(step.getVideoURL());
        getViewState().showDescription(step.getDescription());
    }
}
