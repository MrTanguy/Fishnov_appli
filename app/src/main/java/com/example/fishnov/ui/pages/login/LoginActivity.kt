package com.example.fishnov.ui.pages.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.data.classes.LoginForm
import com.example.fishnov.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
    }

    fun setupViews(){

        binding.loginButton.setOnClickListener(){
            checkLogin()
        }

    }

    fun checkLogin(){

        // Vérification si les inputs ne sont pas vides
        if (binding.editTextEmail.text.toString().isEmpty()
            or binding.editTextPassword.text.toString().isEmpty()) {
            Toast.makeText(this,"Please fill up all the form", Toast.LENGTH_SHORT).show()

        } else {
            val loginForm = LoginForm(
                email = binding.editTextEmail.text.toString(),
                password = binding.editTextPassword.text.toString()
            )
            val result = viewModel.callAPIlogin(loginForm)
        }
    }

}