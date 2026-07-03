package com.example.appmudanza.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appmudanza.data.entity.Move
import kotlinx.coroutines.flow.Flow

@Dao
interface MoveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(move: Move)

    @Query("SELECT * FROM moves")
    fun getMoves(): Flow<List<Move>>

    @Query("DELETE FROM moves")
    suspend fun deleteAll()
}