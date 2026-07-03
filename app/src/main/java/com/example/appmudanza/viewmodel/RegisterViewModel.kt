package com.example.appmudanza.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appmudanza.data.database.DatabaseProvider
import com.example.appmudanza.data.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val userDao = DatabaseProvider.getDatabase(application).userDao()

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerState.value = RegisterState.Loading
            try {
                // Verifica si el email ya existe
                val existingUser = userDao.getUserByEmail(email) // Assuma que existe este método no DAO
                if (existingUser != null) {
                    _registerState.value = RegisterState.Error("Email já cadastrado")
                } else {
                    val user = User(name = name, email = email, password = password) // Ajuste campos conforme sua entity
                    userDao.insertUser(user)
                    _registerState.value = RegisterState.Success
                }
            } catch (e: Exception) {
                _registerState.value = RegisterState.Error("Erro ao cadastrar: ${e.message}")
            }
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
