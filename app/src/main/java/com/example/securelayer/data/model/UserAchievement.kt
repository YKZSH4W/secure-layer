package com.example.securelayer.data.model

// Medalla ganada por un usuario (de usersachievements, con la medalla anidada)
data class UserAchievement(
    val id: Int,
    val userId: Int,
    val achievementId: Int,
    val dateAchieved: String,
    val achievement: Achievement
)
