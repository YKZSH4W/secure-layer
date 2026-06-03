package com.example.securelayer.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.securelayer.data.model.Activity
import com.example.securelayer.data.model.Lesson
import com.example.securelayer.data.model.QuizFeedbackItem
import com.example.securelayer.data.model.Route
import com.example.securelayer.data.model.User

object SessionManager {
    var currentUser: User? by mutableStateOf(null)
    var currentRoute: Route? by mutableStateOf(null)
    var currentAdviceType: String? by mutableStateOf(null)
    var currentLesson: Lesson? by mutableStateOf(null)
    var currentActivity: Activity? by mutableStateOf(null)
    // Indica si la actividad abierta ya había sido completada antes (para no dar puntos de nuevo)
    var currentActivityCompleted: Boolean by mutableStateOf(false)

    // Actividades de la lección actual (ordenadas) y cuáles ya completó el usuario.
    // Se usan para navegar a la "siguiente actividad" desde la pantalla de resultados.
    var currentLessonActivities: List<Activity> by mutableStateOf(emptyList())
    var currentLessonCompletedIds: Set<Int> by mutableStateOf(emptySet())

    // Se incrementa al completar una actividad para forzar que la ruta recargue el progreso
    var progressRefreshTrigger: Int by mutableStateOf(0)

    // Título de la medalla recién desbloqueada (para mostrar el diálogo en resultados)
    var newlyUnlockedMedalTitle: String? by mutableStateOf(null)

    // Resultado del último quiz enviado (usado en la pantalla de retroalimentación)
    var lastQuizScore: Int by mutableStateOf(0)
    var lastQuizTotal: Int by mutableStateOf(0)
    var lastQuizEarnedXp: Int by mutableStateOf(0)
    var lastQuizFeedback: List<QuizFeedbackItem> by mutableStateOf(emptyList())

    fun logout() {
        currentUser = null
        currentRoute = null
        currentLesson = null
        currentActivity = null
        currentActivityCompleted = false
        currentLessonActivities = emptyList()
        currentLessonCompletedIds = emptySet()
        lastQuizScore = 0
        lastQuizTotal = 0
        lastQuizEarnedXp = 0
        lastQuizFeedback = emptyList()
        newlyUnlockedMedalTitle = null
    }
}