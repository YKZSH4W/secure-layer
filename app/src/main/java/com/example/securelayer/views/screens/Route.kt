package com.example.securelayer.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.views.components.BottomNavBar
import com.example.securelayer.views.components.LearningNode
import com.example.securelayer.views.components.TipOfDayCard
import com.example.securelayer.R
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.theme.SecureGreen
import com.example.securelayer.views.viewmodel.EnrollsViewModel
import com.example.securelayer.views.viewmodel.LessonsViewModel

@Composable
fun RouteScreen(navController: NavController) {
    val enrollsViewModel: EnrollsViewModel = viewModel()
    val lessonsViewModel: LessonsViewModel = viewModel()

    val currentRoute = SessionManager.currentRoute

    // 1. Carga los enrollments del usuario → esto setea SessionManager.currentRoute
    LaunchedEffect(Unit) {
        enrollsViewModel.getEnrollsByUser(SessionManager.currentUser?.id)
    }

    // 2. Carga las lecciones con su progreso. Se recarga cuando cambia la ruta
    //    o cuando se completa una actividad (progressRefreshTrigger).
    LaunchedEffect(currentRoute, SessionManager.progressRefreshTrigger) {
        currentRoute?.id?.let { routeId ->
            lessonsViewModel.getLessonsByRoute(routeId, SessionManager.currentUser?.id)
        }
    }

    // Selecciona automáticamente la lección actual (primera no completada) si no hay
    // ninguna seleccionada — por ejemplo, al entrar o al avanzar de ruta.
    LaunchedEffect(lessonsViewModel.lessons) {
        if (SessionManager.currentLesson == null && lessonsViewModel.lessons.isNotEmpty()) {
            SessionManager.currentLesson =
                lessonsViewModel.lessons.firstOrNull { !it.isCompleted }
                    ?: lessonsViewModel.lessons.first()
        }
    }

    // Índice de la primera lección no completada (es la lección actual)
    val firstUncompletedIndex = lessonsViewModel.lessons.indexOfFirst { !it.isCompleted }

    // 3. Detecta cuando TODAS las lecciones de la ruta están completas
    val allLessonsCompleted = lessonsViewModel.lessons.isNotEmpty() &&
            lessonsViewModel.lessons.all { it.isCompleted }

    // Recuerda la última ruta para la que ya se pulsó "Continuar",
    // para no volver a mostrar el diálogo de esa misma ruta.
    var advancedRouteId by remember { mutableStateOf<Int?>(null) }

    // Se muestra siempre que la ruta esté completa y aún no se haya avanzado de ella
    // (no depende de detectar una transición, así es robusto a recomposiciones).
    val showRouteCompletedDialog = allLessonsCompleted &&
            currentRoute != null &&
            advancedRouteId != currentRoute.id

    if (showRouteCompletedDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("¡Ruta completada!") },
            text = {
                Text("Has terminado todas las lecciones de \"${currentRoute?.name}\". ¡Pasarás a la siguiente ruta!")
            },
            confirmButton = {
                TextButton(onClick = {
                    advancedRouteId = currentRoute?.id
                    enrollsViewModel.completeRouteAndAdvance(
                        SessionManager.currentUser?.id,
                        currentRoute?.id
                    )
                }) {
                    Text("Continuar")
                }
            }
        )
    }

    Scaffold(
        containerColor = Background,
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .background(Background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = currentRoute?.name ?: "Ruta",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = SecureBlue
            )
            Text(
                text = currentRoute?.description ?: "",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            when {
                lessonsViewModel.isLoading -> {
                    CircularProgressIndicator(
                        color = SecureBlue,
                        modifier = Modifier.padding(32.dp)
                    )
                }
                lessonsViewModel.lessons.isEmpty() -> {
                    Text(
                        text = "No hay lecciones disponibles para esta ruta.",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
                else -> {
                    lessonsViewModel.lessons.forEachIndexed { index, lesson ->
                        val isCurrent = index == firstUncompletedIndex
                        // Bloqueada si aún hay lecciones sin completar y esta está después de la actual
                        val isLocked = firstUncompletedIndex != -1 && index > firstUncompletedIndex

                        LearningNode(
                            iconId = mapLessonIcon(lesson.icon),
                            label = if (isCurrent) "${lesson.name}\n¡ESTÁS AQUÍ!" else lesson.name,
                            containerColor = if (lesson.isCompleted) SecureGreen else SecureBlue,
                            isLocked = isLocked,
                            isCurrent = isCurrent,
                            onClick = {
                                SessionManager.currentLesson = lesson
                                navController.navigate("ejercicios")
                            },
                            // Alternar nodos: par → empuja a la izquierda, impar → empuja a la derecha
                            modifier = Modifier.padding(
                                start = if (index % 2 != 0) 200.dp else 0.dp,
                                end = if (index % 2 == 0) 200.dp else 0.dp
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TipOfDayCard(
                title = "Consejo del Día",
                description = "Nunca compartas tus claves.",
                iconContent = {
                    Image(
                        painter = painterResource(id = R.drawable.tip_ic),
                        contentDescription = null
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

fun mapLessonIcon(icon: String): Int {
    return when (icon.lowercase()) {
        "email", "mail"   -> R.drawable.mail_route_ic
        "shield"          -> R.drawable.shield_route_ic
        "key"             -> R.drawable.key_ic
        "message"         -> R.drawable.message_dots
        "lock"            -> R.drawable.lock_ic
        else              -> R.drawable.shield_route_ic
    }
}

@Preview(showBackground = true)
@Composable
fun RoutePreview() {
    RouteScreen(navController = rememberNavController())
}
