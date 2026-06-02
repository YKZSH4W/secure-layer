package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.advices.Advice
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class AdvicesViewModel: ViewModel() {
    var advicesByRoute by mutableStateOf<List<Advice>>(emptyList())
        private set

    // Get routes
    fun getAdvicesByRoute(routeId: Int?) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAdvicesByRoute(routeId)
                advicesByRoute = response
            } catch (e: Exception) {
                Log.e("Advices", "Error: ${e.message}")
            }
        }
    }
}