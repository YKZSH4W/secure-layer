package com.example.securelayer.data.model

data class CompleteRouteResult(
    val completedRouteId: Int,
    val nextRoute: Route?,
    val enrolledNext: Boolean
)
