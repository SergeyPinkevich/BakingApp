package com.innopolis.sergeypinkevich.bakingapp.feature.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.innopolis.sergeypinkevich.domain.repository.DataProvider;

import javax.inject.Inject;

/**
 * @author Sergey Pinkevich
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private DataProvider dataProvider;

    @Inject
    public MainPresenter(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public void loadRecipes() {
        getViewState().showRecipes(dataProvider.getRecipesList());
    }
}
