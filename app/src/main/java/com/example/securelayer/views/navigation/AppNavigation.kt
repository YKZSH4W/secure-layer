package com.example.securelayer.views.navigation

import com.example.securelayer.views.screens.ExercisesScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.securelayer.views.activities.PhishingDetected
import com.example.securelayer.views.screens.FormScreen
import com.example.securelayer.views.screens.AdviceScreen
import com.example.securelayer.views.screens.AdvicesScreen
import com.example.securelayer.views.screens.AllMedalsScreen
import com.example.securelayer.views.screens.BasicConceptsScreen
import com.example.securelayer.views.screens.EditProfileScreen
import com.example.securelayer.views.screens.FinishedActivityScreen
import com.example.securelayer.views.screens.HistoryScreen
import com.example.securelayer.views.screens.LoginScreen
import com.example.securelayer.views.screens.Profile
import com.example.securelayer.views.screens.QuizScreen
import com.example.securelayer.views.screens.RegisterScreen
import com.example.securelayer.views.screens.RouteScreen
import com.example.securelayer.views.screens.FormResult
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
        composable("form") {
            FormScreen(navController)
        }
        composable("encuesta_resultado") {
            FormResult(navController)
        }
        composable(
            "BasicConcepts?fromConsejos={fromConsejos}",
            arguments = listOf(navArgument("fromConsejos") {
                type = NavType.BoolType
                defaultValue = false
            })
        ) { backStackEntry ->
            val fromConsejos = backStackEntry.arguments?.getBoolean("fromConsejos") ?: false
            BasicConceptsScreen(navController, fromConsejos)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("perfil") {
            Profile(navController)
        }
        composable("editar_perfil") {
            EditProfileScreen(navController)
        }
        composable("historial") {
            HistoryScreen(navController)
        }
        composable("medallas") {
            AllMedalsScreen(navController)
        }
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("ejercicios") {
            ExercisesScreen(navController)
        }
        composable("quiz") {
            QuizScreen(navController)
        }
        composable("consejos") {
            AdvicesScreen(navController)
        }
        composable("consejo_privacidad_info") {
            AdviceScreen(navController)
        }
        composable("activity1") {
            PhishingDetected(navController)
        }
        composable("actividad_completada") {
            FinishedActivityScreen(navController)
        }
    }
}
