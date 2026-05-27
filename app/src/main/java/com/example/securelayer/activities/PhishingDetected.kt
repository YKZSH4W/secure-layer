package com.example.securelayer.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.components.BottomNavBar
import com.example.securelayer.components.CustomOutlinedButton
import com.example.securelayer.components.CustomPrimaryButton
import com.example.securelayer.components.TipOfDayCard
import com.example.securelayer.components.TopNavBar
import com.example.securelayer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhishingDetected(navController: NavController) {
    Scaffold(
        topBar = {
            TopNavBar(navController = navController, title = "SecurityLayer")
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
            Text("Paso 1: Analizar el mensaje", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Los estafadores suelen usar el sentido de\n" +
                        "urgencia para que no pienses con claridad.\n" +
                        "Observa este mensaje con atención.",
                fontSize = 14.sp,
                color = Color(0xFF424750),
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
            )

            // Tarjeta del mensaje
            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.user_msg_ic),
                            contentDescription = "Consejo de seguridad",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("55 344 2356", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("Ahora", fontSize = 12.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    // Contenedor principal con fondo gris claro
                    Surface(
                        color = Color(0xFFF0F2F5), // Color gris suave de la imagen
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp), // Esquinas redondeadas solo a la derecha
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.height(IntrinsicSize.Min) // Hace que la altura se ajuste al contenido
                        ) {
                            // Línea roja lateral (ocupa toda la altura)
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .fillMaxHeight()
                                    .background(Color.Red)
                            )

                            // Columna con el texto
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "URGENTE:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Su cuenta ha sido bloqueada. Haga clic aquí para validar:",
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "bit.ly/abgj23ok",
                                    fontSize = 15.sp,
                                    color = Color(0xFF0066CC),
                                    textDecoration = TextDecoration.Underline
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "¿Es este mensaje seguro?",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Haz clic en la opción que consideres\n" +
                        "correcta.",
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color(0xFF424750)
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            CustomPrimaryButton(
                text = "No, es una estafa",
                onClick = { /* Lógica aquí */ },
                containerColor = Color(0xFF00674F) ,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.correct_ic),
                        contentDescription = "Icono",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            CustomOutlinedButton(
                text = "Parece seguro",
                onClick = { /* Lógica aquí */ },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.incorrect_ic),
                        contentDescription = "Icono",
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .background(Color(0xFFC3C6D1))
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Consejo
            TipOfDayCard(
                title = "Consejo de seguridad",
                description = "Los bancos reales nunca usan\n" +
                        "enlaces cortos como \"bit.ly\" ni te\n" +
                        "presionan para actuar\n" +
                        "inmediatamente por SMS.",
                iconContent = {
                    Image(painter = painterResource(id = R.drawable.tip_ic), contentDescription = null)
                }
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