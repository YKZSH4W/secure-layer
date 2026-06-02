package com.example.securelayer.data.model

data class Question (
    val id: Int,
    val activityId: Int,
    val questionText: String,
    val correctAnswer: String,
    val explanation: String,
    val type: String,
    val order: Int,
    val support: String?,
    val options: List<Option>
)
