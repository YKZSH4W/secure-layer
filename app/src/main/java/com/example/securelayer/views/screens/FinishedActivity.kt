package com.example.securelayer.views.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.R
import com.example.securelayer.views.theme.Background


val FinishedActivityBorderIcon = Color(0xFFe4f9f0)
@Composable
fun FinishedActivityScreen(navController: NavController){

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .border(10.dp, FinishedActivityBorderIcon, CircleShape)
                        .clip(CircleShape)
                        .background(Color(0xFF98f2bb)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.shield),
                        contentDescription = null,
                        tint = Color(0xFF32483e),
                        modifier = Modifier
                            .size(90.dp)
                    )
                }

                Spacer(modifier = Modifier.height(17.dp))

                Text("¡Excelente trabajo!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecureBlue)

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD9D9D9),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 15.dp)
                            .padding(vertical = 10.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .background(Background)
                                .padding(horizontal = 25.dp)
                                .padding(vertical = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("+50 puntos",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006d42))

                            Spacer(modifier = Modifier.height(7.dp))

                            Text("Obtenidos",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF424750))
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .background(Background)
                                .padding(horizontal = 25.dp)
                                .padding(vertical = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("10% más",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006d42))

                            Spacer(modifier = Modifier.height(7.dp))

                            Text("Protegido",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF424750))
                        }
                    }

                    Spacer(modifier = Modifier.height(7.dp))

                    Column(
                        modifier = Modifier
                            .padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Has aprendido a identificar mensajes de phishing. ¡Sigue así!",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF424750))
                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFd5e3ff))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                    ) {

                        Icon(
                            painter = painterResource(id =  R.drawable.incorrect_ic),
                            contentDescription = "Icono",
                            tint = SecureBlue,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(13.dp))

                        Text(
                            "Cada lección completada fortalece tu seguridad digital ante los estafadores",
                            fontSize = 18.sp,
                            color = SecureBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                CustomPrimaryButton(
                    text = "Siguiente lección",
                    onClick = { navController.navigate("ejercicios") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),

                    icon = {
                        Icon(
                            painter = painterResource(id =  R.drawable.right_arrow),
                            contentDescription = "Icono",
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                CustomOutlinedButton(
                    text = "Volver a la Ruta",
                    onClick = { navController.navigate("route") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),

                    icon = {
                        Icon(
                            painter = painterResource(id =  R.drawable.home),
                            contentDescription = "Icono",
                            tint = SecureBlue,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                )


            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun FinishedActivityPreview(){
    FinishedActivityScreen(rememberNavController())
}