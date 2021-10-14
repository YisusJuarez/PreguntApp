package com.yjdev.preguntapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.yjdev.preguntapp.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding:ActivityQuestionBinding

    //variables
    private var mCurrentPosition:Int = 1
    private lateinit var mQuestionList: ArrayList<Question>
    private var mSelectedOption:Int = 0
    private var mCorrectAnswers:Int = 0
    private var mUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mQuestionList = Constants.getQuestions()
        Log.i("Question Size", "${mQuestionList.size}")
        fillQuestion()

        binding.tvOptionOne.setOnClickListener(this)
        binding.tvOptionTwo.setOnClickListener(this)
        binding.tvOptionThree.setOnClickListener(this)
        binding.tvOptionFour.setOnClickListener(this)
        binding.btnSubmit.setOnClickListener(this)


    }

    private fun fillQuestion(){

        val question: Question = mQuestionList[mCurrentPosition - 1]
        // Reset de el color de las opciones
        defaultOptionsView()
        if(mCurrentPosition == mQuestionList.size){
            binding.btnSubmit.text = "Terminar"
        }else{
            binding.btnSubmit.text = "Enviar"
        }
        // ProgressBar
        binding.progressBar.progress = mCurrentPosition
        binding.tvProgress.text = "${mCurrentPosition}" + "/" + binding.progressBar.max

        //Question
        binding.tvQuestion.text = question.question

        //IMG
        binding.ivImage.setImageResource(question.image)

        // Asignando preguntas
        binding.tvOptionOne.text = question.optionOne
        binding.tvOptionTwo.text = question.optionTwo
        binding.tvOptionThree.text = question.optionThree
        binding.tvOptionFour.text = question.optionFour


    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, binding.tvOptionOne)
        options.add(1, binding.tvOptionTwo)
        options.add(2, binding.tvOptionThree)
        options.add(3, binding.tvOptionFour)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one -> {selectedOptionView(binding.tvOptionOne,1)}
            R.id.tv_option_two -> {selectedOptionView(binding.tvOptionTwo,2)}
            R.id.tv_option_three -> {selectedOptionView(binding.tvOptionThree,3)}
            R.id.tv_option_four -> {selectedOptionView(binding.tvOptionFour,4)}
            R.id.btn_submit -> {
                if(mSelectedOption == 0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <= mQuestionList.size ->{
                            fillQuestion()
                        }
                        else ->{
                            Toast.makeText(this, "Acabaste el Test Satifactoriamente :D", Toast.LENGTH_SHORT).show()
                            val intentResult = Intent(this, ResultActivity::class.java)
                            intentResult.putExtra(Constants.USER_NAME, mUserName)
                            intentResult.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intentResult.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList.size)
                            Log.d("Constants", "$mCorrectAnswers")
                            Log.d("Constants", "${mQuestionList.size}")
                            startActivity(intentResult)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionList.get(mCurrentPosition - 1)
                    if(question.correctAnswer != mSelectedOption){
                        selectAnswerColor(mSelectedOption, R.drawable.incorrect_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    selectAnswerColor(question.correctAnswer, R.drawable.correct_option_border_bg)
                    if(mCurrentPosition == mQuestionList.size){
                        binding.btnSubmit.text = "Terminar"

                    }else{
                        binding.btnSubmit.text = "Siguiente Pregunta"
                    }
                    mSelectedOption = 0

                }
            }
        }
    }
    private fun selectedOptionView(tv:TextView, selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOption = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.typeface = Typeface.DEFAULT_BOLD
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun selectAnswerColor(answer:Int, drawableView:Int){
        when(answer){
            1->{binding.tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)}
            2->{binding.tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)}
            3->{binding.tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)}
            4->{binding.tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)}
        }
    }




}