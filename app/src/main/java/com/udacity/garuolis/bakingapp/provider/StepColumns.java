package com.udacity.garuolis.bakingapp.provider;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

public interface StepColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
    @DataType(DataType.Type.INTEGER) String NR = "nr";
    @DataType(DataType.Type.TEXT) String DESCRIPTION = "description";
    @DataType(DataType.Type.TEXT) String SHORT_DESCRIPTION = "shortDescription";
    @DataType(DataType.Type.TEXT) String VIDEO_URL = "video";
    @DataType(DataType.Type.TEXT) String THUMBNAIL_URL = "thumbnail";

    @DataType(DataType.Type.INTEGER) @References(table = RecipeDatabase.RECIPES, column = RecipeColumns._ID) String RECIPE_ID = "recipeId";
}
