package com.udacity.garuolis.bakingapp.provider;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;
import net.simonvt.schematic.annotation.TableEndpoint;

@Database(version = RecipeDatabase.VERSION, fileName = RecipeDatabase.FILE_NAME, packageName = "com.udacity.garuolis.bakingapp.generated")
public class RecipeDatabase {

    public static final int VERSION = 1;
    public static final String FILE_NAME = "recipe.sqlite";

    @Table(RecipeColumns.class) public static final String RECIPES = "recipes";
    @Table(IngredientColumns.class) public static final String INGREDIENTS = "ingredients";
    @Table(StepColumns.class) public static final String STEPS = "steps";
}
