package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.Lesson
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class LessonsViewModel : ViewModel() {
    var lessons by mutableStateOf<List<Lesson>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun getLessonsByRoute(routeId: Int?, userId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                // Trae las lecciones con su estado de completado calculado por usuario
                val response = RetrofitInstance.api.getLessonsByRouteWithProgress(routeId, userId)
                lessons = response
            } catch (e: Exception) {
                Log.e("Lessons", "Error al obtener lecciones: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
