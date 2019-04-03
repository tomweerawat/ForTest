package com.example.myinterface.Model.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myinterface.Model.Dessert
import com.example.myinterface.Model.DessertDao


@Database(entities = [Dessert::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dessertDao(): DessertDao
}