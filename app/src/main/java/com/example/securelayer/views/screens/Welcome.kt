package com.example.securelayer.views.screens

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
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.R
import com.example.securelayer.views.components.secureLayerLogo
import com.example.securelayer.views.theme.SecureBlue

val Background = Color(0xFFf7f7f7)

@Composable
fun WelcomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    //Generacion del encabezado de la pantalla
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF9F9F9))) {
        secureLayerLogo()

        Spacer(modifier = Modifier.height(10.dp))

        //Generacion de la imagen
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_img),
                contentDescription = "Descripción de la imagen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "La forma mas segura de\nnavegar en internet",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Aprende a reconocer facilmente estafas en Internet con metodos de enseñanza interactivos y simulaciones reales.",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomPrimaryButton(
                text = "Iniciar Sesión",
                onClick = { navController.navigate("login") },
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.welcome_user_ic),
                        contentDescription = "Icono de perfil",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlinedButton(
                text = "Registrarse",
                onClick = { navController.navigate("register") },
                modifier = Modifier.fillMaxWidth(),
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.register),
                        contentDescription = "Icono de registro",
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
fun WelcomePreview() {
    WelcomeScreen(rememberNavController())
}