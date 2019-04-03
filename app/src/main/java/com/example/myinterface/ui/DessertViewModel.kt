package com.example.myinterface.ui

import androidx.lifecycle.MutableLiveData
import com.example.myinterface.Model.Dessert
import com.example.myinterface.base.BaseViewModel

class DessertViewModel: BaseViewModel() {
    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: Dessert) {
        postTitle.value = post.title
        postBody.value = post.description
    }

    fun getPostTitle(): MutableLiveData<String> {
        return postTitle
    }

    fun getPostBody(): MutableLiveData<String> {
        return postBody
    }
}