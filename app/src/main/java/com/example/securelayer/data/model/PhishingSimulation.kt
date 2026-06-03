package com.example.securelayer.data.model

data class PhishingSimulation(
    val id: Int,
    val activityId: Int,
    val typeMessage: String,
    val sender: String,
    val content: String,
    val isScam: Boolean
)
