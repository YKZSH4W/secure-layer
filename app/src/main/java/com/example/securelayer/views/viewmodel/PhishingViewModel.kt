package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.AttemptRequest
import com.example.securelayer.data.model.CompleteActivityRequest
import com.example.securelayer.data.model.PhishingSimulation
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class PhishingViewModel : ViewModel() {
    var simulations by mutableStateOf<List<PhishingSimulation>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadSimulations(activityId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                simulations = RetrofitInstance.api.getPhishingSimulationsByActivity(activityId)
            } catch (e: Exception) {
                Log.e("Phishing", "Error al cargar simulaciones: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    // Persiste en el backend que la actividad fue completada (la XP en sesión
    // ya se sumó localmente en el momento del envío).
    fun markActivityCompleted(userId: Int?, activityId: Int?) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.completeActivity(
                    CompleteActivityRequest(userId, activityId)
                )
            } catch (e: Exception) {
                Log.e("Phishing", "Error al marcar actividad: ${e.message}")
            }
        }
    }

    // Registra el intento en el historial
    fun registerAttempt(userId: Int?, activityId: Int?, isCorrect: Boolean, score: Int) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.createAttempt(
                    AttemptRequest(userId, activityId, isCorrect, score)
                )
            } catch (e: Exception) {
                Log.e("Phishing", "Error al registrar intento: ${e.message}")
            }
        }
    }
}
