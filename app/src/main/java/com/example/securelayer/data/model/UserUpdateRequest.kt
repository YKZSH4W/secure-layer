package com.example.securelayer.data.model

data class UserUpdateRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val profilePicture: String?
)
