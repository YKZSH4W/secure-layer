package com.example.secure_layer.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.secure_layer.Components.BottomNavBar
import com.example.secure_layer.Components.LearningNode
import com.example.secure_layer.Components.SecureBlue
import com.example.secure_layer.Components.TopNavBar
import com.example.secure_layer.R

val SecureGreen = Color(0xFF2ECC71)

@Composable
fun RouteScreen(navController: NavController) {
    Scaffold(
        topBar = {TopNavBar(navController, title = "SecureLayer")},
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Tu Camino Seguro", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = SecureBlue)
            Text("Sigue los círculos para aprender a\nprotegerte paso a paso.", textAlign = TextAlign.Center, fontSize = 16.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp))

            Spacer(modifier = Modifier.height(32.dp))

            LearningNode(
                iconId = R.drawable.shield,
                label = "Conceptos Básicos",
                containerColor = SecureGreen,
                isLocked = false,
            )
            ConnectorLine()
            LearningNode(
                iconId = R.drawable.mail_ic,
                label = "Detectando Phishing \n¡ESTÁS AQUÍ!",
                containerColor = SecureBlue,
                isLocked = false,
                isCurrent = true
            )
            ConnectorLine()
            LearningNode(
                iconId = R.drawable.sms_icon,
                label = "Seguridad en SMS",
                containerColor = SecureGreen,
                isLocked = true
            )
            ConnectorLine()
            LearningNode(
                iconId = R.drawable.key_ic,
                label = "Contraseñas Fuertes",
                containerColor = SecureGreen,
                isLocked = true
            )
            Spacer(modifier = Modifier.height(32.dp))
            TipOfDayCard()
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ConnectorLine() {
    Box(modifier = Modifier.width(4.dp).height(40.dp).background(Color(0xFFE0E0E0)))
}

@Composable
fun TipOfDayCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("💡", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Consejo del Día", fontWeight = FontWeight.Bold, color = Color(0xFF856404))
                Text(text = "Nunca compartas tus claves por teléfono.", fontSize = 13.sp, color = Color(0xFF856404))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutePreview() {
    RouteScreen(navController = rememberNavController())
}

