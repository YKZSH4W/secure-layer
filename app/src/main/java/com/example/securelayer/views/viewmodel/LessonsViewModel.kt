package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.Route
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class LessonsViewModel {
    var lessons by mutableStateOf<List<Route>>(emptyList())
        private set

    fun getLessonsByRoute(userId: Int?) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEnrollsByUser(userId)
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }
    }

}