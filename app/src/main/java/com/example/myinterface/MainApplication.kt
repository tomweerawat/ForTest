package com.example.myinterface

import android.app.Application
import com.example.myinterface.di.handlerModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin(this, listOf(handlerModule))
    }
}