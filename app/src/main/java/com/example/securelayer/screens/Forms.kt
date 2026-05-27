package com.example.securelayer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.components.TopNavBar

@Composable
fun EncuestaScreen(navController: NavController) {
    val questions = listOf(
        "¿Qué haces si recibes un mensaje de un número desconocido ofreciendo un premio?",
        "Te llega un correo de 'tu banco' pidiendo tu clave. ¿Qué haces?",
        "Un familiar pide dinero urgente por mensaje. ¿Qué haces?",
        "Aparece una ventana diciendo que tu celular tiene virus. ¿Qué haces?",
        "¿Cómo guardas tus contraseñas?"
    )

    val opciones = listOf(
        listOf("Abro el enlace", "Pregunto quién es", "Borro el mensaje"),
        listOf("Pongo mis datos", "Llamo al banco", "Ignoro el correo"),
        listOf("Envío el dinero", "Hago preguntas clave", "Lo llamo por teléfono"),
        listOf("Hago clic en 'Limpiar'", "Cierro la ventana", "Apago el teléfono"),
        listOf("La misma para todo", "En un papel en casa", "Uso contraseñas distintas"),
    )

    val seleccionadas = remember { mutableStateListOf(*Array(questions.size) { -1 }) }

    Scaffold(
        topBar = {
            TopNavBar(navController,title = "Encuesta")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Marca la opción que mejor describa lo que sueles hacer:", fontSize = 16.sp, modifier = Modifier.padding(bottom = 16.dp),color = Color(0xFF003466),fontWeight = FontWeight.Bold)

            Column {
                questions.forEachIndexed { qIndex, question ->

                    Box(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = "${qIndex + 1}. $question",
                                fontWeight = FontWeight.SemiBold
                            )

                            opciones[qIndex].forEachIndexed { oIndex, option ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = (seleccionadas[qIndex] == oIndex),
                                        onClick = { seleccionadas[qIndex] = oIndex }
                                    )
                                    Text(option)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("route") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                enabled = !seleccionadas.contains(-1)
            ) {
                Text("Evaluar mi nivel de seguridad", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEncuesta() {
    MaterialTheme {
        Surface {
            EncuestaScreen(navController = rememberNavController())
        }
    }
}