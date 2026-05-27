package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.Usuario
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

// UsuariosViewModel.kt
class UsuariosViewModel : ViewModel() {
    var users by mutableStateOf<List<Usuario>>(emptyList())
        private set

    fun loadUsers() {
        viewModelScope.launch {
            try {
                users = RetrofitInstance.api.getUsers()
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }
}