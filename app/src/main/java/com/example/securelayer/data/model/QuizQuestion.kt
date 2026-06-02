package com.example.securelayer.data.model

data class QuizQuestion(
    val question: String,
    val answers: List<String>,
    val rightAnswer: String
)