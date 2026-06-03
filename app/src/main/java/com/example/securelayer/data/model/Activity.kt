package com.example.securelayer.data.model

data class Activity (
    val id: Int,
    val lessonId: Int,
    val name: String,
    val description: String,
    val icon: String,
    val isCompleted: Boolean,
    val type: String?,
    val xp: Int,
    val category: String? = null
)
