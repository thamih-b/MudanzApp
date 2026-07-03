package com.example.appmudanza.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val plate: String,
    val type: String,
    val capacity: Int,
    val driver: String = "",
    val licenseType: String = "",
    val withDriver: Boolean = false,
    val description: String = "",
    val valoration: Float = 0f
)

