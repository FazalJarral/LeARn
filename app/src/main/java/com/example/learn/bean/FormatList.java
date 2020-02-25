package com.example.learn.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FormatList {
    @SerializedName("formats")
List<Format> FormatList;

    public List<Format> getFormatList() {
        return FormatList;
    }
}
