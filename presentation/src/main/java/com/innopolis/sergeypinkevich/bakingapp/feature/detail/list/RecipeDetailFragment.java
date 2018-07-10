package com.innopolis.sergeypinkevich.bakingapp.feature.detail.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.innopolis.sergeypinkevich.bakingapp.R;
import com.innopolis.sergeypinkevich.bakingapp.di.BaseApp;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.DetailActivity;
import com.innopolis.sergeypinkevich.bakingapp.feature.detail.content.StepDetailFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.innopolis.sergeypinkevich.bakingapp.feature.detail.content.StepDetailFragment.STEP_KEY;

public class RecipeDetailFragment extends Fragment implements RecipeDetailView {

    @Inject
    @InjectPresenter
    RecipeDetailPresenter presenter;

    private ListView listView;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        BaseApp.component.inject(this);
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        presenter.attachView(this);
        presenter.prepareData(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        listView = view.findViewById(R.id.list_view);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void showList(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        if (((DetailActivity)getActivity()).twoPane) {
            listView.setOnItemClickListener((parent, view, position, id) -> getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.step_detail, prepareFragment(position))
                    .addToBackStack(null)
                    .commit());
        } else {
            listView.setOnItemClickListener((parent, view, position, id) -> getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_fragment, prepareFragment(position))
                    .addToBackStack(null)
                    .commit());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return false;
        } else {
            // Do some other things to other menu
            return super.onOptionsItemSelected(item);
        }
    }

    private StepDetailFragment prepareFragment(int step) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle bundle = getArguments();
        bundle.putInt(STEP_KEY, step);
        fragment.setArguments(bundle);
        return fragment;
    }
}