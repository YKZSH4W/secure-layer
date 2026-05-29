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


class EnrollsViewModel: ViewModel() {
    var currentRoutes by mutableStateOf<List<Route>>(emptyList())
        private set

    // Get user's enrolls
    fun getEnrollsByUser(userId: Int?) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEnrollsByUser(userId)
                currentRoutes = response
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }

}