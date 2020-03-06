package com.example.learn.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ModelList {
    @SerializedName("assets")
    ArrayList<Asset> assets;

    public ArrayList<Asset> getAssets() {
        return assets;
    }
}
