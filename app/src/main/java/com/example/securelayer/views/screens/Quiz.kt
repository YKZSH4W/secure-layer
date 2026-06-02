package com.example.securelayer.views.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.QuizActivityCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.viewmodel.QuizViewModel

@Composable
fun QuizScreen(navController: NavController) {
    val viewModel: QuizViewModel = viewModel()

    val currentActivity = SessionManager.currentActivity

    var showUnansweredError by remember { mutableStateOf(false) }

    // Carga las preguntas + opciones de la actividad seleccionada
    LaunchedEffect(currentActivity) {
        currentActivity?.id?.let { activityId ->
            viewModel.loadQuestionsByActivity(activityId)
        }
    }

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF7FAFD))
        ) {
            Text(
                text = currentActivity?.name ?: "Quiz",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp)
            )
            Text(
                text = currentActivity?.description ?: "Responde las siguientes preguntas.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 4.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    viewModel.isLoading -> {
                        CircularProgressIndicator(
                            color = Color(0xFF003366),
                            modifier = Modifier.padding(32.dp)
                        )
                    }

                    viewModel.questions.isEmpty() -> {
                        Text(
                            text = "No hay preguntas disponibles para esta actividad.",
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }

                    else -> {
                        viewModel.questions.forEachIndexed { index, question ->
                            QuizActivityCard(
                                index = index,
                                question = question,
                                viewModel = viewModel
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (showUnansweredError) {
                            Text(
                                "Por favor responde todas las preguntas",
                                color = Color(0xFFB3261E),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }

                        CustomPrimaryButton(
                            text = "Enviar",
                            onClick = {
                                if (viewModel.selectedAnswers.size == viewModel.questions.size) {
                                    showUnansweredError = false

                                    val score = viewModel.validateAnswers()
                                    val total = viewModel.questions.size
                                    val activityXp = currentActivity?.xp ?: 0
                                    // Si la actividad ya estaba completada, no se otorgan puntos de nuevo
                                    val alreadyCompleted = SessionManager.currentActivityCompleted

                                    // Guarda el resultado para la pantalla de retroalimentación
                                    SessionManager.lastQuizScore = score
                                    SessionManager.lastQuizTotal = total
                                    SessionManager.lastQuizEarnedXp = when {
                                        alreadyCompleted -> 0
                                        total > 0 -> activityXp * score / total
                                        else -> 0
                                    }
                                    SessionManager.lastQuizFeedback = viewModel.buildFeedback()

                                    // Marca la actividad como completada en el backend
                                    viewModel.markActivityCompleted(
                                        SessionManager.currentUser?.id,
                                        currentActivity?.id
                                    )

                                    // Fuerza que la ruta recargue el progreso de las lecciones
                                    SessionManager.progressRefreshTrigger++

                                    navController.navigate("actividad_completada")
                                } else {
                                    showUnansweredError = true
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.check),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizScreen(navController = rememberNavController())
}
