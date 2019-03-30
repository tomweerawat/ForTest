package com.example.myinterface.networking

import com.example.myinterface.Model.BaseApiresponse
import com.example.myinterface.Model.BaseCollectionResponse
import com.example.myinterface.Model.DessertDao
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface IDessertApi {

    @Headers("user-key: 7cd5f10c66378443bb51b7346136680c")
    @POST("api/v2.1/collections")
    fun getList(@Query("city_id")city_id: Int): Call<BaseApiresponse<BaseCollectionResponse<DessertDao>>>

}