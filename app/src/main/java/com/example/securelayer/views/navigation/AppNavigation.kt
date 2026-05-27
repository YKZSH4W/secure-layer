package com.example.securelayer.views.navigation

import com.example.securelayer.views.screens.EjerciciosScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.activities.PhishingDetected
import com.example.securelayer.views.screens.ConsejoPrivacidadTotalScreen
import com.example.securelayer.views.screens.ConsejosScreen
import com.example.securelayer.views.screens.ConseptosBasicosScreen
import com.example.securelayer.views.screens.EncuestaScreen
import com.example.securelayer.views.screens.FinishedActivityScreen
import com.example.securelayer.views.screens.LoginScreen
import com.example.securelayer.views.screens.Perfil
import com.example.securelayer.views.screens.RegisterScreen
import com.example.securelayer.views.screens.RouteScreen
import com.example.securelayer.views.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("route") {
            RouteScreen(navController)
        }
        composable("conceptos básicos") {
            ConseptosBasicosScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("perfil") {
            Perfil(navController)
        }
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("ejercicios") {
            EjerciciosScreen(navController)
        }
        composable("form") {
            EncuestaScreen(navController)
        }
        composable("consejos") {
            ConsejosScreen(navController)
        }
        composable("consejo privacidad info", ) {
            ConsejoPrivacidadTotalScreen(navController)
        }
        composable("activity1") {
            PhishingDetected(navController)
        }
        composable("actividad completada") {
            FinishedActivityScreen(navController)
        }
    }
}
