package com.example.myinterface.di

import com.example.myinterface.networking.DessertService
import com.example.myinterface.networking.IHandler
import org.koin.dsl.module.module

var handlerModule = module {

    factory<IHandler> { DessertService(get(),get()) }

}