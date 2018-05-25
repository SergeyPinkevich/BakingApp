package com.innopolis.sergeypinkevich.domain.repository;

import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import java.util.List;

/**
 * @author Sergey Pinkevich
 */
public interface DataProvider {

    List<Recipe> getRecipesList();
}
