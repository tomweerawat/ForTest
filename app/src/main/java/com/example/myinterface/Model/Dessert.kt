package com.example.myinterface.Model

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Dessert (

    @SerializedName("collection_id")
    @Expose
    var collectionId: Int? = null,
    @SerializedName("res_count")
    @Expose
    var resCount: Int? = null,
    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null,
    @SerializedName("url")
    @Expose
    var url: String? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("share_url")
    @Expose
    var shareUrl: String? = null
)

