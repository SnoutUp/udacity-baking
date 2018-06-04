package com.udacity.garuolis.bakingapp.model;


import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("ingredient")
    public String name;

    public String measure;
    public float quantity;
}
