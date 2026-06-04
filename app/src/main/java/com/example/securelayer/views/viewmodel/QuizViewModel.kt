package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securelayer.data.SessionManager
import com.example.securelayer.data.model.AttemptRequest
import com.example.securelayer.data.model.CompleteActivityRequest
import com.example.securelayer.data.model.Question
import com.example.securelayer.data.model.QuizFeedbackItem
import com.example.securelayer.data.model.QuizQuestion
import com.example.securelayer.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    var questions by mutableStateOf(emptyList<QuizQuestion>())
        private set
    var selectedAnswers by mutableStateOf(mapOf<Int, String>())
        private set
    var isLoading by mutableStateOf(false)
        private set

    private var apiQuestions: List<Question> = emptyList()

    fun loadQuestions(list: List<QuizQuestion>) {
        questions = list
        selectedAnswers = mapOf()
    }

    fun loadQuestionsByActivity(activityId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitInstance.api.getQuestionsByActivity(activityId)
                apiQuestions = response

                val quizQuestions = response.map { question ->
                    // La respuesta correcta es el texto de la opción marcada como correcta
                    val rightAnswer = question.options.find { it.isCorrect }?.optionText ?: ""

                    QuizQuestion(
                        question = question.questionText,
                        answers = question.options.map { it.optionText },
                        rightAnswer = rightAnswer
                    )
                }

                questions = quizQuestions
                selectedAnswers = mapOf()
            } catch (e: Exception) {
                Log.e("Quiz", "Error al cargar el quiz: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun selectAnswer(questionIndex: Int, answer: String) {
        selectedAnswers += (questionIndex to answer)
    }

    fun validateAnswers(): Int {
        var correct = 0

        questions.forEachIndexed { index, question ->
            if (selectedAnswers[index] == question.rightAnswer) correct++
        }

        return correct
    }

    // Persiste en el backend que la actividad fue completada (la XP en sesión
    // ya se sumó localmente en el momento del envío).
    fun markActivityCompleted(userId: Int?, activityId: Int?) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.api.completeActivity(
                    CompleteActivityRequest(userId, activityId)
                )
                // El backend otorga las medallas; mostramos la primera nueva (si la hay)
                SessionManager.newlyUnlockedAchievement = result.newAchievements.firstOrNull()
            } catch (e: Exception) {
                Log.e("Quiz", "Error al marcar actividad completada: ${e.message}")
            }
        }
    }

    // Registra cada intento del usuario en el historial (tabla attempts).
    // score = porcentaje de aciertos (0-100); isCorrect = todas correctas.
    fun registerAttempt(userId: Int?, activityId: Int?, isCorrect: Boolean, score: Int) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.createAttempt(
                    AttemptRequest(userId, activityId, isCorrect, score)
                )
            } catch (e: Exception) {
                Log.e("Quiz", "Error al registrar intento: ${e.message}")
            }
        }
    }

    fun buildFeedback(): List<QuizFeedbackItem> {
        return questions.mapIndexed { index, question ->
            val userAnswer = selectedAnswers[index] ?: ""
            QuizFeedbackItem(
                question = question.question,
                userAnswer = userAnswer,
                correctAnswer = question.rightAnswer,
                isCorrect = userAnswer == question.rightAnswer,
                explanation = apiQuestions.getOrNull(index)?.explanation ?: ""
            )
        }
    }
}
