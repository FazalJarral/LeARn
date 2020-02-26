package com.example.learn.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Format {
    @SerializedName("formatType")
    String formatType;
    @SerializedName("root")
    @Expose
    FormatRoot formatRoot;

    public Format() {
    }

    public String getFormatType() {
        return formatType;
    }

    public FormatRoot getFormatRoot() {
        return formatRoot;
    }

    @NonNull
    @Override
    public String toString() {
        return formatType + " " + formatRoot.getUrl();
    }
}
