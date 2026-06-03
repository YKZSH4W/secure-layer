package com.example.securelayer.views.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.MedalBienvenido
import com.example.securelayer.views.components.MedalPrimerPaso
import com.example.securelayer.views.components.StatCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.R
import com.example.securelayer.views.components.MedalChispaDigital
import com.example.securelayer.views.components.MedalCyberAgente
import com.example.securelayer.views.components.MedalEscudoDeElite
import com.example.securelayer.views.components.MedalGuardianNovato
import com.example.securelayer.views.components.MedalMenteAlerta
import com.example.securelayer.views.components.MedalPlatino
import com.example.securelayer.views.components.MedalRachaDeSiete
import com.example.securelayer.views.components.MedalTropiezoValiente

@Composable
fun Profile(navController: NavController) {
    Scaffold(
        topBar = {
            TopNavBar(navController = navController, title = "SecurityLayer")
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Foto de Perfil
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Verificado",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp).background(Color.White, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Hola, María", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("¡Estás haciendo un gran trabajo!", color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            // Nivel de Seguridad
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tu Nivel de Seguridad", fontWeight = FontWeight.Bold)
                        Text("Nivel 4", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                    }
                    LinearProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF2E7D32),
                        trackColor = Color(0xFFE0E0E0),
                        strokeCap = StrokeCap.Butt
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Casi un experto en detectar trampas digitales.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Medallas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Mis Medallas", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = { navController.navigate("") }) {
                    Text("Ver todas", color = Color.Blue)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MedalMenteAlerta(modifier = Modifier.weight(1f).fillMaxHeight())
                MedalChispaDigital(modifier = Modifier.weight(1f).fillMaxHeight())
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Estadísticas
            Text(
                "Mis Estadísticas",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "5",
                    label = "Días activos",
                    sublabel = "últimos 7 días"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "68%",
                    label = "Progreso promedio",
                    sublabel = "ruta general"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "78%",
                    label = "Acierto global",
                    sublabel = "actividades"
                )
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "25",
                    label = "Medallas obtenidas",
                    sublabel = "de 60"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomOutlinedButton(
                text = "Cerrar Sesión",
                onClick = { navController.navigate("welcome") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PerfilPreview() {
    Profile(navController = rememberNavController())
}