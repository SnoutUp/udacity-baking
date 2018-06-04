package com.udacity.garuolis.bakingapp.utils;

import com.udacity.garuolis.bakingapp.model.Recipe;

import java.util.List;
import java.util.Observable;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApi {
    @GET("baking.json")
    Single<List<Recipe>> getRecipeList();
}
