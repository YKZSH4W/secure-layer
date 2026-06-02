package com.example.securelayer.data.model

data class Lesson (
    val id: Int,
    val routeId: Int,
    val name: String,
    val description: String,
    val icon: String,
    val isCompleted: Boolean
)