package com.example.fishnov.ui.pages.register

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.example.fishnov.R
import com.example.fishnov.data.classes.RegisterForm
import com.example.fishnov.databinding.ActivityRegisterBinding
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
    }

    fun setupViews(){

        binding.registerButton.setOnClickListener(){
            checkRegister()
        }

    }

    fun checkRegister(){

        val user = RegisterForm(
            first_name = binding.editTextFirstname.toString(),
            last_name = binding.editTextLastname.toString(),
            email = binding.editTextEmail.toString(),
            password = binding.editTextPassword.toString(),
            type = "trawler",
            registration_trawler = binding.editTextTrawlerRegistration.toString()
            )

        val gson = Gson()
        val userJson = gson.toJson(user)
        val result = viewModel.callAPIregister(userJson)
        //Log.d("result", result)


        //Log.d("User", user.toString())

    }


}