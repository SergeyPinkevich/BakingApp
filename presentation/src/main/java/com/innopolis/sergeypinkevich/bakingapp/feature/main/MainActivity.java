package com.innopolis.sergeypinkevich.bakingapp.feature.main;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.RecipeWidget;
import com.innopolis.sergeypinkevich.bakingapp.di.BaseApp;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.DetailActivity;
import com.innopolis.sergeypinkevich.domain.entity.Ingredient;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements MainView {

    public static final String RECIPE_KEY = "recipe";

    @BindView(R.id.main_recycler)
    RecyclerView recipesRecyclerView;

    @Inject
    @InjectPresenter
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BaseApp.component.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter.attachView(this);
        presenter.loadRecipes();
    }

    @Override
    public void showRecipes(final List<Recipe> recipeList) {
        MainAdapter adapter = new MainAdapter((view, position) -> {
            presenter.showInformationAboutRecipe(recipeList.get(position));
            updateWidget(recipeList.get(position));
        });
        adapter.setData(this, recipeList);
        recipesRecyclerView.setAdapter(adapter);
        if (isLandscapeOrientation()) {
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void showInformationAboutRecipe(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        startActivity(intent);
    }

    @Override
    public void showError() {
        Toasty.error(this, getString(R.string.error)).show();
    }

    private boolean isLandscapeOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void updateWidget(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.recipe_widget);
        ComponentName thisWidget = new ComponentName(getApplicationContext(), RecipeWidget.class);
        remoteViews.setTextViewText(R.id.recipe_ingredients, createRecipeStepsString(recipe));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }

    private String createRecipeStepsString(Recipe recipe) {
        StringBuilder builder = new StringBuilder();
        for (Ingredient ingredient : recipe.getIngredients()) {
            builder.append(ingredient.getQuantity() + " " + ingredient.getMeasure() + " " +
                    ingredient.getIngredient() + " \n");
        }
        return builder.toString();
    }
}
