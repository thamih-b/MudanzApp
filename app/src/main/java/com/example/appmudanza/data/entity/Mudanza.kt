package com.example.appmudanza.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mudanzas")
data class Mudanza(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val origen: String,
    val destino: String,
    val fecha: Long, // fecha en milisegundos
    val withDriver: Boolean = false,
    val driverName: String = "",
    val estado: String = "activa" // activa o cancelada
)