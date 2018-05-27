package com.innopolis.sergeypinkevich.bakingapp.feature.detail.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.innopolis.sergeypinkevich.bakingapp.feature.detail.DetailActivity;
import com.innopolis.sergeypinkevich.domain.entity.Recipe;
import com.innopolis.sergeypinkevich.domain.entity.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailFragment extends ListFragment {

    private List<String> data;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prepareData(((DetailActivity)getActivity()).recipe);

        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, data);
        setListAdapter(adapter);
    }

    private void prepareData(Recipe recipe) {
        data = new ArrayList<>();
        data.add("Ingredients");
        int i = 1;
        for (Step step : recipe.getSteps()) {
            data.add(String.valueOf(i) + " " + step.getShortDescription());
            i++;
        }
    }
}
