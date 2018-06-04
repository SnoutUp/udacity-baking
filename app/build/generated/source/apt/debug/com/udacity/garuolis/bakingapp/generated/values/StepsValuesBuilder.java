package com.udacity.garuolis.bakingapp.generated.values;

import android.content.ContentValues;
import com.udacity.garuolis.bakingapp.provider.StepColumns;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;

public class StepsValuesBuilder {
  ContentValues values = new ContentValues();

  public StepsValuesBuilder Id(Integer value) {
    values.put(StepColumns._ID, value);
    return this;
  }

  public StepsValuesBuilder Id(Long value) {
    values.put(StepColumns._ID, value);
    return this;
  }

  public StepsValuesBuilder nr(Integer value) {
    values.put(StepColumns.NR, value);
    return this;
  }

  public StepsValuesBuilder nr(Long value) {
    values.put(StepColumns.NR, value);
    return this;
  }

  public StepsValuesBuilder description(String value) {
    values.put(StepColumns.DESCRIPTION, value);
    return this;
  }

  public StepsValuesBuilder shortDescription(String value) {
    values.put(StepColumns.SHORT_DESCRIPTION, value);
    return this;
  }

  public StepsValuesBuilder videoUrl(String value) {
    values.put(StepColumns.VIDEO_URL, value);
    return this;
  }

  public StepsValuesBuilder thumbnailUrl(String value) {
    values.put(StepColumns.THUMBNAIL_URL, value);
    return this;
  }

  public StepsValuesBuilder recipeId(Integer value) {
    values.put(StepColumns.RECIPE_ID, value);
    return this;
  }

  public StepsValuesBuilder recipeId(Long value) {
    values.put(StepColumns.RECIPE_ID, value);
    return this;
  }

  public ContentValues values() {
    return values;
  }
}
