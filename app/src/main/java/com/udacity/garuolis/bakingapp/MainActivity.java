package com.udacity.garuolis.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import com.udacity.garuolis.bakingapp.generated.Provider;
import com.udacity.garuolis.bakingapp.model.Ingredient;
import com.udacity.garuolis.bakingapp.model.Recipe;
import com.udacity.garuolis.bakingapp.model.Step;
import com.udacity.garuolis.bakingapp.provider.IngredientColumns;
import com.udacity.garuolis.bakingapp.provider.RecipeColumns;
import com.udacity.garuolis.bakingapp.provider.RecipeProvider;
import com.udacity.garuolis.bakingapp.provider.StepColumns;
import com.udacity.garuolis.bakingapp.utils.ApiUtils;
import com.udacity.garuolis.bakingapp.utils.RecipeApi;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements RecipeListAdapter.OnInteractionListener{

    RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int mColumnCount = getResources().getInteger(R.integer.column_count);
        RecyclerView rv = findViewById(R.id.list);
        rv.setLayoutManager(new GridLayoutManager(this, mColumnCount));

        Cursor cursor = getContentResolver().query(RecipeProvider.Recipes.RECIPES, null,null,null,null);
        mAdapter = new RecipeListAdapter(this, cursor, this);
        rv.setAdapter(mAdapter);

        loadRecipes();
    }

    protected void saveRecipeData(List<Recipe> recipes) {
        getContentResolver().delete(RecipeProvider.Recipes.RECIPES, null, null);
        getContentResolver().delete(RecipeProvider.Ingredients.INGREDIENTS, null, null);
        getContentResolver().delete(RecipeProvider.Steps.STEPS, null, null);

        for (Recipe r : recipes) {
            ContentValues v = new ContentValues();
            v.put(RecipeColumns._ID, r.id);
            v.put(RecipeColumns.NAME, r.name);
            v.put(RecipeColumns.IMAGE, r.image);
            v.put(RecipeColumns.SERVINGS, r.servings);
            getContentResolver().insert(RecipeProvider.Recipes.RECIPES, v);

            for (Ingredient i : r.ingredients) {
                ContentValues vi = new ContentValues();
                vi.put(IngredientColumns.NAME, i.name);
                vi.put(IngredientColumns.MEASURE, i.measure);
                vi.put(IngredientColumns.QUANTITY, i.quantity);
                vi.put(IngredientColumns.RECIPE_ID, r.id);
                getContentResolver().insert(RecipeProvider.Ingredients.INGREDIENTS, vi);
            }

            for (Step s : r.steps) {
                ContentValues vs = new ContentValues();
                vs.put(StepColumns.NR, s.nr);
                vs.put(StepColumns.DESCRIPTION, s.description);
                vs.put(StepColumns.SHORT_DESCRIPTION, s.shortDescription);
                vs.put(StepColumns.VIDEO_URL, s.videoURL);
                vs.put(StepColumns.THUMBNAIL_URL, s.thumbnailURL);

                vs.put(StepColumns.RECIPE_ID, r.id);
                getContentResolver().insert(RecipeProvider.Steps.STEPS, vs);
            }
        }
        Cursor cursor = getContentResolver().query(RecipeProvider.Recipes.RECIPES, null,null,null,null);
        mAdapter.updateCursor(cursor);
    }

    protected void loadRecipes() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUtils.API_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        RecipeApi api = retrofit.create(RecipeApi.class);

        Single <List<Recipe>> recipeSingle = api.getRecipeList().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        recipeSingle.subscribe(new SingleObserver<List<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<Recipe> recipes) {
                saveRecipeData(recipes);
            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }

    public void showRecipeActivity(int recipeId) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.EXTRA_ID, recipeId);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(int recipeId) {
        Log.v("mano", "recipe id clicked: " + recipeId);
        showRecipeActivity(recipeId);
    }
}
