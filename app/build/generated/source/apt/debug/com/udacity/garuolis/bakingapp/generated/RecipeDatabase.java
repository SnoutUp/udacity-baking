package com.udacity.garuolis.bakingapp.generated;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.udacity.garuolis.bakingapp.provider.IngredientColumns;
import com.udacity.garuolis.bakingapp.provider.RecipeColumns;
import com.udacity.garuolis.bakingapp.provider.StepColumns;
import java.lang.Override;
import java.lang.String;

public class RecipeDatabase extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;

  public static final String RECIPES = "CREATE TABLE recipes ("
   + RecipeColumns._ID + " INTEGER PRIMARY KEY,"
   + RecipeColumns.NAME + " TEXT NOT NULL,"
   + RecipeColumns.IMAGE + " TEXT,"
   + RecipeColumns.SERVINGS + " INTEGER)";

  public static final String INGREDIENTS = "CREATE TABLE ingredients ("
   + IngredientColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
   + IngredientColumns.NAME + " TEXT NOT NULL,"
   + IngredientColumns.MEASURE + " TEXT NOT NULL,"
   + IngredientColumns.QUANTITY + " REAL,"
   + IngredientColumns.RECIPE_ID + " INTEGER REFERENCES recipes(_id))";

  public static final String STEPS = "CREATE TABLE steps ("
   + StepColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
   + StepColumns.NR + " INTEGER,"
   + StepColumns.DESCRIPTION + " TEXT,"
   + StepColumns.SHORT_DESCRIPTION + " TEXT,"
   + StepColumns.VIDEO_URL + " TEXT,"
   + StepColumns.THUMBNAIL_URL + " TEXT,"
   + StepColumns.RECIPE_ID + " INTEGER REFERENCES recipes(_id))";

  private static volatile RecipeDatabase instance;

  private Context context;

  private RecipeDatabase(Context context) {
    super(context.getApplicationContext(), "recipe.sqlite", null, DATABASE_VERSION);
    this.context = context.getApplicationContext();
  }

  public static RecipeDatabase getInstance(Context context) {
    if (instance == null) {
      synchronized (RecipeDatabase.class) {
        if (instance == null) {
          instance = new RecipeDatabase(context);
        }
      }
    }
    return instance;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(RECIPES);
    db.execSQL(INGREDIENTS);
    db.execSQL(STEPS);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
