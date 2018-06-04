package com.udacity.garuolis.bakingapp.model;

import com.google.gson.annotations.SerializedName;

public class Step {
    @SerializedName("id")
    public int nr;

    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;
}
