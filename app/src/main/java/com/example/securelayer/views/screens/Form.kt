package com.example.securelayer.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.securelayer.data.model.QuizFeedbackItem
import com.example.securelayer.data.model.QuizQuestion
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.QuizActivityCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.viewmodel.QuizViewModel
import com.example.securelayer.views.viewmodel.UsersViewModel


@Composable
fun FormScreen(navController: NavController) {
    val viewModel: QuizViewModel = viewModel()
    val usersViewModel: UsersViewModel = viewModel()
    var showUnansweredError by remember { mutableStateOf(false) }

    // Explicaciones hardcodeadas (iguales para todos), alineadas con el orden de las preguntas
    val explanations = listOf(
        "Los premios inesperados que piden tus datos son una estafa (phishing). Nunca compartas tu información para reclamar algo que no solicitaste.",
        "Ninguna institución legítima te pedirá tu contraseña o NIP por teléfono. Si te los piden, es un fraude.",
        "Una frase larga que combina letras, números y símbolos es mucho más difícil de adivinar que una fecha o '1234'.",
        "Los archivos adjuntos de remitentes desconocidos pueden contener virus. Si no esperabas el correo, bórralo sin abrirlo."
    )

    LaunchedEffect(Unit) {
        viewModel.loadQuestions(listOf(
            QuizQuestion(
                question = "Recibes un mensaje que dice que ganaste un premio y te piden tus datos para reclamarlo. ¿Qué haces?",
                answers = listOf(
                    "Doy mis datos para reclamar el premio",
                    "Lo ignoro o lo borro",
                    "Reenvío el mensaje a mis contactos"
                ),
                rightAnswer = "Lo ignoro o lo borro"
            ),
            QuizQuestion(
                question = "Tu banco te llama y te pide tu contraseña o NIP. ¿Qué haces?",
                answers = listOf(
                    "Se los doy, es mi banco",
                    "No los comparto, el banco nunca los pide",
                    "Le doy solo una parte"
                ),
                rightAnswer = "No los comparto, el banco nunca los pide"
            ),
            QuizQuestion(
                question = "¿Cuál de estas es una contraseña más segura?",
                answers = listOf(
                    "1234",
                    "Tu fecha de nacimiento",
                    "Una frase larga con letras, números y símbolos"
                ),
                rightAnswer = "Una frase larga con letras, números y símbolos"
            ),
            QuizQuestion(
                question = "Llega un correo de un desconocido con un archivo adjunto. ¿Qué haces?",
                answers = listOf(
                    "Lo abro por curiosidad",
                    "No lo abro y lo borro",
                    "Respondo pidiendo más información"
                ),
                rightAnswer = "No lo abro y lo borro"
            )
        ))
    }

    Scaffold(
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Background)
        ) {
            Text("Encuesta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // === Preguntas de la encuesta ===
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
                            // El test solo puede dejar como máximo en "Principiante":
                            // mayoría de aciertos → Principiante; si lo hace mal → Sin clasificar
                            val level = if (score * 2 > total) "Principiante" else "Sin clasificar"

                            usersViewModel.updateKnowledgeLevel(
                                SessionManager.currentUser?.id,
                                level
                            )

                            // Construye la retroalimentación por pregunta:
                            // respuesta del usuario vs. respuesta correcta + explicación
                            SessionManager.surveyFeedback = viewModel.questions.mapIndexed { i, q ->
                                val userAnswer = viewModel.selectedAnswers[i] ?: ""
                                QuizFeedbackItem(
                                    question = q.question,
                                    userAnswer = userAnswer,
                                    correctAnswer = q.rightAnswer,
                                    isCorrect = userAnswer == q.rightAnswer,
                                    explanation = explanations.getOrElse(i) { "" }
                                )
                            }
                            SessionManager.surveyScore = score
                            SessionManager.surveyTotal = total
                            SessionManager.surveyLevel = level

                            // La retroalimentación se muestra en su propia pantalla (inicia desde arriba)
                            navController.navigate("encuesta_resultado")
                        } else {
                            showUnansweredError = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Icono de enviar",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewActivity() {
    FormScreen(navController = rememberNavController())
}