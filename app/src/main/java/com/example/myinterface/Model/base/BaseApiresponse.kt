package com.example.myinterface.Model.base

import com.google.gson.annotations.SerializedName

class BaseApiresponse<T> {

    @SerializedName("collections")
    var list: List<T>? = null

    var hasMore: Long = 0
    var shareURL: String? = null
    var displayText: String? = null
    var hasTotal: Long = 0
}
