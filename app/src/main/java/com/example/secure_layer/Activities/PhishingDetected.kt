package com.example.secure_layer.Activities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.secure_layer.Components.BottomNavBar
import com.example.secure_layer.Components.TopNavBar

@Composable
fun PhishingDetected(navController: NavController) {
    Scaffold(
        topBar = {
            TopNavBar(navController, title = "Detectando Phishing")
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Mensajes de Texto (SMS)",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF003466)
            )
            Text(
                text = "Se ha detectado un posible intento de phishing en sus mensajes. Por favor, tenga cuidado con los enlaces sospechosos.",
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhishingPreview() {
    val navController = rememberNavController()
    PhishingDetected(navController)
}