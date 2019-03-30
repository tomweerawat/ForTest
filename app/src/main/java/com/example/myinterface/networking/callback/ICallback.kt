package com.example.myinterface.networking.callback

interface ICallback<T> {
    fun onSuccess(response: T?)
}
