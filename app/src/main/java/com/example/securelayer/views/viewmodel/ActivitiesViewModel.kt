package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.Activity
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class ActivitiesViewModel : ViewModel() {
    var activities by mutableStateOf<List<Activity>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun getActivitiesByLesson(lessonId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.getActivitiesByLesson(lessonId)
                activities = response
            } catch (e: Exception) {
                Log.e("Activities", "Error al obtener actividades: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
