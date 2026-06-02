package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.Activity
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class ActivitiesViewModel : ViewModel() {
    var activities by mutableStateOf<List<Activity>>(emptyList())
        private set

    var completedActivityIds by mutableStateOf<Set<Int>>(emptySet())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun getActivitiesByLesson(lessonId: Int?, userId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                activities = RetrofitInstance.api.getActivitiesByLesson(lessonId)
                    .sortedBy { it.id }

                val progress = RetrofitInstance.api.getActivitiesProgressByUser(userId)
                completedActivityIds = progress
                    .filter { it.isCompleted }
                    .map { it.activityId }
                    .toSet()

                SessionManager.currentLessonActivities = activities
                SessionManager.currentLessonCompletedIds = completedActivityIds
            } catch (e: Exception) {
                Log.e("Activities", "Error al obtener actividades: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun isCompleted(activityId: Int): Boolean = activityId in completedActivityIds
}
