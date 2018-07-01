package com.innopolis.sergeypinkevich.bakingapp.feature.detail.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

        presenter.attachView(this);
        presenter.prepareData(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        listView = view.findViewById(R.id.list_view);
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

    private StepDetailFragment prepareFragment(int step) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle bundle = getArguments();
        bundle.putInt(STEP_KEY, step);
        fragment.setArguments(bundle);
        return fragment;
    }
}