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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue

@Composable
fun FormResult(navController: NavController) {
    val score = SessionManager.surveyScore
    val total = SessionManager.surveyTotal
    val level = SessionManager.surveyLevel
    val feedback = SessionManager.surveyFeedback

    Scaffold(
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Background)
                .padding(horizontal = 24.dp)
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                when {
                    total > 0 && score == total -> "¡Perfecto!"
                    total > 0 && score >= total / 2.0 -> "¡Buen trabajo!"
                    else -> "¡Sigue practicando!"
                },
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = SecureBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Acertaste $score de $total preguntas.",
                fontSize = 16.sp,
                color = Color(0xFF424750)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                "Tu nivel: $level",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF006d42)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                if (level == "Principiante")
                    "¡Bien hecho! Ya conoces lo básico de seguridad digital. Vamos a reforzarlo."
                else
                    "No te preocupes. Empezaremos desde lo más básico para que aprendas a protegerte.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Retroalimentación",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = SecureBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, bottom = 4.dp)
            )

            // Reutiliza la misma tarjeta de retroalimentación de las actividades
            feedback.forEach { item ->
                FeedbackCard(item)
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomPrimaryButton(
                text = "Continuar",
                onClick = {
                    navController.navigate("route") {
                        popUpTo("route") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.right_arrow),
                        contentDescription = "Icono de continuar",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun FormResultPreview() {
    FormResult(rememberNavController())
}
