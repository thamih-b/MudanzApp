package com.example.appmudanza.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.MudanzaVehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LicenciaViewModel(app: Application) : AndroidViewModel(app) {

    // Lista observable de vehículos
    private val _vehicles = MutableStateFlow<List<MudanzaVehicle>>(emptyList())
    val vehicles: StateFlow<List<MudanzaVehicle>> = _vehicles

    fun loadMudanzas() {

        viewModelScope.launch {

            // Obtener instancia de la base de datos
            val db = DatabaseProvider.getDatabase(getApplication())

            // Obtener DAO
            val dao = db.mudanzaVehicleDao()

            // Obtener lista de camiones
            val list = dao.getAll()

            // Actualizar estado
            _vehicles.value = list
        }
    }
}


// StateFlow se usa para que la UI observe cambios
// Cuando vehicles cambia, Compose actualiza la pantalla