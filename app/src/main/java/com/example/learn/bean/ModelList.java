package com.example.learn.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelList {
    @SerializedName("assets")
    List<Asset> assets;

    public List<Asset> getAssets() {
        return assets;
    }
}
