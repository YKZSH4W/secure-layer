package com.example.secure_layer.Navigation

import com.example.secure_layer.Screens.EjerciciosScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secure_layer.Activities.PhishingDetected
import com.example.secure_layer.Screens.ConsejoPrivacidadTotalScreen
import com.example.secure_layer.Screens.ConsejosScreen
import com.example.secure_layer.Screens.EncuestaScreen
import com.example.secure_layer.Screens.LoginScreen
import com.example.secure_layer.Screens.Perfil
import com.example.secure_layer.Screens.RegisterScreen
import com.example.secure_layer.Screens.RouteScreen
import com.example.secure_layer.Screens.WelcomeScreen

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
    }
}
