package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.Route
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class RoutesViewModel: ViewModel() {
    var routes by mutableStateOf<List<Route>>(emptyList())
        private set

    // Get routes
    fun getRoutesByUser() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRoutes()
                routes = response
            } catch (e: Exception) {
                Log.e("Routes", "Error: ${e.message}")
            }
        }
    }
}