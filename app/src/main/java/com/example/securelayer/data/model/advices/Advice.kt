package com.example.securelayer.data.model.advices

data class Advice (
    val id: Int,
    val routeId: Int,
    val type: String,
    val adviceTitle: String,
    val adviceText: String
)