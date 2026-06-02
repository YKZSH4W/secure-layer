package com.example.securelayer.views.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.MedalCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.R
import com.example.securelayer.data.ImageUtils
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue

@Composable
fun Profile(navController: NavController) {
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
                            painter = painterResource(id = R.drawable.user),
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
                Text("Hola, ${SessionManager.currentUser?.name}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
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

                // Las rayas activas toman el color del nivel actual:
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
                    TextButton(onClick = { navController.navigate("") }) {
                        Text("Ver todas", color = Color.Blue)
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MedalCard(
                        modifier = Modifier.weight(1f),
                        title = "Escudo de\nOro",
                        subtitle = "Por 7 días sin\nriesgos",
                        iconContent = {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFFFE0B2)), contentAlignment = Alignment.Center) {
                                Icon(painter = painterResource(id = R.drawable.shield_medal), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(40.dp))
                            }
                        }
                    )
                    MedalCard(
                        modifier = Modifier.weight(1f),
                        title = "Detective de\nFraudes",
                        subtitle = "Detectaste un SMS\nfalso",
                        iconContent = {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFA5F3C2)), contentAlignment = Alignment.Center) {
                                Icon(painter = painterResource(id = R.drawable.lupa_medal), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(40.dp))
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Estadísticas
                Text(
                    "Estadísticas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                val stats = listOf(
                    "Días seguidos" to "${SessionManager.currentUser?.streak ?: 0}",
                    "XP total" to "${SessionManager.currentUser?.totalXp ?: 0}"
                )

                // Grid de 2 columnas
                stats.chunked(2).forEach { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        fila.forEach { (label, value) ->
                            StatCard(modifier = Modifier.weight(1f), value = value, label = label)
                        }
                        // Rellena la columna faltante si la fila tiene un solo elemento
                        if (fila.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            CustomOutlinedButton(
                text = "Cerrar Sesion",
                onClick = {
                    SessionManager.logout()
                    navController.navigate("welcome") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

// Tarjeta de una estadística del usuario (valor grande + etiqueta)
@Composable
private fun StatCard(modifier: Modifier = Modifier, value: String, label: String) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF003366))
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 13.sp, color = Color.Gray)
        }
    }
}

@Preview
@Composable
fun PerfilPreview() {
    Profile(navController = rememberNavController())
}