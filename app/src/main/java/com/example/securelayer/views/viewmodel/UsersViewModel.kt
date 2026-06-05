package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.ErrorResponse
import com.example.securelayer.data.model.KnowledgeLevelRequest
import com.example.securelayer.data.model.UserRegister
import com.example.securelayer.data.model.UserLogin
import com.example.securelayer.data.network.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.String

class UsersViewModel : ViewModel() {
    var loginError by mutableStateOf(false)
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var registerSuccess by mutableStateOf(false)
        private set

    var registerError by mutableStateOf(false)
        private set

    // Mensaje de error a mostrar al usuario (p. ej. correo ya registrado)
    var registerErrorMessage by mutableStateOf<String?>(null)
        private set

    // Create a user
    fun createUser(email: String, password: String, name: String) {
        viewModelScope.launch {
            try {
                val newUser = UserRegister(email, password, name)

                // Guarda el usuario creado en sesión para poder asociar el test inicial (nivel)
                SessionManager.currentUser = RetrofitInstance.api.createUser(newUser)

                registerSuccess = true
            } catch (e: HttpException) {
                // Errores con respuesta del servidor (p. ej. 409 correo repetido)
                val errorBody = e.response()?.errorBody()?.string()
                val parsed = try {
                    Gson().fromJson(errorBody, ErrorResponse::class.java)
                } catch (ex: Exception) {
                    null
                }

                registerErrorMessage = parsed?.message
                    ?: "No se pudo crear la cuenta. Intenta de nuevo."
                registerError = true
                Log.e("API", "Error ${e.code()}: $errorBody")
            } catch (e: Exception) {
                // Errores de red u otros
                registerErrorMessage = "Error de conexión. Intenta de nuevo."
                registerError = true
                Log.e("API", "Error: ${e.message}")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(UserLogin(email, password))
                SessionManager.currentUser = response
                loginSuccess = true
            } catch (e: Exception) {
                loginError = true
            }
        }
    }

    // Actualiza el nivel de conocimiento (resultado del test inicial) en BD y en sesión
    fun updateKnowledgeLevel(userId: Int?, level: String) {
        viewModelScope.launch {
            try {
                val updated = RetrofitInstance.api.updateKnowledgeLevel(
                    userId, KnowledgeLevelRequest(level)
                )
                SessionManager.currentUser = updated
            } catch (e: Exception) {
                Log.e("API", "Error al actualizar nivel: ${e.message}")
            }
        }
    }

    fun resetLoginError() {
        loginError = false
    }

    fun resetLoginSuccess() {
        loginSuccess = false
        SessionManager.currentUser = null
    }

    fun resetRegisterError() {
        registerError = false
        registerErrorMessage = null
    }

    fun resetRegisterSuccess() {
        registerSuccess = false
    }
}