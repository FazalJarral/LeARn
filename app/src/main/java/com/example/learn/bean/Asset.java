package com.example.learn.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Asset {
    String displayName;
    Thumbnail thumbnail;
    @SerializedName("formats")
            @Expose
    List<Format> formatList;
    public Asset() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Format> getFormatList() {
        return formatList;
    }

    @NonNull
    @Override
    public String toString() {
        return displayName + "" + thumbnail.getUrl();
    }
}
