package com.example.appmudanza.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String,
    val licenciaCategoria: String? = null // A, B, C para verificación
    // // Categoria de licencia: A=motos, B=coches ligeros, C=pesados
)



