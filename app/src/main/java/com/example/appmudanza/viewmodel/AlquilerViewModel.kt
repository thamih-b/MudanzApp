package com.example.appmudanza.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.AlquilerVehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlquilerViewModel(application: Application) : AndroidViewModel(application) {
    private val _vehicles = MutableStateFlow<List<AlquilerVehicle>>(emptyList())
    val vehicles: StateFlow<List<AlquilerVehicle>> = _vehicles.asStateFlow()

    fun loadAlquileres() {  // Solo vehículos sin conductor
        viewModelScope.launch {
            val db = DatabaseProvider.getDatabase(getApplication())
            val list = db.alquilerVehicleDao().getAll()
            _vehicles.value = list
        }
    }
}