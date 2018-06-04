package com.udacity.garuolis.bakingapp.provider;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

public interface IngredientColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull String NAME  = "name";
    @DataType(DataType.Type.TEXT) @NotNull String MEASURE  = "measure";
    @DataType(DataType.Type.REAL) String QUANTITY  = "quantity";
    @DataType(DataType.Type.INTEGER) @References(table = RecipeDatabase.RECIPES, column = RecipeColumns._ID) String RECIPE_ID = "recipeId";
}
