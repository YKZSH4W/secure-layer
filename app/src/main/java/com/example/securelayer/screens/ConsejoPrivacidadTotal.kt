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
import com.example.securelayer.components.AdviceInfoCard
import com.example.securelayer.components.BottomNavBar
import com.example.securelayer.components.CustomPrimaryButton
import com.example.securelayer.components.TopNavBar


@Composable
fun ConsejoPrivacidadTotalScreen(navController: NavController){

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            CustomPrimaryButton(
                text = "Entendido",
                onClick = { navController.navigate("consejos") },
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

                Text("Privacidad Total",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)

                Spacer(modifier = Modifier.height(17.dp))

                AdviceInfoCard(
                    "La regla de oro de las contraseñas: ",
                    "Una contraseña es como la llave de tu casa. Nunca la compartas con nadie, ni siquiera con alguien que diga ser de soporte técnico\".\n" +
                            "\". Intenta que sean frases largas y fáciles de recordar para ti, pero dificiles para los demas."
                )

                Spacer(modifier = Modifier.height(7.dp))

                AdviceInfoCard(
                    "La verificación en dos pasos (Tu segunda capa):",
                    "Es como tener una cerradura extra en la puerta. Cuando inicies sesión en un lugar nuevo, el sistema te enviarà un código a tu teléfono para confirmar que eres tu.\n" +
                            "¡Activa siempre esta opción, es tu mayor seguridad!"
                )

                Spacer(modifier = Modifier.height(7.dp))

                AdviceInfoCard(
                    "La verificación en dos pasos (Tu segunda capa):",
                    "Es como tener una cerradura extra en la puerta. Cuando inicies sesión en un lugar nuevo, el sistema te enviarà un código a tu teléfono para confirmar que eres tu.\n" +
                            "¡Activa siempre esta opción, es tu mayor seguridad!"
                )
                Spacer(modifier = Modifier.height(45.dp))
            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun ConsejoPrivacidadTotalPreview(){
    ConsejoPrivacidadTotalScreen(rememberNavController())
}
