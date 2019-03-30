package com.example.myinterface.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseCollectionResponse<T> {

    @SerializedName("collection")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
