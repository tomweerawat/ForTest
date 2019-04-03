package com.example.myinterface.base


//import com.example.myinterface.di.component.ViewModelInjector
import androidx.lifecycle.ViewModel
import com.example.myinterface.di.component.ViewModelInjector
import com.example.myinterface.di.module.NetworkModule
import com.example.myinterface.ui.DessertListViewModel
import com.example.myinterface.ui.DessertViewModel


abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is DessertListViewModel -> injector.inject(this)
            is DessertViewModel -> injector.inject(this)
        }
    }

}