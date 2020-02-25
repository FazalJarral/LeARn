package com.example.learn.bean;

import com.google.gson.annotations.SerializedName;

public class Format {
    @SerializedName("formatType")
    String formatType;
    FormatRoot formatRoot;

    public Format() {
    }

    public String getFormatType() {
        return formatType;
    }

    public FormatRoot getFormatRoot() {
        return formatRoot;
    }
}
