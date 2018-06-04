package com.udacity.garuolis.bakingapp.provider;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public interface RecipeColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull String NAME  = "name";
    @DataType(DataType.Type.TEXT) String IMAGE          = "image";
    @DataType(DataType.Type.INTEGER) String SERVINGS    = "servings";
}
