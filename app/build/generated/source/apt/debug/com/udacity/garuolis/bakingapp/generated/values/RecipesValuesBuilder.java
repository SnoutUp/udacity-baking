package com.udacity.garuolis.bakingapp.generated.values;

import android.content.ContentValues;
import com.udacity.garuolis.bakingapp.provider.RecipeColumns;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

public class RecipesValuesBuilder {
  ContentValues values = new ContentValues();

  public RecipesValuesBuilder Id(Integer value) {
    values.put(RecipeColumns._ID, value);
    return this;
  }

  public RecipesValuesBuilder Id(Long value) {
    values.put(RecipeColumns._ID, value);
    return this;
  }

  public RecipesValuesBuilder name(String value) {
    values.put(RecipeColumns.NAME, value);
    return this;
  }

  public RecipesValuesBuilder image(String value) {
    values.put(RecipeColumns.IMAGE, value);
    return this;
  }

  public RecipesValuesBuilder servings(Integer value) {
    values.put(RecipeColumns.SERVINGS, value);
    return this;
  }

  public RecipesValuesBuilder servings(Long value) {
    values.put(RecipeColumns.SERVINGS, value);
    return this;
  }

  public ContentValues values() {
    return values;
  }
}
