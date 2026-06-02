package com.example.securelayer.data.model

data class CompleteActivityResult(
    val progress: ActivityProgress,
    val alreadyCompleted: Boolean,
    val xpAwarded: Int = 0,
    val totalXp: Int? = null
)
