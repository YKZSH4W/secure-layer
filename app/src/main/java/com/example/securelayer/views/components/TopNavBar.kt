package com.example.securelayer.views.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.securelayer.R
//import com.example.securelayer.Screens.ConsejoAScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(navController: NavController, title: String) {
    TopAppBar(
        title = { Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = SecureBlue) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Icono de Regreso"
                ) }
        },
        actions = {
            IconButton(onClick = { navController.navigate("") }) {
                Icon(
                    painter = painterResource(id = R.drawable.configuration),
                    contentDescription = "Icono de Configuracion"
                ) }
        }
    )
}


