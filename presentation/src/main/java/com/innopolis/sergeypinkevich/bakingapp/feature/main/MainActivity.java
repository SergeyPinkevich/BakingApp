package com.innopolis.sergeypinkevich.bakingapp.feature.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.di.BaseApp;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

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
    public void showRecipes(List<Recipe> recipeList) {
        MainAdapter adapter = new MainAdapter(this, recipeList);
        recipesRecyclerView.setAdapter(adapter);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
