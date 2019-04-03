package com.example.myinterface.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.myinterface.Model.database.AppDatabase
import com.example.myinterface.ui.DessertListViewModel


class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DessertListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "dessert").build()
            @Suppress("UNCHECKED_CAST")
            return DessertListViewModel(db.dessertDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}