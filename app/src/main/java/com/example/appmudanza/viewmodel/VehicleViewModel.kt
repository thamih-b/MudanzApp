package com.example.appmudanza.viewmodel

import android.app.Application
import android.provider.Telephony
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.Vehicle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class VehicleViewModel(application: Application) : AndroidViewModel(application) {

    private val dao =
        DatabaseProvider.getDatabase(application).vehicleDao()

    val vehicles =
        dao.getVehicles()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun addVehicle(
        plate: String,
        type: String,
        capacity: Int,
        driver: String = "",
        licenseType: String = "",
        withDriver: Boolean = false,
        description: String = "",
        valoration: Float = 0f


    ) {
        viewModelScope.launch {
            dao.insert(
                Vehicle(plate = plate, type = type, capacity = capacity, driver = driver, licenseType = licenseType, withDriver = withDriver, description = description, valoration = valoration)
            )
        }
    }

        }

