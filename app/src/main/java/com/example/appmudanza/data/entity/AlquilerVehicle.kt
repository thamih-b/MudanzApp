package com.example.appmudanza.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alquiler_vehicles")  // Tabla separada: vehículos sin conductor
data class AlquilerVehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,           // Ej: "Furgoneta mediana"
    val description: String,
    val imageRes: Int,
    val rating: Float,
    val pricePerDay: Float,      // Precio por día (€)
    val seats: Int               // Plazas disponibles
)
