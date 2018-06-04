package com.udacity.garuolis.bakingapp.generated;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import net.simonvt.schematic.utils.SelectionBuilder;

public class Provider extends ContentProvider {
  public static final String AUTHORITY = "com.udacity.garuolis.bakingapp.provider.RecipeProvider";

  private static final int RECIPES_RECIPES = 0;

  private static final int INGREDIENTS_INGREDIENTS = 1;

  private static final int STEPS_STEPS = 2;

  private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    MATCHER.addURI(AUTHORITY, "recipes", RECIPES_RECIPES);
    MATCHER.addURI(AUTHORITY, "ingredients", INGREDIENTS_INGREDIENTS);
    MATCHER.addURI(AUTHORITY, "steps", STEPS_STEPS);
  }

  private SQLiteOpenHelper database;

  @Override
  public boolean onCreate() {
    database = RecipeDatabase.getInstance(getContext());
    return true;
  }

  private SelectionBuilder getBuilder(String table) {
    SelectionBuilder builder = new SelectionBuilder();
    return builder;
  }

  private long[] insertValues(SQLiteDatabase db, String table, ContentValues[] values) {
    long[] ids = new long[values.length];
    for (int i = 0; i < values.length; i++) {
      ContentValues cv = values[i];
      db.insertOrThrow(table, null, cv);
    }
    return ids;
  }

  @Override
  public int bulkInsert(Uri uri, ContentValues[] values) {
    final SQLiteDatabase db = database.getWritableDatabase();
    db.beginTransaction();
    try {
      switch(MATCHER.match(uri)) {
        case RECIPES_RECIPES: {
          long[] ids = insertValues(db, "recipes", values);
          getContext().getContentResolver().notifyChange(uri, null);
          break;
        }
        case INGREDIENTS_INGREDIENTS: {
          long[] ids = insertValues(db, "ingredients", values);
          getContext().getContentResolver().notifyChange(uri, null);
          break;
        }
        case STEPS_STEPS: {
          long[] ids = insertValues(db, "steps", values);
          getContext().getContentResolver().notifyChange(uri, null);
          break;
        }
      }
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
    return values.length;
  }

  @Override
  public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> ops) throws
      OperationApplicationException {
    ContentProviderResult[] results;
    final SQLiteDatabase db = database.getWritableDatabase();
    db.beginTransaction();
    try {
      results = super.applyBatch(ops);
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }
    return results;
  }

  @Override
  public String getType(Uri uri) {
    switch(MATCHER.match(uri)) {
      case RECIPES_RECIPES: {
        return "vnd.android.cursor.dir/recipe";
      }
      case INGREDIENTS_INGREDIENTS: {
        return "vnd.android.cursor.dir/ingredient";
      }
      case STEPS_STEPS: {
        return "vnd.android.cursor.dir/step";
      }
      default: {
        throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
      String sortOrder) {
    final SQLiteDatabase db = database.getReadableDatabase();
    switch(MATCHER.match(uri)) {
      case RECIPES_RECIPES: {
        SelectionBuilder builder = getBuilder("Recipes");
        if (sortOrder == null) {
          sortOrder = "_id ASC";
        }
        String table = "recipes";
        final String groupBy = null;
        final String having = null;
        final String limit = null;
        Cursor cursor = builder.table(table)
            .where(selection, selectionArgs)
            .query(db, projection, groupBy, having, sortOrder, limit);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
      }
      case INGREDIENTS_INGREDIENTS: {
        SelectionBuilder builder = getBuilder("Ingredients");
        if (sortOrder == null) {
          sortOrder = "_id ASC";
        }
        String table = "ingredients";
        final String groupBy = null;
        final String having = null;
        final String limit = null;
        Cursor cursor = builder.table(table)
            .where(selection, selectionArgs)
            .query(db, projection, groupBy, having, sortOrder, limit);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
      }
      case STEPS_STEPS: {
        SelectionBuilder builder = getBuilder("Steps");
        if (sortOrder == null) {
          sortOrder = "_id ASC";
        }
        String table = "steps";
        final String groupBy = null;
        final String having = null;
        final String limit = null;
        Cursor cursor = builder.table(table)
            .where(selection, selectionArgs)
            .query(db, projection, groupBy, having, sortOrder, limit);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
      }
      default: {
        throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    final SQLiteDatabase db = database.getWritableDatabase();
    switch(MATCHER.match(uri)) {
      case RECIPES_RECIPES: {
        final long id = db.insertOrThrow("recipes", null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
      }
      case INGREDIENTS_INGREDIENTS: {
        final long id = db.insertOrThrow("ingredients", null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
      }
      case STEPS_STEPS: {
        final long id = db.insertOrThrow("steps", null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
      }
      default: {
        throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }

  @Override
  public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
    final SQLiteDatabase db = database.getWritableDatabase();
    switch(MATCHER.match(uri)) {
      case RECIPES_RECIPES: {
        SelectionBuilder builder = getBuilder("Recipes");
        builder.where(where, whereArgs);
        final int count = builder.table("recipes")
            .update(db, values);
        if (count > 0) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
      }
      case INGREDIENTS_INGREDIENTS: {
        SelectionBuilder builder = getBuilder("Ingredients");
        builder.where(where, whereArgs);
        final int count = builder.table("ingredients")
            .update(db, values);
        if (count > 0) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
      }
      case STEPS_STEPS: {
        SelectionBuilder builder = getBuilder("Steps");
        builder.where(where, whereArgs);
        final int count = builder.table("steps")
            .update(db, values);
        if (count > 0) {
          getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
      }
      default: {
        throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }

  @Override
  public int delete(Uri uri, String where, String[] whereArgs) {
    final SQLiteDatabase db = database.getWritableDatabase();
    switch(MATCHER.match(uri)) {
      case RECIPES_RECIPES: {
        SelectionBuilder builder = getBuilder("Recipes");
        builder.where(where, whereArgs);
        final int count = builder
            .table("recipes")
            .delete(db);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
      }
      case INGREDIENTS_INGREDIENTS: {
        SelectionBuilder builder = getBuilder("Ingredients");
        builder.where(where, whereArgs);
        final int count = builder
            .table("ingredients")
            .delete(db);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
      }
      case STEPS_STEPS: {
        SelectionBuilder builder = getBuilder("Steps");
        builder.where(where, whereArgs);
        final int count = builder
            .table("steps")
            .delete(db);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
      }
      default: {
        throw new IllegalArgumentException("Unknown URI " + uri);
      }
    }
  }
}
