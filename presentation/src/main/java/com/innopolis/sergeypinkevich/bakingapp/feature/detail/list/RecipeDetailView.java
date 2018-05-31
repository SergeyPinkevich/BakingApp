package com.innopolis.sergeypinkevich.bakingapp.feature.detail.list;

import com.arellomobile.mvp.MvpView;

import java.util.List;

/**
 * @author Sergey Pinkevich
 */
public interface RecipeDetailView extends MvpView {

    void showList(List<String> list);
}
