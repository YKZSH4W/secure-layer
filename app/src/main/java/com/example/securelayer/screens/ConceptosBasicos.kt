package com.example.securelayer.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.components.BasicConceptCard
import com.example.securelayer.components.BottomNavBar
import com.example.securelayer.components.CustomPrimaryButton
import com.example.securelayer.components.TopNavBar


@Composable
fun ConseptosBasicosScreen(navController: NavController){

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            CustomPrimaryButton(
                text = "Volver",
                onClick = { navController.navigate("route") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Conceptos Básicos",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)

                Spacer(modifier = Modifier.height(17.dp))

                BasicConceptCard(
                    "¿Qué es el Phishing?",
                    "Es cuando alguien intenta engañarte haciéndose pasar por una empresa, banco o persona conocida",
                    "Siempre te piden actuar ráoido",
                    "El banco nunca te pedirá datos personales"
                )

                Spacer(modifier = Modifier.height(7.dp))

                BasicConceptCard(
                    "La importancia de lacontraseña fuerte",
                    "Es cuando alguien intenta engañarte haciéndose pasar por una empresa, banco o persona conocida",
                    "Siempre te piden actuar ráoido",
                    "El banco nunca te pedirá datos personales"
                )

                Spacer(modifier = Modifier.height(7.dp))

                BasicConceptCard(
                    "El peligro de los enlaces desconocidos",
                    "Es cuando alguien intenta engañarte haciéndose pasar por una empresa, banco o persona conocida",
                    "Siempre te piden actuar ráoido",
                    "El banco nunca te pedirá datos personales"
                )

                Spacer(modifier = Modifier.height(45.dp))
            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun ConseptosBasicosPreview(){
    ConseptosBasicosScreen(rememberNavController())
}
