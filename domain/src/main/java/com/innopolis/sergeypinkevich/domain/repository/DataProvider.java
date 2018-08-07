package com.innopolis.sergeypinkevich.domain.repository;

import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import java.util.List;

import io.reactivex.Single;

/**
 * @author Sergey Pinkevich
 */
public interface DataProvider {

    Single<List<Recipe>> getRecipesList();
}
