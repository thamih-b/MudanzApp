package com.example.appmudanza.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmudanza.data.entity.AlquilerVehicle

@Dao
interface AlquilerVehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(alquileres: List<AlquilerVehicle>)

    @Query("SELECT * FROM alquiler_vehicles ORDER BY rating DESC")
    suspend fun getAll(): List<AlquilerVehicle>
}
