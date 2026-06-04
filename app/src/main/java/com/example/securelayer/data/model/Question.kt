package com.example.securelayer.data.model

data class Question (
    val id: Int,
    val activityId: Int,
    val questionText: String,
    val explanation: String,
    val type: String,
    val questionOrder: Int,
    val options: List<Option>
)
