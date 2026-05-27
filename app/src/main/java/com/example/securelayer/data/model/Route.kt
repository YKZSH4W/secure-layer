package com.example.securelayer.data.model

data class Route (
    val id: Int,
    val level: String,
    val name: String,
    val difficulty: String,
    val description: String,
    val isCompleted: Boolean
)