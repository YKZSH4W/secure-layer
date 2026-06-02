package com.example.securelayer.data.model

data class Attempt(
    val id: Int,
    val userId: Int,
    val activityId: Int,
    val attemptDate: String,
    val isCorrect: Boolean,
    val score: Int
)
