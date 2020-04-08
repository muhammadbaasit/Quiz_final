package com.example.quizapp

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class QviewModel(application:Application) : AndroidViewModel(application) {

    var nextButton = MutableLiveData<Boolean>().apply { value = false }
    var prevButton = MutableLiveData<Boolean>().apply { value = false }

    var al = ArrayList<Model>()
    var alMutableLiveData = MutableLiveData<List<Model>>()
    var mo:Model?=null
    var COUNT=1
    var repository:QuizRepository ?= null
    var allQuiz :LiveData<QModel>? = null

    fun getApiData() : MutableLiveData<List<Model>> {

        val apiinterface= ApiInterface.create().getData()
        apiinterface.enqueue(object : Callback<MutableList<Model>> {
            override fun onResponse(call: Call<MutableList<Model>>, response: Response<MutableList<Model>>) {

                if(response?.body() != null){

                    for(items in response?.body()!!){
                        //Log.d("DATASSS","info>>>>>"+items)
                        mo=Model(items.quizNumber,items.question,items.option1,items.option2,items.option3,items.option4,items.answer)
                        al.add(mo!!)
                        alMutableLiveData.value = al
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Model>>, t: Throwable) {
                Log.d("FAILED","failed")
            }
        })
        return alMutableLiveData
    }

    fun insertQuiz(quiz:QModel) = viewModelScope.launch {

        repository?.insertQuiz(quiz)

    }

    fun getQuiz(context: Context,qid:Int){

        val quizDao = QuizDb.getInstance(context).quizDao()
        repository = QuizRepository(quizDao,qid)

        allQuiz = repository?.allQuiz

    }

    fun onNextClick(view: View){
        nextButton.value = true
    }

    fun onPrevClick(view:View){
        prevButton.value = true
    }
}