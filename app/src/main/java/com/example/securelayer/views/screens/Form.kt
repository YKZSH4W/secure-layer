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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.R
import com.example.securelayer.data.model.QuizQuestion
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.QuizActivityCard
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.viewmodel.QuizViewModel


@Composable
fun FormScreen(navController: NavController) {
    val viewModel: QuizViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadQuestions(listOf(
            QuizQuestion(
                question = "1. ¿Qué es el phishing?",
                answers = listOf("Un deporte", "Un fraude digital", "Una app"),
                rightAnswer = "Un fraude digital"
            ),
            QuizQuestion(
                question = "2. ¿Qué haces con enlaces desconocidos?",
                answers = listOf("Abrirlos", "Ignorarlos", "Borrarlos"),
                rightAnswer = "Borrarlos"
            ),
            QuizQuestion(
                question = "3. ¿Qué haces si recibes un sms del banco solicitando datos personales?",
                answers = listOf("Abrirlos", "Ignorarlos", "Borrarlos"),
                rightAnswer = "Borrarlos"
            )
        ))
    }

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        containerColor = Color(0xFFF7FAFD)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF7FAFD))
        ) {
            Text("Encuesta",
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
                Spacer(modifier = Modifier.height(10.dp))

                viewModel.questions.forEachIndexed { index, question ->
                    QuizActivityCard(
                        index = index,
                        question = question,
                        viewModel = viewModel
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomPrimaryButton(
                    text = "Enviar",
                    onClick = { navController.navigate("login") },
                    modifier = Modifier.fillMaxWidth(),
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "Icono de perfil",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewActivity() {
    FormScreen(navController = rememberNavController())
}