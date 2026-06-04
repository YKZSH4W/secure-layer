package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.model.UserAchievement
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class AchievementsViewModel : ViewModel() {
    var userAchievements by mutableStateOf<List<UserAchievement>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun load(userId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                userAchievements = RetrofitInstance.api.getUserAchievements(userId)
            } catch (e: Exception) {
                Log.e("Achievements", "Error al cargar medallas: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
