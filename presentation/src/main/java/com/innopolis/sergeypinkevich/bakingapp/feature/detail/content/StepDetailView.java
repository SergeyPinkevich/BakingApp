package com.innopolis.sergeypinkevich.bakingapp.feature.detail.content;

import com.arellomobile.mvp.MvpView;

/**
 * @author Sergey Pinkevich
 */
public interface StepDetailView extends MvpView {

    void showVideo(String url);

    void showDescription(String description);
}
