package com.udacity.garuolis.bakingapp.generated.values;

import android.content.ContentValues;
import com.udacity.garuolis.bakingapp.provider.IngredientColumns;
import java.lang.Double;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

public class IngredientsValuesBuilder {
  ContentValues values = new ContentValues();

  public IngredientsValuesBuilder Id(Integer value) {
    values.put(IngredientColumns._ID, value);
    return this;
  }

  public IngredientsValuesBuilder Id(Long value) {
    values.put(IngredientColumns._ID, value);
    return this;
  }

  public IngredientsValuesBuilder name(String value) {
    values.put(IngredientColumns.NAME, value);
    return this;
  }

  public IngredientsValuesBuilder measure(String value) {
    values.put(IngredientColumns.MEASURE, value);
    return this;
  }

  public IngredientsValuesBuilder quantity(Float value) {
    values.put(IngredientColumns.QUANTITY, value);
    return this;
  }

  public IngredientsValuesBuilder quantity(Double value) {
    values.put(IngredientColumns.QUANTITY, value);
    return this;
  }

  public IngredientsValuesBuilder recipeId(Integer value) {
    values.put(IngredientColumns.RECIPE_ID, value);
    return this;
  }

  public IngredientsValuesBuilder recipeId(Long value) {
    values.put(IngredientColumns.RECIPE_ID, value);
    return this;
  }

  public ContentValues values() {
    return values;
  }
}
