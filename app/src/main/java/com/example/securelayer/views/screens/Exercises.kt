package com.example.securelayer.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.LessonCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.viewmodel.ActivitiesViewModel

@Composable
fun ExercisesScreen(navController: NavController) {
    val activitiesViewModel: ActivitiesViewModel = viewModel()

    val currentLesson = SessionManager.currentLesson

    // Carga las actividades de la lección seleccionada
    LaunchedEffect(currentLesson) {
        currentLesson?.id?.let { lessonId ->
            activitiesViewModel.getActivitiesByLesson(lessonId)
        }
    }

    Scaffold(
        containerColor = Color(0xFFF7FAFD),
        topBar = {
            TopNavBar(navController = navController, title = "SecureLayer")
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF7FAFD))
        ) {
            Text(
                text = currentLesson?.name ?: "Actividades",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = currentLesson?.description ?: "Completa las actividades de esta lección.",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            when {
                activitiesViewModel.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF007A33))
                    }
                }

                activitiesViewModel.activities.isEmpty() -> {
                    Text(
                        text = "No hay actividades disponibles para esta lección.",
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                else -> {
                    activitiesViewModel.activities.forEach { activity ->
                        val (statusText, statusColor, statusTextColor) = if (activity.isCompleted) {
                            Triple("Completada", Color(0xFFC8E6C9), Color(0xFF087347))
                        } else {
                            Triple("Disponible", Color(0xFFFFDDB5), Color(0xFFAF6C00))
                        }

                        LessonCard(
                            icon = mapLessonIcon(activity.icon),
                            title = activity.name,
                            desc = activity.description,
                            status = statusText,
                            statusColor = statusColor,
                            textColor = statusTextColor,
                            onClick = {
                                SessionManager.currentActivity = activity
                                navController.navigate("quiz")
                            },
                            iconTint = statusTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisesScreenPreview() {
    ExercisesScreen(navController = rememberNavController())
}
