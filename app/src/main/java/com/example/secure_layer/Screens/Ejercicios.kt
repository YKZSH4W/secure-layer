package com.example.secure_layer.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.secure_layer.Components.BottomNavBar
import com.example.secure_layer.Components.TopNavBar
import com.example.secure_layer.R

@Composable
fun EjerciciosScreen(navController: NavController) {
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
                "Detectando el Phishing",
                "Aprenda a identificar mensajes sospechosos.",
                "Completada", Color(0xFFC8E6C9),
                textColor = Color(0xFF087347),
                onClick = { navController.navigate("activity1") }
            )
            LessonCard(R.drawable.link_ic,
                "Enlaces Peligrosos",
                "Nunca haga clic sin estar seguro.",
                "En curso", Color(0xFFFFE0B2),
                textColor = Color(0xFFAF6C00),
                onClick = ({})
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

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cellphone_ic),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Llamadas Telefónicas",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003466)
                )
            }
            LessonCard(
                R.drawable.cell_activity_ic,
                "El Cuento del Tío",
                "Reconozca tácticas de presión\n emocional por teléfono.",
                "Bloqueada",
                Color(0xFFE0E0E0),
                onClick = ({})
            )
        }
    }
}

@Composable
fun LessonCard(icon: Int,
               title: String,
               desc: String,
               status: String,
               statusColor: Color,
               textColor: Color = Color.Black,
               onClick: () -> Unit
)
{
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(75.dp))

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            Surface(color = statusColor, shape = RoundedCornerShape(16.dp)) {
                Text(status, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), fontSize = 11.sp, fontWeight = FontWeight.Bold,color = textColor)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun EjerciciosScreenPreview() {
    EjerciciosScreen(navController = rememberNavController())
}
