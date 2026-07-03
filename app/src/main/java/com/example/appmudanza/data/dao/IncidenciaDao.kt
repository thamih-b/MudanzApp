package com.example.appmudanza.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmudanza.data.entity.Incidencia
import kotlinx.coroutines.flow.Flow

@Dao
interface IncidenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(incidencia: Incidencia)

    @Query("SELECT * FROM incidencias")
    fun getIncidencias(): Flow<List<Incidencia>>

    @Query("DELETE FROM incidencias WHERE id = :id")
    suspend fun delete(id: Int)
}