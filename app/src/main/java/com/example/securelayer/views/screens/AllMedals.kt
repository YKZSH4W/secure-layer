package com.example.securelayer.views.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.components.AchievementCardHorizontal
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.AchievementsViewModel

@Composable
fun AllMedalsScreen(navController: NavController) {
    val viewModel: AchievementsViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.load(SessionManager.currentUser?.id)
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
                "Mis Medallas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {
                viewModel.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator(color = SecureBlue) }
                }

                viewModel.userAchievements.isEmpty() -> {
                    Text(
                        "Aún no has desbloqueado medallas. ¡Gana XP completando actividades!",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                else -> {
                    viewModel.userAchievements.forEach { ua ->
                        AchievementCardHorizontal(ua.achievement)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllMedalsPreview() {
    AllMedalsScreen(navController = rememberNavController())
}
