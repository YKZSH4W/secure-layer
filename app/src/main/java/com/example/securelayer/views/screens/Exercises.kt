package com.example.securelayer.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.LessonCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.R

@Composable
fun ExercisesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopNavBar(navController = navController, title = "Secure Layer")
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
        ) {
            Text("Biblioteca de Lecciones", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Aprenda a protegerse paso a paso con nuestras guías sencillas.", color = Color.Gray)

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Tu Progreso", fontWeight = FontWeight.Medium)
                Text("65%", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.65f },
                modifier = Modifier.fillMaxWidth().height(10.dp),
                color = Color(0xFF007A33),
                trackColor = Color(0xFFE0E0E0),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sms_icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Mensajes de Texto (SMS)",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003466)
                )
            }

            LessonCard(R.drawable.phishing_ic,
                "Enlaces Peligrosos",
                "Nunca haga clic sin estar seguro.",
                "Completada", Color(0xFFC8E6C9),
                textColor = Color(0xFF087347),
                onClick = ({navController.navigate("actividad completada")})
            )
            LessonCard(R.drawable.link_ic,
                "Detectando el Phishing",
                "Aprenda a identificar mensajes sospechosos.",
                "Completada", Color(0xFFFFE0B2),
                textColor = Color(0xFFAF6C00),
                onClick = { navController.navigate("activity1") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mail_ic),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Correos Electrónicos",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003466)
                )
            }
            LessonCard(
                R.drawable.gift_ic,
                "Premios Falsos",
                "Si parece demasiado bueno para ser verdad.",
                "Bloqueada", Color(0xFFE0E0E0),
                onClick = ({})
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EjerciciosScreenPreview() {
    ExercisesScreen(navController = rememberNavController())
}
