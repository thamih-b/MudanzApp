package com.example.appmudanza.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.Incidencia
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IncidenciaViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider.getDatabase(application).incidenciaDao()

    val incidencias = dao.getIncidencias()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addIncidencia(
        mudanzaId: Int,
        tipo: String,
        origen: String,
        destino: String,
        fecha: Long
    ) {
        viewModelScope.launch {
            dao.insert(
                Incidencia(
                    mudanzaId = mudanzaId,
                    tipo = tipo,
                    origen = origen,
                    destino = destino,
                    fecha = fecha
                )
            )
        }
    }

    fun deleteIncidencia(id: Int) {
        viewModelScope.launch {
            dao.delete(id)
        }
    }
}