package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuizDao {

    @Query("select * from QModel where qId=:qId")
    fun getQuiz(qId: Int) : LiveData<QModel>

    @Insert
    suspend fun insertQuiz(quiz:QModel)

}