package com.example.securelayer.data.network

import com.example.securelayer.data.model.Activity
import com.example.securelayer.data.model.ActivityProgress
import com.example.securelayer.data.model.Attempt
import com.example.securelayer.data.model.AttemptRequest
import com.example.securelayer.data.model.CompleteActivityRequest
import com.example.securelayer.data.model.CompleteActivityResult
import com.example.securelayer.data.model.CompleteRouteRequest
import com.example.securelayer.data.model.CompleteRouteResult
import com.example.securelayer.data.model.Lesson
import com.example.securelayer.data.model.Option
import com.example.securelayer.data.model.Question
import com.example.securelayer.data.model.Route
import com.example.securelayer.data.model.UserRegister
import com.example.securelayer.data.model.User
import com.example.securelayer.data.model.UserLogin
import com.example.securelayer.data.model.advices.Advice
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/users/auth/register")
    suspend fun createUser(@Body user: UserRegister): User

    @POST("/users/auth/login")
    suspend fun login(@Body user: UserLogin): User

    @GET("/routes")
    suspend fun getRoutes(): List<Route>

    @GET("/enrolls/user/{userId}")
    suspend fun getEnrollsByUser(@Path("userId") userId: Int?): List<Route>

    @POST("/enrolls/complete-and-advance")
    suspend fun completeRouteAndAdvance(@Body body: CompleteRouteRequest): CompleteRouteResult

    @GET("/routes-advices")
    suspend fun getAdvices(): List<Advice>

    @GET("/routes-advices/route/{routeId}")
    suspend fun getAdvicesByRoute(@Path("routeId") routeId: Int?): List<Advice>

    @GET("/lessons/route/{routeId}")
    suspend fun getLessonsByRoute(@Path("routeId") routeId: Int?): List<Lesson>

    @GET("/lessons/route/{routeId}/user/{userId}")
    suspend fun getLessonsByRouteWithProgress(
        @Path("routeId") routeId: Int?,
        @Path("userId") userId: Int?
    ): List<Lesson>

    @GET("/activities/lesson/{lessonId}")
    suspend fun getActivitiesByLesson(@Path("lessonId") lessonId: Int?): List<Activity>

    @GET("/questions/activity/{activityId}")
    suspend fun getQuestionsByActivity(@Path("activityId") activityId: Int?): List<Question>

    @GET("/options/question/{questionId}")
    suspend fun getOptionsByQuestion(@Path("questionId") questionId: Int?): List<Option>

    @GET("/activities-progress/user/{userId}")
    suspend fun getActivitiesProgressByUser(@Path("userId") userId: Int?): List<ActivityProgress>

    @POST("/activities-progress/complete")
    suspend fun completeActivity(@Body body: CompleteActivityRequest): CompleteActivityResult

    @POST("/attempts")
    suspend fun createAttempt(@Body body: AttemptRequest): Attempt
}