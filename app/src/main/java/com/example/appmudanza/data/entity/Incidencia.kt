package com.example.appmudanza.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incidencias")
data class Incidencia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mudanzaId: Int,
    val tipo: String,
    val origen: String,
    val destino: String,
    val fecha: Long
)