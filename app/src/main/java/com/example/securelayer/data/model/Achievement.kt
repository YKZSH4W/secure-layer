package com.example.securelayer.data.model

// Definición de una medalla (viene de la tabla Achievements)
data class Achievement(
    val id: Int,
    val name: String,
    val description: String,
    val icon: String,
    val requiredXp: Int
)
