package com.example.securelayer.views.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.R
import com.example.securelayer.views.components.BasicConceptCard
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background


@Composable
fun BasicConceptsScreen(navController: NavController, fromConsejos: Boolean = false){

    Scaffold(
        containerColor = Color(0xFFF7FAFD),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Conceptos Básicos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(start = 24.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicConceptCard(
                    title = "¿Qué es el Phishing?",
                    concept = "Es cuando alguien intenta engañarte haciéndose pasar por una empresa, banco o persona conocida",
                    advice = "Cuando te pidan actuar rápido es muy probable que sea una estafa",
                    rule = "El banco nunca te pedirá datos personales"
                )

                Spacer(modifier = Modifier.height(7.dp))

                BasicConceptCard(
                    title ="La importancia de la contraseña fuerte",
                    concept = """
                        Tu contraseña es la llave de tu casa digital. Si es fácil 
                        (como \"1234\" o tu fecha de nacimiento), cualquier 
                        \"ladrón\" puede entrar.
                    """.trimIndent(),
                    advice = "Usa frases largas que solo tú recuerdes, combinando letras, números y algún símbolo especial.",
                    rule = "Nunca uses la misma contraseña para todo. Si una se filtra, todas están en riesgo."
                )

                Spacer(modifier = Modifier.height(7.dp))

                BasicConceptCard(
                    title = "El peligro de los enlaces desconocidos",
                    concept = """
                        Los enlaces (esos textos azules o cortos que dicen \"haz clic aquí\") son puertas. 
                        Si haces clic en una puerta desconocida, puedes invitar a un virus a entrar en tu teléfono.
                    """.trimIndent(),
                    advice = "Si no sabes quién envió el mensaje o por qué, no hagas clic.",
                    rule = """
                        En caso de duda, borra. Es mejor preguntar a un familiar 
                        o llamar directamente al banco antes de abrir algo sospechoso.
                    """.trimIndent()
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (fromConsejos) {
                    // Acceso desde la pantalla de Consejos: solo permite regresar
                    CustomPrimaryButton(
                        text = "Regresar",
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = "Icono de regresar",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    )
                } else {
                    // Flujo de registro: continúa a la encuesta para medir el nivel
                    CustomPrimaryButton(
                        text = "Probar mi Nivel",
                        onClick = { navController.navigate("form") },
                        modifier = Modifier.fillMaxWidth(),
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.pencil),
                                contentDescription = "Icono de lapiz",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    )
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun BasicConceptsPreview(){
    BasicConceptsScreen(rememberNavController())
}
