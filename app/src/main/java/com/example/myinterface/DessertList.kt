package com.example.myinterface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.base.recyclerview.BaseList
import com.example.myinterface.Model.BaseApiresponse
import com.example.myinterface.Model.BaseCollectionResponse
import com.example.myinterface.Model.DessertDao
import com.example.myinterface.adapter.DessertItems
import com.example.myinterface.networking.DessertService
import com.example.myinterface.networking.RetrofitHelper
import com.example.myinterface.networking.callback.ICallback
import com.google.gson.GsonBuilder
import com.mikepenz.fastadapter.IAdapter
import java.util.ArrayList


class DessertList: BaseList<DessertItems>() {

    var service: DessertService? = null

    val mydata : MutableList<DessertDao> = ArrayList()

    companion object {
    fun newInstance(): DessertList {
        val args = Bundle()

        val fragment = DessertList()
        fragment.arguments = args
        return fragment
    }
}

    override fun onLoad(offset: Int, limit: Int, action: BaseListAction) {
        service = DessertService(this.context!!, RetrofitHelper("https://developers.zomato.com"))
        service?.loadData(object: ICallback<BaseApiresponse<BaseCollectionResponse<DessertDao>>> {
            override fun onSuccess(response: BaseApiresponse<BaseCollectionResponse<DessertDao>>?) {
                Log.d(
                    "mycallback",
                    "mycallback: " + GsonBuilder().setPrettyPrinting().create().toJson(response?.list)
                )
                    adddatatoView(response!!.list)
            }
        })
    }

    override fun search(query: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?, adapter: IAdapter<DessertItems>, item: DessertItems, position: Int): Boolean {
        val dessertDao = item.model
        Toast.makeText(getActivity(), "This is my Toast message!"+ GsonBuilder().setPrettyPrinting().create().toJson(dessertDao),
            Toast.LENGTH_LONG).show()
        return false
    }

    override fun firstItem() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun emptyView(header: TextView, detail: TextView, imageView: ImageView) {
        header.text = getText(R.string.abc_capital_on)
        detail.text = getText(R.string.abc_capital_on)
        Glide.with(this.context!!)
            .load(R.mipmap.ic_launcher)
            .into(imageView)
    }



    private fun adddatatoView(daos: List<BaseCollectionResponse<DessertDao>>) {
        val list: MutableList<DessertItems> = ArrayList()


//        val valid = daos?.let { this.add(DessertItems(daos)) } ?: false

        daos.forEach { f ->
            list.add(DessertItems(f.data))
        }
        this.add(list)
    }
}