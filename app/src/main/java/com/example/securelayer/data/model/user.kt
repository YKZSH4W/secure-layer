package com.example.securelayer.data.model

data class Usuario(
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val name: String,
    val lastName: String,
    val birthDate: String,
    val totalXp: Int,
    val lastaccessed: String,
    val streak: Int,
    val knowledgeLevel: String
)