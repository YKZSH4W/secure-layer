package com.example.securelayer.data.model

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val totalXp: Int,
    val streak: Int,
    val knowledgeLevel: String,
    val profilePicture: String? = null
)