package com.innopolis.sergeypinkevich.bakingapp.feature.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.di.BaseApp;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.content.StepDetailFragment;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.list.RecipeDetailFragment;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @Inject
    @InjectPresenter
    DetailPresenter presenter;

    public boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BaseApp.component.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        if (smallestWidth() > 600) {
            twoPane = true;
        }

        presenter.attachView(this);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.RECIPE_KEY)) {
            presenter.handleIntentData(getIntent().getExtras().getParcelable(MainActivity.RECIPE_KEY));
        }
    }

    private float smallestWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        return Math.min(widthDp, heightDp);
    }

    @Override
    public void showRecipeDetailFragment(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.RECIPE_KEY, recipe);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_fragment, fragment)
                .addToBackStack(RecipeDetailFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void setToolbarTitle(String text) {
        getSupportActionBar().setTitle(text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (twoPane) {
                finish();
            } else {
                onBackPressed();
            }
        }
        return false;
    }
}
