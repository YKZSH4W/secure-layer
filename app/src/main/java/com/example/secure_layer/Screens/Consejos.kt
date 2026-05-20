package com.example.secure_layer.Screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.secure_layer.Components.BottomNavBar
import com.example.secure_layer.Components.TopNavBar
import com.example.secure_layer.R

@Composable
fun ConsejosScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopNavBar(navController = navController, title = "SecureLayer")
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        containerColor = Color(0xFFF8FAFC)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            // Encabezado
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

            //Tarjeta Privacidad Total
            ConsejoCard(
                title = "Privacidad total",
                subtitle = "Nunca compartas tus\ncontraseñas",
                titleColor = Color(0xFF0D47A1),
                iconContainerColor = Color(0xFFD6E4FF),
                iconContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.key),
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
                    .height(130.dp)
                    .clickable {
                        val urlIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ&pp=ygUIcmlja3JvbGw%3D") // Reemplaza con tu link real
                        )
                        context.startActivity(urlIntent)
                    },
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

@Composable
fun ConsejoCard(
    title: String,
    subtitle: String,
    titleColor: Color,
    iconContainerColor: Color,
    onClick: () -> Unit = {},
    iconContent: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(iconContainerColor),
                    contentAlignment = Alignment.Center
                ) {
                    iconContent()
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = titleColor)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = subtitle, fontSize = 12.sp, color = Color.DarkGray, lineHeight = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ver más", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Color(0xFF37474F))
                Icon(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "Ir",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}