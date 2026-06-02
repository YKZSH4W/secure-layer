package com.example.securelayer.views.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.QuizFeedbackItem
import com.example.securelayer.views.theme.Background


val FinishedActivityBorderIcon = Color(0xFFe4f9f0)

@Composable
fun FinishedActivityScreen(navController: NavController){

    val score = SessionManager.lastQuizScore
    val total = SessionManager.lastQuizTotal
    val earnedXp = SessionManager.lastQuizEarnedXp
    val feedback = SessionManager.lastQuizFeedback

    val hasResults = total > 0

    // Mensaje según el desempeño
    val title = when {
        !hasResults -> "¡Excelente trabajo!"
        score == total -> "¡Perfecto!"
        score >= total / 2.0 -> "¡Buen trabajo!"
        else -> "¡Sigue practicando!"
    }

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .border(10.dp, FinishedActivityBorderIcon, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFF98f2bb)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.shield),
                        contentDescription = null,
                        tint = Color(0xFF32483e),
                        modifier = Modifier
                            .size(90.dp)
                    )
                }

                Spacer(modifier = Modifier.height(17.dp))

                Text(title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecureBlue)

                SessionManager.currentActivity?.name?.let { activityName ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        activityName,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Resumen de puntos y aciertos
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD9D9D9),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 15.dp)
                            .padding(vertical = 10.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .background(Background)
                                .padding(horizontal = 25.dp)
                                .padding(vertical = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("+$earnedXp puntos",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006d42))

                            Spacer(modifier = Modifier.height(7.dp))

                            Text("Obtenidos",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF424750))
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .background(Background)
                                .padding(horizontal = 25.dp)
                                .padding(vertical = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                if (hasResults) "$score/$total" else "10% más",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006d42))

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                if (hasResults) "Correctas" else "Protegido",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF424750))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Retroalimentación por pregunta
                if (feedback.isNotEmpty()) {
                    Text(
                        "Retroalimentación",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecureBlue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 8.dp, bottom = 4.dp)
                    )

                    feedback.forEach { item ->
                        FeedbackCard(item)
                    }
                } else {
                    // Fallback cuando se llega sin datos de quiz
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFd5e3ff))
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.incorrect_ic),
                                contentDescription = "Icono",
                                tint = SecureBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(13.dp))
                            Text(
                                "Cada lección completada fortalece tu seguridad digital ante los estafadores",
                                fontSize = 18.sp,
                                color = SecureBlue
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomPrimaryButton(
                    text = "Siguiente lección",
                    onClick = { navController.navigate("ejercicios") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),

                    icon = {
                        Icon(
                            painter = painterResource(id =  R.drawable.right_arrow),
                            contentDescription = "Icono",
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                CustomOutlinedButton(
                    text = "Volver a la Ruta",
                    onClick = { navController.navigate("route") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),

                    icon = {
                        Icon(
                            painter = painterResource(id =  R.drawable.home),
                            contentDescription = "Icono",
                            tint = SecureBlue,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                )
            }
        }
    }
}

// Tarjeta de retroalimentación de una pregunta
@Composable
fun FeedbackCard(item: QuizFeedbackItem) {
    val accentColor = if (item.isCorrect) Color(0xFF006d42) else Color(0xFFB3261E)
    val containerColor = if (item.isCorrect) Color(0xFFe4f9f0) else Color(0xFFFDEAEA)
    val iconRes = if (item.isCorrect) R.drawable.correct_ic else R.drawable.incorrect_ic

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(containerColor)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                item.question,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF181C1E)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Tu respuesta: ${item.userAnswer}",
            fontSize = 14.sp,
            color = accentColor,
            fontWeight = FontWeight.SemiBold
        )

        if (!item.isCorrect) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Respuesta correcta: ${item.correctAnswer}",
                fontSize = 14.sp,
                color = Color(0xFF006d42),
                fontWeight = FontWeight.SemiBold
            )
        }

        if (item.explanation.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                item.explanation,
                fontSize = 14.sp,
                color = Color(0xFF424750)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinishedActivityPreview(){
    FinishedActivityScreen(rememberNavController())
}
