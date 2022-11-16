package com.example.Module1.ApiClasses;

import com.google.gson.annotations.SerializedName;

public class SequreClass {
    @SerializedName("sequre") private String sequre;

    public String getSequre() {
        return sequre;
    }

    public void setSequre(String sequre) {
        this.sequre = sequre;
    }
}
