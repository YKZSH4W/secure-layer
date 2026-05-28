package com.example.securelayer.data.model

data class User(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val name: String,
    val lastName: String,
    val birthDate: String,
    val totalXp: Int,
    val streak: Int,
    val knowledgeLevel: String
)