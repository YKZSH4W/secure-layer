package com.example.securelayer.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.ActivityHistory
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(navController: NavController) {
    val viewModel: HistoryViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadHistory(SessionManager.currentUser?.id)
    }

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        bottomBar = { BottomNavBar(navController) },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Historial de actividades",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = SecureBlue
            )
            Text(
                "Tus actividades intentadas y su puntaje.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                viewModel.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator(color = SecureBlue) }
                }

                viewModel.history.isEmpty() -> {
                    Text(
                        "Aún no tienes actividades en tu historial.",
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                else -> {
                    viewModel.history.forEach { item ->
                        HistoryCard(item)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun HistoryCard(item: ActivityHistory) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo de la actividad
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3F0FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = mapLessonIcon(item.icon)),
                    contentDescription = null,
                    tint = SecureBlue,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.activityName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Fecha: ${formatHistoryDate(item.date)}",
                    fontSize = 13.sp,
                    color = Color(0xFF424750)
                )
            }

            // Puntaje de este intento
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var color = Color(0xFF006D42)

                when {
                    item.score <= 50 -> color = Color(0xFFE53935)
                    item.score <= 85 -> color = Color(0xFFF9A825)
                }

                Text(
                    "${item.score}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = color
                )
                Text("puntaje", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

// Convierte una fecha ISO ("2026-06-03T10:30:00.000Z") a "03/06/2026"
private fun formatHistoryDate(iso: String): String {
    return try {
        val (year, month, day) = iso.take(10).split("-")
        "$day/$month/$year"
    } catch (e: Exception) {
        iso
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryPreview() {
    HistoryScreen(navController = rememberNavController())
}
