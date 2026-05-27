package com.example.securelayer.data.network

import com.example.securelayer.data.model.Usuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// ApiService.kt
interface ApiService {
    @GET("/users")
    suspend fun getUsers(): List<Usuario>

    @POST("/users")
    suspend fun createUser(@Body usuario: Usuario): Usuario
}