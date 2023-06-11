package com.example.fishnov.ui.pages.main

import android.os.Bundle
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityMainBinding
import com.example.fishnov.ui.pages.register.RegisterActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
    }

    private fun setupViews() {
        binding.registerButton.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java))}
    }
}

