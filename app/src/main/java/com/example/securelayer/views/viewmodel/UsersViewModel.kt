package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.UserRegister
import com.example.securelayer.data.model.UserLogin
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch
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

    // Create a user
    fun createUser(email: String, username: String, password: String, name: String,
                   lastName: String, birthDate: String) {
        viewModelScope.launch {
            try {
                val newUser = UserRegister(email, username, password, name, lastName,
                    birthDate)

                RetrofitInstance.api.createUser(newUser)

                registerSuccess = true
            } catch (e: Exception) {
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

    fun resetLoginError() {
        loginError = false
    }

    fun resetLoginSuccess() {
        loginSuccess = false
        SessionManager.currentUser = null
    }

    fun resetRegisterError() {
        registerError = false
    }

    fun resetRegisterSuccess() {
        registerSuccess = false
    }
}