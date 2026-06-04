package com.example.securelayer.views.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.PhishingSimulation
import com.example.securelayer.data.model.QuizFeedbackItem
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.PhishingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhishingDetected(navController: NavController) {
    val viewModel: PhishingViewModel = viewModel()
    val currentActivity = SessionManager.currentActivity

    // Carga las simulaciones de la actividad
    LaunchedEffect(currentActivity) {
        currentActivity?.id?.let { viewModel.loadSimulations(it) }
    }

    // Estado del recorrido
    var currentIndex by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var lastCorrect by remember { mutableStateOf(false) }
    // Respuesta del usuario por simulación (index → dijo "es phishing")
    val userAnswers = remember { mutableStateMapOf<Int, Boolean>() }

    Scaffold(
        topBar = { TopNavBar(navController = navController, title = "SecureLayer") },
        bottomBar = { BottomNavBar(navController = navController) },
        containerColor = Color(0xFFF7FAFD)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when {
                viewModel.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator(color = SecureBlue) }
                }

                viewModel.simulations.isEmpty() -> {
                    Text(
                        "No hay mensajes para esta actividad.",
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    val total = viewModel.simulations.size
                    val index = currentIndex.coerceIn(0, total - 1)
                    val simulation = viewModel.simulations[index]
                    val isLast = index == total - 1

                    Text("Analiza el mensaje", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Mensaje ${index + 1} de $total",
                        fontSize = 14.sp,
                        color = Color(0xFF424750),
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                    )

                    // Mensaje simulado según su tipo (sms / email / whatsapp)
                    MessageSimulationCard(simulation)

                    Spacer(modifier = Modifier.height(24.dp))

                    if (!answered) {
                        Text(
                            "¿Este mensaje es seguro o es phishing?",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        CustomPrimaryButton(
                            text = "Es phishing",
                            containerColor = Color(0xFF00674F),
                            onClick = {
                                userAnswers[index] = true
                                lastCorrect = simulation.isScam
                                answered = true
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.correct_ic),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomOutlinedButton(
                            text = "Parece seguro",
                            onClick = {
                                userAnswers[index] = false
                                lastCorrect = !simulation.isScam
                                answered = true
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.incorrect_ic),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            color = Color(0xFF424750)
                        )
                    } else {
                        // Retroalimentación de la respuesta
                        FeedbackBanner(correct = lastCorrect, isScam = simulation.isScam)

                        Spacer(modifier = Modifier.height(16.dp))

                        CustomPrimaryButton(
                            text = if (isLast) "Ver resultados" else "Siguiente mensaje",
                            onClick = {
                                if (!isLast) {
                                    currentIndex = index + 1
                                    answered = false
                                } else {
                                    finishPhishingActivity(viewModel, userAnswers, navController)
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.right_arrow),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

// Calcula el resultado, marca la actividad completada y va a la pantalla de resultados
private fun finishPhishingActivity(
    viewModel: PhishingViewModel,
    userAnswers: Map<Int, Boolean>,
    navController: NavController
) {
    val simulations = viewModel.simulations
    val total = simulations.size
    val correct = simulations.indices.count { i ->
        (userAnswers[i] ?: false) == simulations[i].isScam
    }

    val activityXp = SessionManager.currentActivity?.xp ?: 0
    val alreadyCompleted = SessionManager.currentActivityCompleted
    // Solo se aprueba (y se obtienen puntos) si TODAS están correctas
    val passed = total > 0 && correct == total

    SessionManager.lastQuizScore = correct
    SessionManager.lastQuizTotal = total
    SessionManager.lastQuizEarnedXp = if (passed && !alreadyCompleted) activityXp else 0
    SessionManager.lastQuizFeedback = simulations.mapIndexed { i, sim ->
        val saidPhishing = userAnswers[i] ?: false
        QuizFeedbackItem(
            question = "${sim.sender}: ${sim.content}",
            userAnswer = if (saidPhishing) "Es phishing" else "Es seguro",
            correctAnswer = if (sim.isScam) "Es phishing" else "Es seguro",
            isCorrect = saidPhishing == sim.isScam,
            explanation = ""
        )
    }

    // Marca completada (y suma XP) SOLO si aprobó por primera vez
    if (passed && !alreadyCompleted) {
        // Suma la XP al usuario en sesión de inmediato (perfil actualizado sin pedir a la BD)
        val newXp = (SessionManager.currentUser?.totalXp ?: 0) + activityXp
        SessionManager.currentUser = SessionManager.currentUser?.copy(totalXp = newXp)

        // El backend otorga las medallas; markActivityCompleted prepara el diálogo si hay nueva
        viewModel.markActivityCompleted(SessionManager.currentUser?.id, SessionManager.currentActivity?.id)
    }
    viewModel.registerAttempt(
        SessionManager.currentUser?.id,
        SessionManager.currentActivity?.id,
        isCorrect = passed,
        score = if (total > 0) correct * 100 / total else 0
    )
    SessionManager.progressRefreshTrigger++

    navController.navigate("actividad_completada")
}

// Banner de retroalimentación tras responder
@Composable
private fun FeedbackBanner(correct: Boolean, isScam: Boolean) {
    val bg = if (correct) Color(0xFFE4F9F0) else Color(0xFFFDEAEA)
    val accent = if (correct) Color(0xFF006D42) else Color(0xFFB3261E)
    val iconRes = if (correct) R.drawable.correct_ic else R.drawable.incorrect_ic

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                if (correct) "¡Correcto!" else "Incorrecto",
                fontWeight = FontWeight.Bold,
                color = accent,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            if (isScam)
                "Este mensaje ERA un intento de phishing. Desconfía de la urgencia y los enlaces sospechosos."
            else
                "Este mensaje era legítimo. Bien por no caer en una falsa alarma.",
            fontSize = 14.sp,
            color = Color(0xFF424750)
        )
    }
}

// Despacha el render según el tipo de mensaje
@Composable
fun MessageSimulationCard(simulation: PhishingSimulation) {
    when (simulation.typeMessage.lowercase()) {
        "whatsapp" -> WhatsAppMessage(simulation)
        "email", "correo" -> EmailMessage(simulation)
        else -> SmsMessage(simulation) // sms y cualquier otro
    }
}

// --- SMS ---
@Composable
private fun SmsMessage(simulation: PhishingSimulation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.user_msg_ic),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(simulation.sender, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text("SMS", fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                color = Color(0xFFF0F2F5),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    simulation.content,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

// --- WhatsApp ---
@Composable
private fun WhatsAppMessage(simulation: PhishingSimulation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFECE5DD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Encabezado estilo WhatsApp (verde)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF075E54))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.welcome_user_ic),
                        contentDescription = null,
                        tint = Color(0xFF075E54),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(simulation.sender, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text("WhatsApp", color = Color.White, fontSize = 12.sp)
            }

            // Burbuja del mensaje
            Box(modifier = Modifier.padding(12.dp)) {
                Surface(
                    color = Color(0xFFDCF8C6),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        simulation.content,
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

// --- Email ---
@Composable
private fun EmailMessage(simulation: PhishingSimulation) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.mail_ic),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Correo electrónico", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("De: ${simulation.sender}", fontSize = 13.sp, color = Color.Gray)
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFEEEEEE))
            Text(simulation.content, fontSize = 15.sp, color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhishingPreview() {
    PhishingDetected(rememberNavController())
}
