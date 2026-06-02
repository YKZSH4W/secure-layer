package com.example.securelayer.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(navController: NavController, title: String) {
    TopAppBar(
        modifier = Modifier.background(Background),
        title = { Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = SecureBlue) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF7FAFD)
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Icono de Regreso"
                ) }
        },
        actions = {
            if(SessionManager.currentUser != null) {
                IconButton(onClick = { navController.navigate("perfil") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.configuration),
                        contentDescription = "Icono de Configuracion"
                    )
                }
            }
        }
    )
}


