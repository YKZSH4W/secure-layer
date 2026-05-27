package com.example.securelayer.views.screens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.CustomTextField
import com.example.securelayer.R
import com.example.securelayer.views.viewmodel.UsersViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: UsersViewModel = viewModel()
    var showErrorDialog by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showEmptyError by remember { mutableStateOf(false) }

    val SecureBlue = Color(0xFF003366)
    val scrollState = rememberScrollState()

    LaunchedEffect(viewModel.loginError) {
        if (viewModel.loginError) {
            showErrorDialog = true
            viewModel.resetLoginError()
        }
    }

    LaunchedEffect(viewModel.loginSuccess) {
        if (viewModel.loginSuccess) {
            navController.navigate("route")
        }
    }

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
                Text("SecureLayer", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(vertical = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text("Iniciar Sesión", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = SecureBlue)
            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(email, { email = it }, "Correo Electrónico", showEmptyError && email.isEmpty(), KeyboardType.Email)
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(password, { password = it }, "Contraseña", showEmptyError && password.isEmpty(), isPassword = true)

            if (showEmptyError) {
                Text("Por favor, completa todos los campos", color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
            }

            if (showErrorDialog) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog = false },
                    title = { Text("Error") },
                    text = { Text("Credenciales inválidas, intenta de nuevo.") },
                    confirmButton = {
                        TextButton(onClick = { showErrorDialog = false }) {
                            Text("Aceptar")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(
                onClick = { navController.navigate("register") },
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF424750))
            ) {
                Text("¿No tienes cuenta? Regístrate aquí", style = TextStyle(textDecoration = TextDecoration.Underline))
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomPrimaryButton(
                text = "Iniciar Sesión",
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login(email, password)
                    } else {
                        showEmptyError = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Icono",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomOutlinedButton(
                text = "Cancelar",
                onClick = { navController.navigate("welcome")},
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel_ic),
                        contentDescription = "Cancelar",
                        tint = SecureBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen(navController = rememberNavController())
}