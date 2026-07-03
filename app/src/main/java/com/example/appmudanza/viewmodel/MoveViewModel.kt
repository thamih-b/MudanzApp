package com.example.appmudanza.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.Move
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MoveViewModel(application: Application) : AndroidViewModel(application) {

    private val dao =
        DatabaseProvider.getDatabase(application).moveDao()

    val moves =
        dao.getMoves()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun addMove(
        origin: String,
        destination: String,
        date: String,
        client: String,
        vehicleId: Int
    ) {

        viewModelScope.launch {

            dao.insert(
                Move(
                    origin = origin,
                    destination = destination,
                    date = date,
                    client = client,
                    vehicleId = vehicleId
                )
            )
        }
    }
}