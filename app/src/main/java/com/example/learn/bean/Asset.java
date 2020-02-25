package com.example.learn.bean;

import androidx.annotation.NonNull;

public class Asset {
    String displayName;
    Thumbnail thumbnail;

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

    @NonNull
    @Override
    public String toString() {
        return displayName + "" + thumbnail.getUrl();
    }
}
