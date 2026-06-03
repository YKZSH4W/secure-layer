package com.example.securelayer.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.LessonCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.ActivitiesViewModel

@Composable
fun ExercisesScreen(navController: NavController) {
    val activitiesViewModel: ActivitiesViewModel = viewModel()

    val currentLesson = SessionManager.currentLesson

    // Categoría seleccionada en el filtro (null = todas)
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val filterScrollState = rememberScrollState()

    // Carga las actividades de la lección y el progreso del usuario.
    // Se recarga también al completar una actividad (progressRefreshTrigger).
    LaunchedEffect(currentLesson, SessionManager.progressRefreshTrigger) {
        currentLesson?.id?.let { lessonId ->
            activitiesViewModel.getActivitiesByLesson(lessonId, SessionManager.currentUser?.id)
        }
    }

    Scaffold(
        containerColor = Background,
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

            // Categorías disponibles para filtrar
            val categories = activitiesViewModel.activities
                .map { it.category ?: "General" }
                .distinct()
            // Si la categoría seleccionada ya no existe, equivale a "Todas"
            val effectiveCategory = selectedCategory?.takeIf { it in categories }

            // Botones de filtro por categoría (solo si hay más de una)
            if (categories.size > 1) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(filterScrollState),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = effectiveCategory == null,
                        onClick = { selectedCategory = null },
                        label = { Text("Todas") }
                    )
                    categories.forEach { cat ->
                        FilterChip(
                            selected = effectiveCategory == cat,
                            onClick = { selectedCategory = cat },
                            label = { Text(cat) }
                        )
                    }
                }
            }

            // Barra de progreso de la lección: actividades completadas / total
            val totalActivities = activitiesViewModel.activities.size
            val completedCount = activitiesViewModel.activities.count {
                activitiesViewModel.isCompleted(it.id)
            }
            val progress = if (totalActivities > 0) {
                completedCount.toFloat() / totalActivities
            } else 0f

            if (totalActivities > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Tu progreso", fontWeight = FontWeight.Medium)
                    Text(
                        "${(progress * 100).toInt()}%",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006D42)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = Color(0xFF006D42),
                    trackColor = Color(0xFFE0E0E0),
                    strokeCap = StrokeCap.Round
                )
            }

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
                        text = if (currentLesson == null)
                            "Selecciona una lección desde tu ruta para ver sus actividades."
                        else
                            "No hay actividades disponibles para esta lección.",
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                else -> {
                    // Filtra por la categoría seleccionada y agrupa (sin categoría → "General")
                    val groupedActivities = activitiesViewModel.activities
                        .filter { effectiveCategory == null || (it.category ?: "General") == effectiveCategory }
                        .groupBy { it.category ?: "General" }

                    groupedActivities.forEach { (category, activities) ->
                        // Encabezado de la sección (texto arriba)
                        Text(
                            text = category,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = SecureBlue,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )

                        activities.forEach { activity ->
                            val completed = activitiesViewModel.isCompleted(activity.id)

                            val (statusText, statusColor, statusTextColor) = if (completed) {
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
                                    SessionManager.currentActivityCompleted = completed
                                    // Las actividades de tipo "phishing" abren la simulación;
                                    // el resto, el quiz normal.
                                    if (activity.type?.equals("phishing", ignoreCase = true) == true) {
                                        navController.navigate("activity1")
                                    } else {
                                        navController.navigate("quiz")
                                    }
                                },
                                iconTint = statusTextColor
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
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
