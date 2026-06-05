package com.example.securelayer.views.screens

import android.util.Patterns
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.securelayer.views.components.secureLayerLogo
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.UsersViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: UsersViewModel = viewModel()

    // Variables para el form
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var registerUser by remember { mutableStateOf(false) }

    // Variables para dialogs
    var showEmptyError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }
    var showEmailFormatError by remember { mutableStateOf(false) }
    var showPasswordFormatError by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Variables para cuestiones visuales
    val scrollState = rememberScrollState()

    LaunchedEffect(viewModel.registerSuccess) {
        if(viewModel.registerSuccess) {
            showSuccessDialog = true
            viewModel.resetRegisterSuccess()
        }
    }

    LaunchedEffect(registerUser) {
        if (registerUser) {
            // Se envía el nombre completo tal cual lo escribió el usuario
            viewModel.createUser(email, password, name.trim())

            // Permite reintentar el registro tras un error
            registerUser = false
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9F9F9))) {
        secureLayerLogo()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registrarse", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = SecureBlue)
            Spacer(modifier = Modifier.height(20.dp))

            // Campos del formulario
            CustomTextField(name, { name = it }, "Nombre Completo", showEmptyError && name.isEmpty())
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(email, { email = it }, "Correo Electrónico", (showEmptyError && email.isEmpty()) || showEmailFormatError || viewModel.registerErrorMessage != null, KeyboardType.Email)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(password, { password = it }, "Contraseña", (showEmptyError && password.isEmpty()) || showPasswordFormatError, isPassword = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(confirmPassword, { confirmPassword = it }, "Confirmar Contraseña", (showEmptyError && confirmPassword.isEmpty()) || showPasswordError, isPassword = true)

            Spacer(modifier = Modifier.height(20.dp))

            if (showEmptyError) {
                Text("Por favor, completa todos los campos", color = Color(0xFFB3261E))
            }

            if (showEmailFormatError) {
                Text("Ingresa un correo electrónico válido", color = Color(0xFFB3261E))
            }

            if (showPasswordFormatError) {
                Text(
                    "La contraseña debe tener al menos 6 caracteres y un símbolo especial",
                    color = Color(0xFFB3261E)
                )
            }

            if (showPasswordError) {
                Text("Las contraseñas no coinciden", color = Color(0xFFB3261E))
            }

            // Error devuelto por el servidor (p. ej. correo ya registrado)
            viewModel.registerErrorMessage?.let { msg ->
                Text(msg, color = Color(0xFFB3261E))
            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("¡Cuenta creada!") },
                    text = {
                        Text(
                            "Tu cuenta se creó correctamente.\n\n" +
                                    "¿Quieres aprender los conceptos básicos de seguridad y " +
                                    "responder una breve encuesta para conocer tu nivel?"
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showSuccessDialog = false
                            registerUser = false
                            navController.navigate("BasicConcepts")
                        }) {
                            Text("Sí, aprender")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showSuccessDialog = false
                            registerUser = false
                            navController.navigate("route")
                        }) {
                            Text("Omitir")
                        }
                    }
                )
            }

            TextButton(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF424750))
            ) {
                Text("¿Ya tienes cuenta? Inicia Sesión aquí", style = TextStyle(textDecoration = TextDecoration.Underline))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomPrimaryButton(
                text = "Registrarse",
                onClick = {
                    // Correo válido (formato estándar)
                    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
                    // Contraseña: mínimo 6 caracteres y al menos un símbolo especial
                    val hasSpecialChar = password.any { !it.isLetterOrDigit() }
                    val isPasswordValid = password.length >= 6 && hasSpecialChar

                    when {
                        name.isBlank() || email.isBlank() || password.isBlank() ||
                                confirmPassword.isBlank() -> {
                            showEmptyError = true
                            showEmailFormatError = false
                            showPasswordFormatError = false
                            showPasswordError = false
                        }
                        !isEmailValid -> {
                            showEmailFormatError = true
                            showEmptyError = false
                            showPasswordFormatError = false
                            showPasswordError = false
                        }
                        !isPasswordValid -> {
                            showPasswordFormatError = true
                            showEmptyError = false
                            showEmailFormatError = false
                            showPasswordError = false
                        }
                        password != confirmPassword -> {
                            showPasswordError = true
                            showEmptyError = false
                            showEmailFormatError = false
                            showPasswordFormatError = false
                        }
                        else -> {
                            showEmptyError = false
                            showEmailFormatError = false
                            showPasswordFormatError = false
                            showPasswordError = false
                            viewModel.resetRegisterError()   // limpia error previo antes de reintentar
                            registerUser = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.register),
                        contentDescription = "Icono de registro",
                        modifier = Modifier.size(24.dp),
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedButton(
                text = "Cancelar",
                onClick = { navController.navigate("welcome") },
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel_ic),
                        contentDescription = "Cancelar",
                        tint = SecureBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen(navController = rememberNavController())
}
