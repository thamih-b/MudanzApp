package com.example.appmudanza.data.database

import android.content.Context
import com.example.appmudanza.R
import com.example.appmudanza.data.entity.AlquilerVehicle
import com.example.appmudanza.data.entity.MudanzaVehicle
import com.example.appmudanza.data.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object InitialData {
    fun populateIfEmpty(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = DatabaseProvider.getDatabase(context)

            // Inserta vehículos de mudanza si tabla vacía
            if (db.mudanzaVehicleDao().getAll().isEmpty()) {
                val mudanzas = listOf(
                    MudanzaVehicle(
                        title = "Camión Grande + Conductor",
                        description = "Mudanza completa para casas/apartamentos grandes",
                        imageRes = R.drawable.ic_launcher_foreground,
                        rating = 4.8f,
                        pricePerHour = 45.0f,
                        maxLoad = "3 toneladas"
                    ),
                    MudanzaVehicle(
                        title = "Furgón Mediano + Conductor",
                        description = "Ideal para apartamentos medianos",
                        imageRes = R.drawable.ic_launcher_foreground,
                        rating = 4.2f,
                        pricePerHour = 35.0f,
                        maxLoad = "1.5 toneladas"
                    )
                )
                db.mudanzaVehicleDao().insertAll(mudanzas)
            }

            // Inserta vehículos de alquiler si tabla vacía
            if (db.alquilerVehicleDao().getAll().isEmpty()) {
                val alquileres = listOf(
                    AlquilerVehicle(
                        title = "Furgoneta Mediana",
                        description = "Furgoneta para mudanza pequeña sin conductor",
                        imageRes = R.drawable.ic_launcher_foreground,
                        rating = 4.5f,
                        pricePerDay = 65.0f,
                        seats = 2
                    ),
                    AlquilerVehicle(
                        title = "Camioneta Pequeña",
                        description = "Para transporte ligero sin conductor",
                        imageRes = R.drawable.ic_launcher_foreground,
                        rating = 4.0f,
                        pricePerDay = 45.0f,
                        seats = 3
                    )
                )
                db.alquilerVehicleDao().insertAll(alquileres)
            }
            if (db.userDao().getAllUsers().isEmpty()) {
                val usuariosPrueba = listOf(
                    User(name = "Usuario Test", email = "test@test.com", password = "123456"),
                    User(name = "Admin", email = "admin@admin.com", password = "admin")
                )
                usuariosPrueba.forEach { db.userDao().insert(it) }
            }
        }
    }
}
