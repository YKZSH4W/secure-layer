package com.example.securelayer.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.SessionManager.currentAdviceType
import com.example.securelayer.data.SessionManager.currentRoute
import com.example.securelayer.views.components.AdviceInfoCard
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.AdvicesViewModel
import com.example.securelayer.views.viewmodel.EnrollsViewModel


@Composable
fun AdviceScreen(navController: NavController) {

    val enrollViewModel: EnrollsViewModel = viewModel()
    val adviceViewModel: AdvicesViewModel = viewModel()

    LaunchedEffect(Unit) {
        enrollViewModel.getEnrollsByUser(SessionManager.currentUser?.id)
    }

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7FAFD))
                .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(vertical = 24.dp)
                    .padding(bottom = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                currentAdviceType?.let {
                    Text(
                        it,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }


                enrollViewModel.currentRoutes.forEach { item ->
                    LaunchedEffect(item.id) {
                        adviceViewModel.getAdvicesByRoute(item.id)
                    }

                }

                adviceViewModel.advicesByRoute.forEach { item ->
                    if (item.type == currentAdviceType) {
                        AdviceInfoCard(
                            title = item.adviceTitle,
                            info = item.adviceText
                        )

                        Spacer(modifier = Modifier.height(7.dp))
                    }
                }

            }

            CustomPrimaryButton(
                text = "Entendido",
                onClick = {
                    navController.navigate("consejos")
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvicePreview(){
    AdviceScreen(rememberNavController())
}
