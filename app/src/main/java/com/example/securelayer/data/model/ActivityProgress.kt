package com.example.securelayer.data.model

data class ActivityProgress (
    val id: Int,
    val activityId: Int,
    val userId: Int,
    val progress: Int,
    val isCompleted: Boolean
)
