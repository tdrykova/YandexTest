package com.tatry.yandextest.presentation

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tatry.yandextest.databinding.ActivityBaseBinding
//import com.tatry.yandextest.presentation.components.CustomButton

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}