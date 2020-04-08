package com.example.quizapp

import androidx.lifecycle.LiveData

class QuizRepository(val quizDao: QuizDao,qid:Int){

    val allQuiz : LiveData<QModel> = quizDao.getQuiz(qid)

    suspend fun insertQuiz(quiz:QModel){
        quizDao.insertQuiz(quiz)
    }
}