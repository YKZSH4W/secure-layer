package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class ProfileStatsViewModel : ViewModel() {
    // Progreso del curso completo (% de actividades completadas del total que existe)
    var courseProgress by mutableIntStateOf(0)
        private set

    // Acierto global (promedio del puntaje de todos los intentos)
    var accuracy by mutableIntStateOf(0)
        private set

    // Cantidad de intentos realizados
    var attemptsCount by mutableIntStateOf(0)
        private set

    // Cantidad de medallas obtenidas
    var medalsCount by mutableIntStateOf(0)
        private set

    fun loadStats(userId: Int?) {
        viewModelScope.launch {
            try {
                val allActivities = RetrofitInstance.api.getAllActivities()
                val progress = RetrofitInstance.api.getActivitiesProgressByUser(userId)
                val attempts = RetrofitInstance.api.getAttemptsByUser(userId)
                val achievements = RetrofitInstance.api.getUserAchievements(userId)

                val total = allActivities.size
                val completed = progress.count { it.isCompleted }

                courseProgress = if (total > 0) completed * 100 / total else 0
                attemptsCount = attempts.size
                accuracy = if (attempts.isNotEmpty()) {
                    attempts.map { it.score }.average().toInt()
                } else 0
                medalsCount = achievements.size
            } catch (e: Exception) {
                Log.e("ProfileStats", "Error al cargar estadísticas: ${e.message}")
            }
        }
    }
}
