package com.example.appmudanza.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmudanza.data.entity.MudanzaVehicle

@Dao
interface MudanzaVehicleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mudanzas: List<MudanzaVehicle>)

    @Query("SELECT * FROM mudanza_vehicles ORDER BY rating DESC")
    suspend fun getAll(): List<MudanzaVehicle>

    // Filtra camiones según licencia del usuario
    @Query("SELECT * FROM mudanza_vehicles ORDER BY rating DESC")
    suspend fun getMudanzasPorLicencia(): List<MudanzaVehicle>

}

// DAO para acceder a la tabla de camiones de mudanza
// Room genera automaticamente la implementación de esta interfaz

// insertAll -> inserta una lista de camiones
// getAll -> devuelve todos los camiones disponibles
// getMudanzasPorLicencia -> después filtraremos según licencia del usuario



