package com.innopolis.sergeypinkevich.bakingapp.feature.main;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;
import com.innopolis.sergeypinkevich.domain.repository.DataProvider;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        dataProvider.getRecipesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> getViewState().showRecipes(data), error -> getViewState().showError());
    }

    public void showInformationAboutRecipe(Recipe recipe) {
        getViewState().showInformationAboutRecipe(recipe);
    }
}
