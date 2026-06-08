package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.UserAchievement
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

    // Medallas obtenidas por el usuario (de la BD, con sus datos)
    var userAchievements by mutableStateOf<List<UserAchievement>>(emptyList())
        private set

    // Cantidad de lecciones completadas (todas sus actividades completas)
    var completedLessons by mutableIntStateOf(0)
        private set

    // Promedio de calificación global, considerando SOLO los exámenes finales
    var examAverage by mutableIntStateOf(0)
        private set

    fun loadStats(userId: Int?) {
        viewModelScope.launch {
            try {
                val allActivities = RetrofitInstance.api.getAllActivities()
                val progress = RetrofitInstance.api.getActivitiesProgressByUser(userId)
                val attempts = RetrofitInstance.api.getAttemptsByUser(userId)

                val total = allActivities.size
                val completedIds = progress.filter { it.isCompleted }.map { it.activityId }.toSet()
                val completed = completedIds.size

                courseProgress = if (total > 0) completed * 100 / total else 0
                attemptsCount = attempts.size
                accuracy = if (attempts.isNotEmpty()) {
                    attempts.map { it.score }.average().toInt()
                } else 0
                userAchievements = RetrofitInstance.api.getUserAchievements(userId)

                // Una lección está completa si TODAS sus actividades están completadas
                completedLessons = allActivities
                    .groupBy { it.lessonId }
                    .count { (_, acts) -> acts.isNotEmpty() && acts.all { it.id in completedIds } }

                // Promedio de calificación SOLO de los exámenes finales
                val examActivityIds = allActivities
                    .filter { it.name.trim().equals("Examen final", ignoreCase = true) }
                    .map { it.id }
                    .toSet()
                val examAttempts = attempts.filter { it.activityId in examActivityIds }
                examAverage = if (examAttempts.isNotEmpty()) {
                    examAttempts.map { it.score }.average().toInt()
                } else 0
            } catch (e: Exception) {
                Log.e("ProfileStats", "Error al cargar estadísticas: ${e.message}")
            }
        }
    }
}
