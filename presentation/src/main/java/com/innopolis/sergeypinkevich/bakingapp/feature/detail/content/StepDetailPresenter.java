package com.innopolis.sergeypinkevich.bakingapp.feature.detail.content;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;
import com.innopolis.sergeypinkevich.domain.entity.Ingredient;
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
        int position = arguments.getInt(StepDetailFragment.STEP_KEY);
        Step step;
        // we click by some step
        if (position - 1 > 0) {
            step = recipe.getSteps().get(position);
            getViewState().showPlayer();
            getViewState().showVideo(step.getVideoURL());
            getViewState().showDescription(step.getDescription());
        } else {
            // show list ingredients
            StringBuilder builder = new StringBuilder();
            for (Ingredient i : recipe.getIngredients()) {
                builder.append(String.valueOf(i.getQuantity()) + " " + i.getMeasure() + " " + i.getIngredient() + "\n");
            }
            getViewState().hidePlayer();
            getViewState().showDescription(builder.toString());
        }
    }

    public void nextStep(Bundle arguments) {
        Recipe recipe = arguments.getParcelable(MainActivity.RECIPE_KEY);
        int currentStep = arguments.getInt(StepDetailFragment.STEP_KEY);
        if (currentStep < recipe.getSteps().size() - 1) {
            arguments.putInt(StepDetailFragment.STEP_KEY, currentStep + 1);
            handleCurrentStep(arguments);
        } else {
            getViewState().showMessageAboutLastStep();
        }
    }

    public void previousStep(Bundle arguments) {
        int currentStep = arguments.getInt(StepDetailFragment.STEP_KEY);
        if (currentStep > 0) {
            arguments.putInt(StepDetailFragment.STEP_KEY, currentStep - 1);
            handleCurrentStep(arguments);
        } else {
            getViewState().showMessageAboutFirstStep();
        }
    }
}
