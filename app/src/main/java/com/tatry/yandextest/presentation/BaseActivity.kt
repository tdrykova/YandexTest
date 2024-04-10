package com.tatry.yandextest.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tatry.yandextest.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide();
    }

}