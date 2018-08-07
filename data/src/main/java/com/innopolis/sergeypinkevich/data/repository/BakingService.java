package com.innopolis.sergeypinkevich.data.repository;

import com.innopolis.sergeypinkevich.domain.entity.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface BakingService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Single<List<Recipe>> getRecipes();
}
