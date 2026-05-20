package com.example.secure_layer.Navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secure_layer.Screens.ConsejosScreen
import com.example.secure_layer.Screens.MainMenu
import com.example.secure_layer.Screens.Perfil
import com.example.secure_layer.Screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("main") {
            MainMenu(navController)
        }
        composable("perfil") {
            Perfil(navController)
        }
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("ejercicios") {
            Text("Pantalla de Ejercicios")
        }
        composable("consejos") {
            ConsejosScreen(navController)
        }

    }
}
