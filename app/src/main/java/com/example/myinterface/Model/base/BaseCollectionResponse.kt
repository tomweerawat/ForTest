package com.example.myinterface.Model.base

import com.google.gson.annotations.SerializedName

class BaseCollectionResponse<T> {

    @SerializedName("collection")
    var data: T? = null
}
