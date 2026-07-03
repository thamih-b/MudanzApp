package com.example.appmudanza.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moves")
data class Move(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val origin: String,

    val destination: String,

    val date: String,

    val client: String,

    val vehicleId: Int
)