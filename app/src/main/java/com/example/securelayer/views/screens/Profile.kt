package com.example.securelayer.views.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.R
import com.example.securelayer.data.ImageUtils
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.components.AchievementCardVertical
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.StatCard
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.ProfileStatsViewModel

@Composable
fun Profile(navController: NavController) {
    val statsViewModel: ProfileStatsViewModel = viewModel()

    // Carga las estadísticas reales; se recarga al completar una actividad
    LaunchedEffect(SessionManager.progressRefreshTrigger) {
        statsViewModel.loadStats(SessionManager.currentUser?.id)
    }

    Scaffold(
        topBar = {
            TopNavBar(navController = navController, title = "SecureLayer")
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        containerColor = Background
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7FAFD))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Foto de Perfil (del usuario si la tiene, o placeholder)
                val profileBitmap = remember(SessionManager.currentUser?.profilePicture) {
                    ImageUtils.base64ToImageBitmap(SessionManager.currentUser?.profilePicture)
                }

                Box(contentAlignment = Alignment.BottomEnd) {
                    if (profileBitmap != null) {
                        Image(
                            bitmap = profileBitmap,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.LightGray, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.user_msg_ic),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.LightGray, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Verificado",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.White, CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Hola, ${SessionManager.currentUser?.name?.trim()}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("¡Estás haciendo un gran trabajo!", color = Color.Gray)

                TextButton(onClick = { navController.navigate("editar_perfil") }) {
                    Text("Editar perfil", color = SecureBlue, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))

                //Nivel de Seguridad
                val knowledgeLevel = SessionManager.currentUser?.knowledgeLevel ?: "Sin clasificar"

                // Colores base de cada raya
                val grayBar = Color(0xFFE0E0E0)
                val redBar = Color(0xFFE53935)
                val yellowBar = Color(0xFFF9A825)
                val greenBar = Color(0xFF2E7D32)

                // Las rayas activas toman el color del nivel actual
                // principiante = 1 raya roja, avanzado = 2 amarillas, experto = 3 verdes.
                val (bar1, bar2, bar3) = when (knowledgeLevel.lowercase()) {
                    "principiante" -> Triple(redBar, grayBar, grayBar)
                    "avanzado" -> Triple(yellowBar, yellowBar, grayBar)
                    "experto" -> Triple(greenBar, greenBar, greenBar)
                    else -> Triple(grayBar, grayBar, grayBar) // Sin clasificar
                }

                // Color del texto del nivel (el de la raya más alta alcanzada)
                val levelTextColor = when (knowledgeLevel.lowercase()) {
                    "principiante" -> redBar
                    "avanzado" -> yellowBar
                    "experto" -> greenBar
                    else -> Color.Gray
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Tu Nivel de Seguridad", fontWeight = FontWeight.Bold)
                            Text(knowledgeLevel, color = levelTextColor, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Barra dividida en 3 niveles (3 rayas)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(bar1, bar2, bar3).forEach { barColor ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(barColor)
                                )
                            }
                        }
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
                    TextButton(onClick = { navController.navigate("medallas") }) {
                        Text("Ver todas", color = SecureBlue)
                    }
                }

                // Medallas obtenidas por el usuario (de la BD)
                val medals = statsViewModel.userAchievements

                if (medals.isEmpty()) {
                    Text(
                        "Aún no has desbloqueado medallas. ¡Gana puntos completando actividades!",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                } else {
                    medals.chunked(2).forEach { rowMedals ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowMedals.forEach { ua ->
                                AchievementCardVertical(
                                    ua.achievement,
                                    Modifier.weight(1f).fillMaxHeight()
                                )
                            }
                            // Rellena la columna faltante si la fila tiene una sola medalla
                            if (rowMedals.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Mis Estadísticas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Grid de 2 columnas con estadísticas reales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${SessionManager.currentUser?.streak ?: 0}",
                        label = "Racha",
                        sublabel = "días seguidos"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${statsViewModel.courseProgress}%",
                        label = "Progreso del curso",
                        sublabel = "completado"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${statsViewModel.accuracy}%",
                        label = "Acierto global",
                        sublabel = "en tus intentos"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${medals.size}",
                        label = "Medallas",
                        sublabel = "obtenidas"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${statsViewModel.attemptsCount}",
                        label = "Intentos",
                        sublabel = "realizados"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${SessionManager.currentUser?.totalXp ?: 0}",
                        label = "Puntos totales",
                        sublabel = "acumulados"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${statsViewModel.completedLessons}",
                        label = "Lecciones",
                        sublabel = "completadas"
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        value = "${statsViewModel.examAverage}%",
                        label = "Promedio exámenes",
                        sublabel = "calif. global"
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                CustomPrimaryButton(
                    text = "Historial de Actividades",
                    onClick = {
                        navController.navigate("historial")
                    },
                )

                Spacer(modifier = Modifier.height(10.dp))

                CustomOutlinedButton(
                    text = "Cerrar Sesion",
                    onClick = {
                        SessionManager.logout()
                        navController.navigate("welcome") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun PerfilPreview() {
    Profile(navController = rememberNavController())
}