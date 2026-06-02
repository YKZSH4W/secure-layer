package com.example.securelayer.data.model

data class QuizFeedbackItem(
    val question: String,
    val userAnswer: String,
    val correctAnswer: String,
    val isCorrect: Boolean,
    val explanation: String
)
