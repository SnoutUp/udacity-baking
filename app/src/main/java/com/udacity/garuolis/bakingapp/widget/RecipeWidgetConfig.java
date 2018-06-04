package com.udacity.garuolis.bakingapp.widget;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.udacity.garuolis.bakingapp.MainActivity;
import com.udacity.garuolis.bakingapp.R;
import com.udacity.garuolis.bakingapp.RecipeListAdapter;
import com.udacity.garuolis.bakingapp.provider.IngredientColumns;
import com.udacity.garuolis.bakingapp.provider.RecipeColumns;
import com.udacity.garuolis.bakingapp.provider.RecipeProvider;

import java.util.List;

public class RecipeWidgetConfig extends MainActivity implements RecipeListAdapter.OnInteractionListener {
    private int mAppWidgetId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.title_activity_recipe_widget_config);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_CANCELED, resultValue);
    }

    @Override
    public void onItemSelected(int recipeId) {
        createWidgetForRecipe(recipeId);
    }

    public void createWidgetForRecipe(int recipeId) {
        saveWidgetData(recipeId);

        Intent intent = new Intent(this, RecipeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ComponentName name = new ComponentName(getApplication(), RecipeWidgetProvider.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(name));
        sendBroadcast(intent);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    public void saveWidgetData(int recipeId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();

        Cursor recipeCursor = getContentResolver().query(RecipeProvider.Recipes.RECIPES, null, RecipeColumns._ID + " = " + recipeId, null, null);
        if (recipeCursor.getCount() > 0) {
            recipeCursor.moveToFirst();
            String title = recipeCursor.getString(recipeCursor.getColumnIndex(RecipeColumns.NAME));
            edit.putString(RecipeWidgetProvider.KEY_RECIPE_TITLE + mAppWidgetId, title);

            String ingredients = "";

            Cursor inCursor = getContentResolver().query(RecipeProvider.Ingredients.INGREDIENTS, null, IngredientColumns.RECIPE_ID + " = " + recipeId, null, null);
            while (inCursor.moveToNext()) {
                String ingredient   = inCursor.getString(inCursor.getColumnIndex(IngredientColumns.NAME));
                String measure      = inCursor.getString(inCursor.getColumnIndex(IngredientColumns.MEASURE));
                float quantity      = inCursor.getFloat(inCursor.getColumnIndex(IngredientColumns.QUANTITY));

                ingredients += quantity + " " + measure + " " + ingredient + "\n";
            }

            edit.putString(RecipeWidgetProvider.KEY_INGREDIENTS + mAppWidgetId, ingredients);
        }


        edit.putInt(RecipeWidgetProvider.KEY_RECIPE_ID + mAppWidgetId, recipeId).commit();
    }
}
