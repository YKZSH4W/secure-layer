package com.example.securelayer.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.securelayer.data.model.Route
import com.example.securelayer.data.model.User

object SessionManager {
    var currentUser: User? by mutableStateOf(null)
}