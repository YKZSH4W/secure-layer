package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.CompleteRouteRequest
import com.example.securelayer.data.model.Route
import com.example.securelayer.data.network.RetrofitInstance

import kotlinx.coroutines.launch


class EnrollsViewModel: ViewModel() {
    var currentRoutes by mutableStateOf<List<Route>>(emptyList())
        private set

    fun getEnrollsByUser(userId: Int?) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEnrollsByUser(userId)
                currentRoutes = response

                SessionManager.currentRoute = currentRoutes.find { !it.isCompleted }
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }

    fun completeRouteAndAdvance(userId: Int?, routeId: Int?) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.completeRouteAndAdvance(
                    CompleteRouteRequest(userId, routeId)
                )

                // Si subió de nivel de conocimiento, lo refleja en el usuario en sesión
                result.newKnowledgeLevel?.let { level ->
                    SessionManager.currentUser =
                        SessionManager.currentUser?.copy(knowledgeLevel = level)
                }

                val response = RetrofitInstance.api.getEnrollsByUser(userId)
                currentRoutes = response
                SessionManager.currentRoute = currentRoutes.find { !it.isCompleted }

                SessionManager.currentLesson = null
                SessionManager.currentActivity = null
            } catch (e: Exception) {
                Log.e("Enrolls", "Error al avanzar de ruta: ${e.message}")
            }
        }
    }

}