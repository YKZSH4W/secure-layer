package com.example.securelayer.data.network

import com.example.securelayer.data.model.Route
import com.example.securelayer.data.model.UserRegister
import com.example.securelayer.data.model.User
import com.example.securelayer.data.model.UserLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/users")
    suspend fun createUser(@Body user: UserRegister): User

    @POST("/users/auth/login")
    suspend fun login(@Body user: UserLogin): User

    @GET("/routes")
    suspend fun getRoutes(): List<Route>

    @GET("/enrolls/user/{userId}")
    suspend fun getEnrollsByUser(@Path("userId") userId: Int?): List<Route>
}