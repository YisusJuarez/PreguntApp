package com.yjdev.preguntapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.yjdev.preguntapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val username = intent.getStringExtra(Constants.USER_NAME)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalAnswers = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        Log.d("Constants", "Result: $correctAnswers")
        Log.d("Constants", "Result: $totalAnswers")
        binding.tvUsername.text = username

        binding.tvScore.text = "Tu resultado es: ${correctAnswers} de ${totalAnswers}"

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}