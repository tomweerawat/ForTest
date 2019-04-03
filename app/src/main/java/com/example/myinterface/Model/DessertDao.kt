package com.example.myinterface.Model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DessertDao {

    @get:Query("SELECT * FROM dessert")
    val all: List<Dessert>

    @Insert
    fun insertAll(vararg users: Dessert)
}