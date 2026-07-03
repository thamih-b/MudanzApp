package com.example.appmudanza.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.appmudanza.data.entity.Mudanza
import kotlinx.coroutines.flow.Flow

@Dao
interface MudanzaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mudanza: Mudanza)

    @Query("SELECT * FROM mudanzas")
    fun getMudanzas(): Flow<List<Mudanza>>

    @Query("UPDATE mudanzas SET estado = 'cancelada' WHERE id = :id")
    suspend fun cancelar(id: Int)

    @Query("UPDATE mudanzas SET fecha = :nuevaFecha WHERE id = :id")
    suspend fun cambiarFecha(id: Int, nuevaFecha: Long)
    @Query("DELETE FROM mudanzas WHERE id = :id")
    suspend fun  delete (id: Int)
}