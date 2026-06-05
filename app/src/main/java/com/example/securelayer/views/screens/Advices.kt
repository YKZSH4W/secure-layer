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
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
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
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .background(Color(0xFFF7FAFD))
        ) {
            Text(
                text = "Conceptos de Seguridad",
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

            // Acceso a los conceptos básicos (solo lectura, con botón de regresar)
            ConsejoCard(
                title = "Conceptos Básicos",
                subtitle = "Aprende lo esencial para protegerte",
                titleColor = SecureBlue,
                iconContainerColor = SecureBlue,
                onClick = {
                    navController.navigate("BasicConcepts?fromConsejos=true")
                },
                iconContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.shield_route_ic),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            enrollViewModel.currentRoutes.forEach { item ->
                LaunchedEffect(item.id) {
                    adviceViewModel.getAdvicesByRoute(item.id)
                }

            }

            val adviceTypes = adviceViewModel.advicesByRoute
                .map { it.type }
                .distinct()

            adviceTypes.forEach { type ->

                ConsejoCard(
                    title = type,
                    subtitle = "",
                    titleColor = Color(0xFF0D47A1),
                    iconContainerColor = mapAdvicesIconColor(type),
                    onClick = {
                        SessionManager.currentAdviceType = type
                        navController.navigate("consejo_privacidad_info")
                    },
                    iconContent = {
                        Icon(
                            painter = painterResource(id = mapAdvicesIcon(type)),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

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

fun mapAdvicesIcon(icon: String): Int {
    return when (icon.lowercase()) {
        "privacidad total"   -> R.drawable.circular_key_ic
        "cuidado bancario"   -> R.drawable.bank
        "mantente alerta"    -> R.drawable.gift
        else              -> R.drawable.shield_route_ic
    }
}

fun mapAdvicesIconColor(icon:String): Color {
    return when (icon.lowercase()){
        "privacidad total"  -> Color(0xFFD6E4FF)
        "cuidado bancario"  -> Color(0xFFB9F6CA)
        "mantente alerta"   -> Color(0xFFFFE0B2)
        else -> Color(0xFFD6E4FF)
    }
}

@Preview(showBackground = true)
@Composable
fun ConsejosPreview() {
    AdvicesScreen(rememberNavController())
}

