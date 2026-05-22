package com.example.secure_layer.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.secure_layer.Components.CustomTextField
import com.example.secure_layer.R

@Composable
fun RegisterScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showEmptyError by remember { mutableStateOf(false) }

    val SecureBlue = Color(0xFF003366)
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9F9F9))) {

        Box(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(size.width, 0f)
                    lineTo(size.width, size.height * 0.7f)
                    quadraticTo(size.width * 0.5f, size.height, 0f, size.height * 0.7f)
                    close()
                }
                drawPath(path, color = SecureBlue)
            }

            Row(
                modifier = Modifier.padding(top = 50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.shield),
                    contentDescription = "Logo",
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "SecureLayer",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registrarse",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = SecureBlue
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(nombre, { nombre = it }, "Nombre Completo", showEmptyError && nombre.isEmpty())
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(correo, { correo = it }, "Correo Electrónico", showEmptyError && correo.isEmpty(), KeyboardType.Email)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(password, { password = it }, "Contraseña", showEmptyError && password.isEmpty(), isPassword = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(confirmPassword, { confirmPassword = it }, "Confirmar Contraseña", showEmptyError && confirmPassword.isEmpty(), isPassword = true)

            if (showEmptyError) {
                Text("Por favor, completa todos los campos", color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && correo.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()) {
                        navController.navigate("route")
                    } else {
                        showEmptyError = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SecureBlue)
            ) {
                Text("Registrarse", color = Color.White, fontSize = 16.sp)
            }

            OutlinedButton(
                onClick = { navController.navigate("welcome") },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp).height(55.dp)
            ) {
                Text("Cancelar", color = SecureBlue)
            }

            TextButton(onClick = { navController.navigate("login") }, modifier = Modifier.padding(top = 8.dp)) {
                Text("¿Ya tienes cuenta? Inicia Sesión aquí")
            }
        }
    }
}