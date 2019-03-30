package com.example.myinterface.networking

import android.content.Context
import android.util.Log
import com.example.myinterface.Model.BaseApiresponse
import com.example.myinterface.Model.BaseCollectionResponse
import com.example.myinterface.Model.DessertDao
import com.example.myinterface.networking.callback.ICallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DessertService(val context: Context, retrofit: RetrofitHelper) : IHandler {


    private var dessertService: IDessertApi? = null


    init {
        this.dessertService = retrofit.create<IDessertApi>()
    }

    override fun get(callback: ICallback<BaseApiresponse<BaseCollectionResponse<DessertDao>>>) {
        val dao: List<DessertDao>
        dessertService!!.getList(1).enqueue(object : Callback<BaseApiresponse<BaseCollectionResponse<DessertDao>>?> {
            override fun onFailure(call: Call<BaseApiresponse<BaseCollectionResponse<DessertDao>>?>, t: Throwable) {
                Log.e("onError", "onError" + t.message)
            }

            override fun onResponse(
                call: Call<BaseApiresponse<BaseCollectionResponse<DessertDao>>?>,
                response: Response<BaseApiresponse<BaseCollectionResponse<DessertDao>>?>
            ) {
                val dao = response!!.body()
                callback.onSuccess(dao)
            }
        })
    }

    public fun loadData(callback: ICallback<BaseApiresponse<BaseCollectionResponse<DessertDao>>>) {
        val dao: List<DessertDao>
        dessertService!!.getList(1).enqueue(object : Callback<BaseApiresponse<BaseCollectionResponse<DessertDao>>?> {
            override fun onFailure(call: Call<BaseApiresponse<BaseCollectionResponse<DessertDao>>?>, t: Throwable) {
                Log.e("aaaaaa", "aaaaaa" + t.message)
            }

            override fun onResponse(
                call: Call<BaseApiresponse<BaseCollectionResponse<DessertDao>>?>,
                response: Response<BaseApiresponse<BaseCollectionResponse<DessertDao>>?>
            ) {
                val dao = response!!.body()
                Log.e("onresponse", "onresponse" + dao)
                callback.onSuccess(dao)
            }
        })
    }
}

interface IHandler {
    fun get(callback: ICallback<BaseApiresponse<BaseCollectionResponse<DessertDao>>>)

}

