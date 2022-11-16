package com.example.Module1.ApiClasses;

import com.google.gson.annotations.SerializedName;

public class TokenClass {
    @SerializedName("token") private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
