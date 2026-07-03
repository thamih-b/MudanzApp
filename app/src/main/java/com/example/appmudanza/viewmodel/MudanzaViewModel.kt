package com.example.appmudanza.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.Mudanza
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MudanzaViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider.getDatabase(application).mudanzaDao()

    val mudanzas = dao.getMudanzas()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addMudanza(
        origen: String,
        destino: String,
        fecha: Long,
        withDriver: Boolean = false,
        driverName: String = ""
    ) {
        viewModelScope.launch {
            dao.insert(
                Mudanza(
                    origen = origen,
                    destino = destino,
                    fecha = fecha,
                    withDriver = withDriver,
                    driverName = driverName
                )
            )
        }
    }

    fun cancelarMudanza(id: Int) {
        viewModelScope.launch {
            dao.cancelar(id)
        }
    }

    fun cambiarFecha(id: Int, nuevaFecha: Long) {
        viewModelScope.launch {
            dao.cambiarFecha(id, nuevaFecha)
        }
    }

    fun deleteMudanza (id: Int) {
        viewModelScope.launch {
            dao.delete(id)
        }
    }
}