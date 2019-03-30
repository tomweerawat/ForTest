package com.example.myinterface

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.base.recyclerview.BaseList
import com.example.myinterface.Model.BaseApiresponse
import com.example.myinterface.Model.BaseCollectionResponse
import com.example.myinterface.Model.DessertDao
import com.example.myinterface.adapter.DessertItems
import com.example.myinterface.networking.DessertService
import com.example.myinterface.networking.RetrofitHelper
import com.example.myinterface.networking.callback.ICallback
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    var service: DessertService? = null

    val mydata : MutableList<DessertDao> = ArrayList()

    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        findTelephoneNumber("PETE",object: ITelephoneCallback {
//            override fun onFinishFinding(phoneNumber: String) {
//                Toast.makeText(this@MainActivity, "This is my Toast message!"+phoneNumber,
//                    Toast.LENGTH_LONG).show()
//            }
//        })
//        service = DessertService(applicationContext, RetrofitHelper("https://developers.zomato.com"))
//        service?.loadData(object: ICallback<BaseApiresponse<BaseCollectionResponse<DessertDao>>> {
//            override fun onSuccess(response: BaseApiresponse<BaseCollectionResponse<DessertDao>>?) {
//                Log.d(
//                    "mycallback",
//                    "mycallback: " + GsonBuilder().setPrettyPrinting().create().toJson(response?.list)
//                )
//                val parse =  GsonBuilder().setPrettyPrinting().create().toJson(response?.list)
//                this@MainActivity.mydata.add(response?.list!![0].data)
//                this@MainActivity.addContact(mydata)
//            }
//        })

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment, DessertList.newInstance())
                .commit()
        }
    }

    private fun addContact(daos: List<DessertDao>) {

    }
    fun findTelephoneNumber(name: String, callback: ITelephoneCallback) {
        callback.onFinishFinding(name)
        callback.onFinishFinding(localClassName)
    }
}
