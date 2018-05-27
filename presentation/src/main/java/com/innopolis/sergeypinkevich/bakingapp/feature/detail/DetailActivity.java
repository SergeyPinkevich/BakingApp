package com.innopolis.sergeypinkevich.bakingapp.feature.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.di.BaseApp;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.list.RecipeDetailFragment;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @Inject
    @InjectPresenter
    DetailPresenter presenter;

    public Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BaseApp.component.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter.attachView(this);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.RECIPE_KEY)) {
            recipe = getIntent().getExtras().getParcelable(MainActivity.RECIPE_KEY);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initFragments(Recipe recipe) {
//        getSupportFragmentManager().beginTransaction().add(setupRecipeDetailFragment(recipe)).commit()
    }

    @Override
    public void setupRecipeDetailFragment(Recipe recipe) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(MainActivity.RECIPE_KEY, recipe);
//        RecipeDetailFragment fragment = new RecipeDetailFragment();
//        fragment.setArguments(bundle);
//        return fragment;
    }
}
