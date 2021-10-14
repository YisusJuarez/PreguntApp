package com.yjdev.preguntapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.yjdev.preguntapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {

            if(binding.tfName.text.toString().isEmpty()){
                Toast.makeText(this, "Necesitas escribir tu nombre.", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra(Constants.USER_NAME, binding.tfName.text.toString())
                startActivity(intent)
                finish()
            }
        }

    }
}