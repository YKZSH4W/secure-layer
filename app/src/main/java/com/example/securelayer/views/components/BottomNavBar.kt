package com.example.securelayer.views.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.securelayer.R

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val colorVerde = Color(0xFF2E7D32)
    val colorFondoSeleccionado = Color(0xFFE8F5E9)

    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = rutaActual == "route",
            onClick = { navController.navigate("route") },
            label = { Text("Ruta") },
            icon = { Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Icono de Inicio"
            ) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorVerde,
                selectedTextColor = colorVerde,
                indicatorColor = colorFondoSeleccionado
            )
        )

        NavigationBarItem(
            selected = rutaActual == "ejercicios",
            onClick = { navController.navigate("ejercicios") },
            label = { Text("Ejercicios") },
            icon = { Icon(
                painter = painterResource(id = R.drawable.lessons),
                contentDescription = "Icono de Ejercicios"
            )},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorVerde,
                selectedTextColor = colorVerde,
                indicatorColor = colorFondoSeleccionado
            )
        )

        NavigationBarItem(
            selected = rutaActual == "consejos",
            onClick = { navController.navigate("consejos") },
            label = { Text("Consejos") },
            icon = { Icon(
                painter = painterResource(id = R.drawable.tips),
                contentDescription = "Icono de Consejos"
            )},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorVerde,
                selectedTextColor = colorVerde,
                indicatorColor = colorFondoSeleccionado
            )
        )

        NavigationBarItem(
            selected = rutaActual == "perfil",
            onClick = { navController.navigate("perfil") },
            label = { Text("Perfil") },
            icon = { Icon(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "Icono de Perfil"
            )},
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colorVerde,
                selectedTextColor = colorVerde,
                indicatorColor = colorFondoSeleccionado
            )
        )
    }
}
