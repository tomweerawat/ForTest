package com.example.myinterface.networking

import android.content.Context
import android.util.Log
import com.example.myinterface.Model.base.BaseApiresponse
import com.example.myinterface.Model.base.BaseCollectionResponse
import com.example.myinterface.Model.Dessert
import com.example.myinterface.networking.callback.ICallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DessertService(val context: Context, retrofit: RetrofitHelper) : IHandler {


    private var dessertService: IDessertApi? = null


    init {
        this.dessertService = retrofit.create<IDessertApi>()
    }

    override fun get(callback: ICallback<BaseApiresponse<BaseCollectionResponse<Dessert>>>) {
        val dao: List<Dessert>
        dessertService!!.getList(1).enqueue(object : Callback<BaseApiresponse<BaseCollectionResponse<Dessert>>?> {
            override fun onFailure(call: Call<BaseApiresponse<BaseCollectionResponse<Dessert>>?>, t: Throwable) {
                Log.e("onError", "onError" + t.message)
            }

            override fun onResponse(
                call: Call<BaseApiresponse<BaseCollectionResponse<Dessert>>?>,
                response: Response<BaseApiresponse<BaseCollectionResponse<Dessert>>?>
            ) {
                val dao = response!!.body()
                callback.onSuccess(dao)
            }
        })
    }

    public fun loadData(callback: ICallback<BaseApiresponse<BaseCollectionResponse<Dessert>>>) {
        val dao: List<Dessert>
        dessertService!!.getList(1).enqueue(object : Callback<BaseApiresponse<BaseCollectionResponse<Dessert>>?> {
            override fun onFailure(call: Call<BaseApiresponse<BaseCollectionResponse<Dessert>>?>, t: Throwable) {
                Log.e("aaaaaa", "aaaaaa" + t.message)
            }

            override fun onResponse(
                call: Call<BaseApiresponse<BaseCollectionResponse<Dessert>>?>,
                response: Response<BaseApiresponse<BaseCollectionResponse<Dessert>>?>
            ) {
                val dao = response!!.body()
                Log.e("onresponse", "onresponse" + dao)
                callback.onSuccess(dao)
            }
        })
    }
}

interface IHandler {
    fun get(callback: ICallback<BaseApiresponse<BaseCollectionResponse<Dessert>>>)

}

