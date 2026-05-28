package com.example.securelayer.data.network

import com.example.securelayer.data.model.Route
import com.example.securelayer.data.model.RouteUser
import com.example.securelayer.data.model.UserRegister
import com.example.securelayer.data.model.User
import com.example.securelayer.data.model.UserLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): List<User>

    @POST("/users")
    suspend fun createUser(@Body user: UserRegister): User

    @POST("/users/auth/login")
    suspend fun login(@Body user: UserLogin): User

    @GET("/routes")
    suspend fun getRoutes(): List<Route>
}