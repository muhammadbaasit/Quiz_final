package com.example.quizapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[(QModel::class)],version = 1)
abstract class QuizDb : RoomDatabase(){

    abstract fun quizDao() : QuizDao

    companion object{

        @Volatile
        private var INSTANCE : QuizDb? = null

        fun getInstance(context: Context) : QuizDb{

            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }

            synchronized(QuizDb::class){
                val instance = Room.databaseBuilder(context.applicationContext,
                    QuizDb::class.java,"quizdb").build()

                INSTANCE = instance

                return instance
            }
        }
    }
}