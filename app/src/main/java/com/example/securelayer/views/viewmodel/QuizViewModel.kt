package com.example.securelayer.views.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.securelayer.data.model.QuizQuestion

class QuizViewModel : ViewModel() {
    var questions by mutableStateOf(emptyList<QuizQuestion>())
        private set
    var selectedAnswers by mutableStateOf(mapOf<Int, String>())
        private set

    fun loadQuestions(list: List<QuizQuestion>) {
        questions = list
        selectedAnswers = mapOf()
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
}