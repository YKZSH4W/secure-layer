package com.example.secure_layer.Screens

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.secure_layer.Components.CustomOutlinedButton
import com.example.secure_layer.Components.CustomPrimaryButton
import com.example.secure_layer.Components.CustomTextField
import com.example.secure_layer.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Estados para la fecha
    var birthDate by remember { mutableStateOf("Fecha de nacimiento") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var showEmptyError by remember { mutableStateOf(false) }

    val SecureBlue = Color(0xFF003366)
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9F9F9))) {

        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.TopCenter) {
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
            Row(modifier = Modifier.padding(top = 50.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(id = R.drawable.shield), contentDescription = "Logo", modifier = Modifier.size(50.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("SecureLayer", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registrarse", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = SecureBlue)
            Spacer(modifier = Modifier.height(20.dp))

            // TextField personalizados
            CustomTextField(nombre, { nombre = it }, "Nombre Completo", showEmptyError && nombre.isEmpty())
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(correo, { correo = it }, "Correo Electrónico", showEmptyError && correo.isEmpty(), KeyboardType.Email)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(password, { password = it }, "Contraseña", showEmptyError && password.isEmpty(), isPassword = true)
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(confirmPassword, { confirmPassword = it }, "Confirmar Contraseña", showEmptyError && confirmPassword.isEmpty(), isPassword = true)

            // Selector de fecha
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (showEmptyError && birthDate == "Fecha de nacimiento") Color.Red else Color.Gray)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = birthDate, color = if(birthDate == "Fecha de nacimiento") Color.Gray else Color.Black, modifier = Modifier.weight(1f))
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
                Text("Por favor, completa todos los campos", color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
            }

            TextButton(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF424750))
            ) {
                Text("¿Ya tienes cuenta? Inicia Sesión aquí", style = TextStyle(textDecoration = TextDecoration.Underline))
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomPrimaryButton(
                text = "Registrarse",
                onClick = {
                    if (nombre.isNotBlank() && correo.isNotBlank() && password.isNotBlank() &&
                        confirmPassword.isNotBlank() && birthDate != "Fecha de nacimiento") {
                        navController.navigate("form")
                    } else {
                        showEmptyError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            CustomOutlinedButton(
                text = "Cancelar",
                onClick = { navController.navigate("welcome") },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
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