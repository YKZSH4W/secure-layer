package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.ActivityHistory
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    var history by mutableStateOf<List<ActivityHistory>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadHistory(userId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                val attempts = RetrofitInstance.api.getAttemptsByUser(userId)
                val activitiesById = RetrofitInstance.api.getAllActivities().associateBy { it.id }

                // Una tarjeta por cada intento (más reciente primero)
                history = attempts
                    .sortedByDescending { it.attemptDate }
                    .map { attempt ->
                        val activity = activitiesById[attempt.activityId]
                        ActivityHistory(
                            activityName = activity?.name ?: "Actividad",
                            icon = activity?.icon ?: "",
                            date = attempt.attemptDate,
                            score = attempt.score
                        )
                    }
            } catch (e: Exception) {
                Log.e("History", "Error al cargar el historial: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
