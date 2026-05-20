package com.example.secure_layer.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.secure_layer.R
val SecureBlue = Color(0xFF003366)

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
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

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = "La forma mas segura de\nnavegar en internet",
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Color.Black,
            lineHeight = 34.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Aprende a reconocer facilmente estafas en Internet con metodos de enseñanza interactivos y simulaciones reales.",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 40.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate("main") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SecureBlue),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Iniciar Sesion", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { navController.navigate("main") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(60.dp),
            border = BorderStroke(2.dp, SecureBlue),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.register),
                contentDescription = null,
                tint = SecureBlue,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("Registrarse", color = SecureBlue, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}