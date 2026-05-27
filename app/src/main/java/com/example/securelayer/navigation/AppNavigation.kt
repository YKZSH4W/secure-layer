package com.example.securelayer.navigation

import com.example.securelayer.screens.EjerciciosScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.activities.PhishingDetected
import com.example.securelayer.screens.ConsejoPrivacidadTotalScreen
import com.example.securelayer.screens.ConsejosScreen
import com.example.securelayer.screens.ConseptosBasicosScreen
import com.example.securelayer.screens.EncuestaScreen
import com.example.securelayer.screens.FinishedActivityScreen
import com.example.securelayer.screens.LoginScreen
import com.example.securelayer.screens.Perfil
import com.example.securelayer.screens.RegisterScreen
import com.example.securelayer.screens.RouteScreen
import com.example.securelayer.screens.WelcomeScreen

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
