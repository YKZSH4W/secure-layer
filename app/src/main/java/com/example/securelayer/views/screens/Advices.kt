package com.example.securelayer.views.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.ConsejoCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.viewmodel.AdvicesViewModel
import com.example.securelayer.views.viewmodel.EnrollsViewModel

@Composable
fun AdvicesScreen(navController: NavController) {
    val enrollViewModel: EnrollsViewModel = viewModel()
    val adviceViewModel: AdvicesViewModel = viewModel()

    LaunchedEffect(Unit) {
        enrollViewModel.getEnrollsByUser(SessionManager.currentUser?.id)
    }


    Scaffold(
        topBar = {
            TopNavBar(navController = navController, title = "SecureLayer")
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Consejos de Seguridad",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Aprenda a protegerse en el mundo digital con estos sencillos pasos.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            enrollViewModel.currentRoutes.forEach { item ->
                LaunchedEffect(item.id) {
                    adviceViewModel.getAdvicesByRoute(item.id)
                }

            }

            adviceViewModel.advicesByRoute.forEach { item ->
                Text("Id: ${item.id}, RouteId: ${item.routeId}, AdviceTitle: ${item.adviceTitle}")
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Tarjeta Privacidad Total
            ConsejoCard(
                title = "Privacidad total",
                subtitle = "Nunca compartas tus\ncontraseñas",
                titleColor = Color(0xFF0D47A1),
                iconContainerColor = Color(0xFFD6E4FF),
                onClick = { navController.navigate("consejo privacidad info") },
                iconContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.circular_key_ic),
                        contentDescription = "Llave",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            //Tarjeta Cuidado Bancario
            ConsejoCard(
                title = "Cuidado bancario",
                subtitle = "Los bancos no piden códigos\npor SMS",
                titleColor = Color(0xFF00695C),
                iconContainerColor = Color(0xFFB9F6CA),
                onClick = { navController.navigate("consejo info") },
                iconContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.bank),
                        contentDescription = "Banco",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            //Tarjeta Mantente Alerta
            ConsejoCard(
                title = "Mantente alerta",
                subtitle = "Duda de los premios\ninesperados",
                titleColor = Color(0xFF5D4037),
                iconContainerColor = Color(0xFFFFE0B2),
                onClick = { navController.navigate("consejo info") },
                iconContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.gift),
                        contentDescription = "Regalo",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.saber_mas),
                        contentDescription = "Fondo de teléfono",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Saber más",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "¿Cómo detectar una estafa telefónica?",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsejosPreview() {
    AdvicesScreen(rememberNavController())
}

