package com.example.securelayer.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.QuizViewModel

@Composable
fun QuizScreen(navController: NavController) {
    val viewModel: QuizViewModel = viewModel()

    val currentActivity = SessionManager.currentActivity

    // Índice de la pregunta que se muestra actualmente (una por pantalla)
    var currentQuestionIndex by remember { mutableIntStateOf(0) }

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
                .background(Color(0xFFF7FAFD))
        ) {
            Text(
                text = currentActivity?.name ?: "Quiz",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp)
            )

            when {
                viewModel.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = SecureBlue)
                    }
                }

                viewModel.questions.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay preguntas disponibles para esta actividad.",
                            color = Color.Gray
                        )
                    }
                }

                else -> {
                    val total = viewModel.questions.size
                    // Mantiene el índice dentro de rango por seguridad
                    val index = currentQuestionIndex.coerceIn(0, total - 1)
                    val question = viewModel.questions[index]
                    val isLastQuestion = index == total - 1
                    val currentAnswered = viewModel.selectedAnswers[index] != null

                    // Barra de progreso del quiz (pregunta actual / total)
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                        Text(
                            text = "Pregunta ${index + 1} de $total",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF424750)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { (index + 1).toFloat() / total },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
                            color = SecureBlue,
                            trackColor = Color(0xFFE0E0E0),
                            strokeCap = StrokeCap.Round
                        )
                    }

                    // Pregunta actual (ocupa el espacio disponible)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp)
                    ) {
                        QuizActivityCard(
                            index = index,
                            question = question,
                            viewModel = viewModel
                        )
                    }

                    // Botón inferior: "Siguiente" hasta la última pregunta, donde es "Enviar".
                    // Se habilita solo cuando la pregunta actual está contestada.
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 24.dp, top = 8.dp)
                    ) {
                        CustomPrimaryButton(
                            text = if (isLastQuestion) "Enviar" else "Siguiente",
                            enabled = currentAnswered,
                            onClick = {
                                if (!isLastQuestion) {
                                    currentQuestionIndex = index + 1
                                } else {
                                    // === Flujo de envío ===
                                    val score = viewModel.validateAnswers()
                                    val activityXp = currentActivity?.xp ?: 0
                                    val alreadyCompleted = SessionManager.currentActivityCompleted
                                    // El examen final da una calificación real y se completa en un
                                    // solo intento (sin posibilidad de reintentarlo).
                                    val isExam = currentActivity?.name
                                        ?.trim()?.equals("Examen final", ignoreCase = true) == true
                                    // Una actividad normal solo se aprueba con TODO correcto.
                                    val passed = total > 0 && score == total
                                    // El examen se completa pase lo que pase; la normal, solo al aprobar.
                                    val completes = isExam || passed

                                    SessionManager.lastQuizWasExam = isExam
                                    SessionManager.lastQuizScore = score
                                    SessionManager.lastQuizTotal = total
                                    // Da puntos al completar por primera vez (examen: siempre; normal: al aprobar)
                                    SessionManager.lastQuizEarnedXp =
                                        if (completes && !alreadyCompleted) activityXp else 0
                                    SessionManager.lastQuizFeedback = viewModel.buildFeedback()

                                    // Registra este intento en el historial (siempre) con la calificación real
                                    viewModel.registerAttempt(
                                        userId = SessionManager.currentUser?.id,
                                        activityId = currentActivity?.id,
                                        isCorrect = passed,
                                        score = if (total > 0) score * 100 / total else 0
                                    )

                                    // Marca la actividad completada (y suma puntos) en la primera
                                    // finalización. El examen no se puede reintentar después.
                                    if (completes && !alreadyCompleted) {
                                        // Suma los puntos al usuario en sesión de inmediato,
                                        // así el perfil se muestra actualizado sin pedir a la BD.
                                        val newXp = (SessionManager.currentUser?.totalXp ?: 0) + activityXp
                                        SessionManager.currentUser =
                                            SessionManager.currentUser?.copy(totalXp = newXp)

                                        // El backend otorga las medallas; markActivityCompleted
                                        // las recibe y prepara el diálogo si hay alguna nueva.
                                        viewModel.markActivityCompleted(
                                            SessionManager.currentUser?.id,
                                            currentActivity?.id
                                        )
                                    }

                                    // Fuerza que la ruta/perfil recarguen el progreso
                                    SessionManager.progressRefreshTrigger++

                                    navController.navigate("actividad_completada")
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        id = if (isLastQuestion) R.drawable.check else R.drawable.right_arrow
                                    ),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(if (isLastQuestion) 28.dp else 18.dp)
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
