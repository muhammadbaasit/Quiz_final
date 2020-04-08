package com.example.quizapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QModel(

    @PrimaryKey(autoGenerate = true)
    var qId : Int = 0,

    @ColumnInfo(name = "quizNumber")
    var quizNumber:String="",

    @ColumnInfo(name = "question")
    var question:String="",

    @ColumnInfo(name = "option1")
    var option1:String="",

    @ColumnInfo(name = "option2")
    var option2:String="",

    @ColumnInfo(name = "option3")
    var option3:String="",

    @ColumnInfo(name = "option4")
    var option4:String="",

    @ColumnInfo(name = "answer")
    var answer:String=""

)