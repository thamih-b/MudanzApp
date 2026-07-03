package com.example.appmudanza.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mudanza_vehicles")  // Tabla separada: vehículos con conductor
data class MudanzaVehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,           // Ej: "Camión grande + conductor"
    val description: String,     // Descripción completa
    val imageRes: Int,           // Drawable ID
    val rating: Float,           // Valoración 0-5
    val pricePerHour: Float,     // Precio por hora (€)
    val maxLoad: String          // Carga máxima (ej: "2 toneladas")
)
