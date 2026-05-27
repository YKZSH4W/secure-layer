package com.example.securelayer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.components.CustomOutlinedButton
import com.example.securelayer.components.CustomPrimaryButton
import com.example.securelayer.R

val SecureBlue = Color(0xFF003366)
val Background = Color(0xFFf7f7f7)

@Composable
fun WelcomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    //Generacion del encabezado de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(SecureBlue),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.shield),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(55.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "SecureLayer",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        //Generacion de la imagen
        Image(
            painter = painterResource(id = R.drawable.welcome_img),
            contentDescription = "Descripción de la imagen",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Generacion del texo
        Text(
            text = "La forma mas segura de\nnavegar en internet",
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Aprende a reconocer facilmente estafas en Internet con metodos de enseñanza interactivos y simulaciones reales.",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomPrimaryButton(
            text = "Iniciar Sesión",
            onClick = { navController.navigate("login") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Icono de perfil",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomOutlinedButton(
            text = "Registrarse",
            onClick = { navController.navigate("register") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.register),
                    contentDescription = "Icono de registro",
                    tint = SecureBlue,
                    modifier = Modifier.size(24.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    WelcomeScreen(rememberNavController())
}