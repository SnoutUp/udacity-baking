package com.udacity.garuolis.bakingapp.provider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = RecipeProvider.AUTHORITY, database = RecipeDatabase.class, packageName = "com.udacity.garuolis.bakingapp.generated", name = "Provider")
public class RecipeProvider {
    public final static String AUTHORITY = "com.udacity.garuolis.bakingapp.provider.RecipeProvider";

    @TableEndpoint(table = RecipeDatabase.RECIPES) public static class Recipes {
        @ContentUri(path = "recipes", type = "vnd.android.cursor.dir/recipe", defaultSort = RecipeColumns._ID + " ASC")
        public static final Uri RECIPES = Uri.parse("content://" + AUTHORITY + "/recipes");
    }

    @TableEndpoint(table = RecipeDatabase.INGREDIENTS) public static class Ingredients {
        @ContentUri(path = "ingredients", type = "vnd.android.cursor.dir/ingredient", defaultSort = IngredientColumns._ID + " ASC")
        public static final Uri INGREDIENTS = Uri.parse("content://" + AUTHORITY + "/ingredients");
    }

    @TableEndpoint(table = RecipeDatabase.STEPS) public static class Steps {
        @ContentUri(path = "steps", type = "vnd.android.cursor.dir/step", defaultSort = StepColumns._ID + " ASC")
        public static final Uri STEPS = Uri.parse("content://" + AUTHORITY + "/steps");
    }
}
