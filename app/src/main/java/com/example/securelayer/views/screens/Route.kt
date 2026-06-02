package com.example.securelayer.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.LearningNode
import com.example.securelayer.views.components.TipOfDayCard
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.EnrollsViewModel

val SecureGreen = Color(0xFF2ECC71)

@Composable
fun RouteScreen(navController: NavController) {
    val enrollViewModel: EnrollsViewModel = viewModel()

    LaunchedEffect(Unit) {
        enrollViewModel.getEnrollsByUser(SessionManager.currentUser?.id)
    }

    val currentRoute = SessionManager.currentRoute

    Scaffold(
        containerColor = Color(0xFFF7FAFD),
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF7FAFD)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("${currentRoute?.name}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = SecureBlue)
            Text("${currentRoute?.description}", textAlign = TextAlign.Center, fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp))

            Spacer(modifier = Modifier.height(32.dp))

            /* Probar rutas disponibles por el usuario
                enrollViewModel.currentRoutes.forEach { item ->
                    Text("Ruta: ${item.name}, Id ${item.id}")
                }
            */

            LearningNode(
                iconId = R.drawable.shield_route_ic,
                label = "Conceptos Básicos",
                containerColor = Color(0xFF006D42),
                isLocked = false,
                onClick = { navController.navigate("conceptos básicos") },
                modifier = Modifier.padding(end = 200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LearningNode(
                iconId = R.drawable.mail_route_ic,
                label = "Detectando Phishing \n¡ESTÁS AQUÍ!",
                containerColor = SecureBlue,
                isLocked = false,
                isCurrent = true,
                onClick = { navController.navigate("activity1") },
                modifier = Modifier.padding(start = 200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LearningNode(
                iconId = R.drawable.message_dots,
                label = "Seguridad en SMS",
                containerColor = SecureGreen,
                isLocked = true,
                onClick = { },
                modifier = Modifier.padding(end = 200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LearningNode(
                iconId = R.drawable.key_ic,
                label = "Contraseñas Fuertes",
                containerColor = SecureGreen,
                isLocked = true,
                onClick = { },
                modifier = Modifier.padding(start = 200.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            TipOfDayCard(
                title = "Consejo del Día",
                description = "Nunca compartas tus claves.",
                iconContent = {
                    Image(painter = painterResource(id = R.drawable.tip_ic), contentDescription = null)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutePreview() {
    RouteScreen(navController = rememberNavController())
}

