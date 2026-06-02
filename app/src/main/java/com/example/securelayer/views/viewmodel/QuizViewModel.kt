package com.example.securelayer.views.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // Preguntas crudas de la API (conservan la explicación para la retroalimentación)
    private var apiQuestions: List<Question> = emptyList()

    // Carga preguntas locales (usado en la encuesta inicial de Form)
    fun loadQuestions(list: List<QuizQuestion>) {
        questions = list
        selectedAnswers = mapOf()
    }

    // Carga preguntas + opciones desde la API para una actividad (una sola llamada)
    fun loadQuestionsByActivity(activityId: Int?) {
        viewModelScope.launch {
            isLoading = true
            try {
                // La API ya devuelve las preguntas ordenadas y con sus opciones anidadas.
                val response = RetrofitInstance.api.getQuestionsByActivity(activityId)
                apiQuestions = response

                val quizQuestions = response.map { question ->
                    // La respuesta correcta es el texto de la opción marcada como correcta;
                    // si no hay ninguna, se usa el campo correctAnswer de la pregunta.
                    val rightAnswer = question.options.find { it.isCorrect }?.optionText
                        ?: question.correctAnswer

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

    // Marca la actividad como completada en el backend (idempotente).
    fun markActivityCompleted(userId: Int?, activityId: Int?) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.completeActivity(
                    CompleteActivityRequest(userId, activityId)
                )
            } catch (e: Exception) {
                Log.e("Quiz", "Error al marcar actividad completada: ${e.message}")
            }
        }
    }

    // Construye la retroalimentación por pregunta para la pantalla de resultados
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
