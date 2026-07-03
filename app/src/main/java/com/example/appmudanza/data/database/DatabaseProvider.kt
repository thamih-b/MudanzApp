package com.example.appmudanza.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            Log.d("DB", "Construindo nova instância do banco")

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_mudanza"
            )
                .fallbackToDestructiveMigration(true)
                .build()

            Log.d("DB", "Banco construído")

            INSTANCE = instance
            instance
        }
    }
}