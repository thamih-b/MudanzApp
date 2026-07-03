package com.example.appmudanza.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appmudanza.data.entity.AlquilerVehicle
import com.example.appmudanza.data.entity.MudanzaVehicle
import com.example.appmudanza.data.dao.AlquilerVehicleDao
import com.example.appmudanza.data.dao.IncidenciaDao
import com.example.appmudanza.data.dao.MoveDao
import com.example.appmudanza.data.dao.MudanzaDao
import com.example.appmudanza.data.dao.MudanzaVehicleDao
import com.example.appmudanza.data.dao.UserDao
import com.example.appmudanza.data.dao.VehicleDao
import com.example.appmudanza.data.entity.Incidencia
import com.example.appmudanza.data.entity.User
import com.example.appmudanza.data.entity.Vehicle
import com.example.appmudanza.data.entity.Move
import com.example.appmudanza.data.entity.Mudanza


@Database(
    entities = [User::class, MudanzaVehicle::class, AlquilerVehicle::class, Vehicle::class, Move::class, Mudanza::class, Incidencia::class],
    version = 7,  // Incrementar version para nuevas estructuras
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun mudanzaVehicleDao(): MudanzaVehicleDao
    abstract fun alquilerVehicleDao(): AlquilerVehicleDao

    abstract fun vehicleDao(): VehicleDao

    abstract fun moveDao(): MoveDao
    abstract  fun mudanzaDao(): MudanzaDao
    abstract fun incidenciaDao(): IncidenciaDao
}