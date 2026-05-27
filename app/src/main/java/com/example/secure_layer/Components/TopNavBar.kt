package com.example.secure_layer.Components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.secure_layer.R
//import com.example.secure_layer.Screens.ConsejoAScreen


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


