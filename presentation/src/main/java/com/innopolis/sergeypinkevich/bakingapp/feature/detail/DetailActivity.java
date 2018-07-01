package com.innopolis.sergeypinkevich.bakingapp.feature.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.di.BaseApp;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.list.RecipeDetailFragment;
import com.innopolis.sergeypinkevich.bakingapp.feature.main.MainActivity;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

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

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            twoPane = true;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter.attachView(this);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.RECIPE_KEY)) {
            presenter.handleIntentData(getIntent().getExtras().getParcelable(MainActivity.RECIPE_KEY));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showRecipeDetailFragment(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.RECIPE_KEY, recipe);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.list_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setToolbarTitle(String text) {
        getSupportActionBar().setTitle(text);
    }
}
