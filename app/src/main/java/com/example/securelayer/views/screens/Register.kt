package com.example.securelayer.views.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: UsersViewModel = viewModel()

    // Variables para el form
    var username by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var registerUser by remember { mutableStateOf(false) }

    // Estados para la fecha
    var birthDate by remember { mutableStateOf("Fecha de nacimiento") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    // Variables para dialogs
    var showEmptyError by remember { mutableStateOf(false) }
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
            val parts = name.trim().split(" ")
            val part = if (parts.size == 3) 1 else 2

            viewModel.createUser(
                email,
                username,
                password,
                parts.dropLast(part).joinToString(" "),
                parts.takeLast(2).joinToString(" "),
                birthDate
            )
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
            CustomTextField(username, { username = it }, "Nombre de Usuario", showEmptyError && username.isEmpty())
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(email, { email = it }, "Correo Electrónico", showEmptyError && email.isEmpty(), KeyboardType.Email)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(password, { password = it }, "Contraseña", showEmptyError && password.isEmpty(), isPassword = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(confirmPassword, { confirmPassword = it }, "Confirmar Contraseña", showEmptyError && confirmPassword.isEmpty(), isPassword = true)

            Spacer(modifier = Modifier.height(20.dp))

            // Selector de fecha
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(
                    1.dp,
                    if (showEmptyError && birthDate == "Fecha de nacimiento") Color(0xFFB3261E) else SecureBlue
                ),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = birthDate,
                        color = when {
                            showEmptyError && birthDate == "Fecha de nacimiento" -> Color(0xFFB3261E)
                            birthDate == "Fecha de nacimiento" -> Color.Gray
                            else -> Color.Black
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = SecureBlue)
                }
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            datePickerState.selectedDateMillis?.let { birthDate = convertMillisToDate(it) }
                            showDatePicker = false
                        }) { Text("Aceptar") }
                    }
                ) { DatePicker(state = datePickerState) }
            }

            if (showEmptyError) {
                Text("Por favor, completa todos los campos", color = Color(0xFFB3261E))
            }

            if (showSuccessDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showSuccessDialog = false
                        registerUser = false
                        navController.navigate("BasicConcepts")
                    },
                    title = { Text("Registro exitoso") },
                    text = { Text("La cuenta se creo correctamente.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showSuccessDialog = false
                            registerUser = false
                            navController.navigate("BasicConcepts")
                        }) {
                            Text("Aceptar")
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
                    if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() &&
                        confirmPassword.isNotBlank() && username.isNotBlank() && birthDate != "Fecha de nacimiento"
                    ) {
                        registerUser = true
                    } else {
                        showEmptyError = true
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

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen(navController = rememberNavController())
}