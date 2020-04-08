package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding:ActivityMainBinding? = null
    var qviewModel:QviewModel? = null
    var mlist:MutableList<QModel>?= null
    var COUNT = 1
    var data:List<Model>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding!!.lifecycleOwner
        qviewModel = ViewModelProvider(this@MainActivity).get(QviewModel::class.java)
        qviewModel?.apply {
            binding?.apply {
                viewModel = qviewModel
            }
            getApiData()
            alMutableLiveData!!.observe(this@MainActivity,androidx.lifecycle.Observer {
                data = it
            })
        }
        qviewModel!!.getQuiz(this@MainActivity,COUNT)
        qviewModel!!.allQuiz?.observe(this@MainActivity,androidx.lifecycle.Observer {
            if(it == null){
                for(items in data!!){
                    qviewModel!!.insertQuiz(QModel(
                        0,
                        items.quizNumber,
                        items.question,
                        items.option1,
                        items.option2,
                        items.option3,
                        items.option4,
                        items.answer))
                }
            }
            //Log.d("DATASSS","info-mlist>>>>>"+data?.size)
            binding?.labelQuestionCount?.text = it?.quizNumber
            binding?.tvQuestion?.text = it?.question
            binding?.tvOpt1?.text = it?.option1
            binding?.tvOpt2?.text = it?.option2
            binding?.tvOpt3?.text = it?.option3
            binding?.tvOpt4?.text = it?.option4
        })

        //------------------------------------------------------------------------------------------

        qviewModel!!.nextButton.observe(this@MainActivity,androidx.lifecycle.Observer {
            if(it == true){

                if(data?.size!! > COUNT){

                    binding!!.btnBack.visibility = View.VISIBLE
                    COUNT++
                    qviewModel!!.getQuiz(this@MainActivity,COUNT)

                    qviewModel!!.allQuiz?.observe(this@MainActivity,androidx.lifecycle.Observer {

                        //Log.d("DATASSS","info>>>>>"+it)
                        binding?.labelQuestionCount?.text = it?.quizNumber
                        binding?.tvQuestion?.text = it?.question
                        binding?.tvOpt1?.text = it?.option1
                        binding?.tvOpt2?.text = it?.option2
                        binding?.tvOpt3?.text = it?.option3
                        binding?.tvOpt4?.text = it?.option4

                    })
                }
                else{
                    Toast.makeText(this,"QUESTIONS ARE OVER",Toast.LENGTH_LONG).show()
                    binding!!.btnNext.visibility = View.GONE
                }
                if(data?.size!! == COUNT){

                    binding!!.btnNext.visibility = View.GONE
                }
            }
        })

        //------------------------------------------------------------------------------------------

        qviewModel!!.prevButton.observe(this@MainActivity,androidx.lifecycle.Observer {

            if(it == true){
                if(COUNT > 1){
                    binding!!.btnNext.visibility = View.VISIBLE
                    --COUNT
                    qviewModel!!.getQuiz(this@MainActivity,COUNT)
                    qviewModel!!.allQuiz?.observe(this@MainActivity,androidx.lifecycle.Observer {

                        //Log.d("DATASSS","info>>>>>"+it)
                        binding?.labelQuestionCount?.text = it?.quizNumber
                        binding?.tvQuestion?.text = it?.question
                        binding?.tvOpt1?.text = it?.option1
                        binding?.tvOpt2?.text = it?.option2
                        binding?.tvOpt3?.text = it?.option3
                        binding?.tvOpt4?.text = it?.option4
                    })
                }
                if(COUNT == 1){
                    binding!!.btnBack.visibility = View.GONE
                }
            }
        })
      //--------------------------------------------------------------------------------------------
    }
}
