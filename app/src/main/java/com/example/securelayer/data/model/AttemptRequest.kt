package com.example.securelayer.data.model

data class AttemptRequest(
    val userId: Int?,
    val activityId: Int?,
    val isCorrect: Boolean,
    val score: Int
)
