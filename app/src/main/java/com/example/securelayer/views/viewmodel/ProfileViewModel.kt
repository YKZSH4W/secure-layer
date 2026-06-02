package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.ErrorResponse
import com.example.securelayer.data.model.UserUpdateRequest
import com.example.securelayer.data.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProfileViewModel : ViewModel() {
    var isSaving by mutableStateOf(false)
        private set

    var updateSuccess by mutableStateOf(false)
        private set

    // Mensaje de error a mostrar (p. ej. correo ya registrado)
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun updateProfile(
        userId: Int?,
        name: String,
        lastName: String,
        email: String,
        profilePicture: String?
    ) {
        viewModelScope.launch {
            isSaving = true
            errorMessage = null
            try {
                val updated = RetrofitInstance.api.updateUser(
                    userId,
                    UserUpdateRequest(name, lastName, email, profilePicture)
                )

                // Refleja los cambios en el usuario en sesión
                SessionManager.currentUser = updated
                updateSuccess = true
            } catch (e: HttpException) {
                val body = e.response()?.errorBody()?.string()
                val parsed = try {
                    Gson().fromJson(body, ErrorResponse::class.java)
                } catch (ex: Exception) {
                    null
                }
                errorMessage = parsed?.message ?: "No se pudieron guardar los cambios. Intenta de nuevo."
                Log.e("Profile", "Error ${e.code()}: $body")
            } catch (e: Exception) {
                errorMessage = "Error de conexión. Intenta de nuevo."
                Log.e("Profile", "Error: ${e.message}")
            } finally {
                isSaving = false
            }
        }
    }

    fun resetError() {
        errorMessage = null
    }
}
