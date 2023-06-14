package com.example.fishnov.ui.pages.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fishnov.R
import com.example.fishnov.data.classes.LoginForm
import com.example.fishnov.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[LoginViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupViews()
    }

    private fun setupViews() {
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                checkLogin()
            }
        }
    }

    private suspend fun checkLogin() {
        val loginForm = LoginForm(
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPassword.text.toString()
        )
        val result = viewModel.callAPIlogin(loginForm)

        result.onSuccess { accessToken ->
            withContext(Dispatchers.Main) {
                // Connexion réussie, affichez un message de succès
                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                Log.d("bearer", accessToken)
            }
        }
        result.onFailure { error ->
            withContext(Dispatchers.Main) {
                // Gestion de l'erreur de connexion
                Toast.makeText(this@LoginActivity, "Login failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


/*
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

    fun setupViews() {
        binding.loginButton.setOnClickListener {
            lifecycleScope.launch {
                checkLogin()
            }
        }
    }

    private suspend fun checkLogin() {
        val loginForm = LoginForm(
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPassword.text.toString()
        )
        val result = viewModel.callAPIlogin(loginForm)

        result.onSuccess { accessToken ->
            withContext(Dispatchers.Main) {
                // Connexion réussie, affichez un message de succès
                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                Log.d("bearer", accessToken)
            }
        }
        result.onFailure { error ->
            withContext(Dispatchers.Main) {
                // Gestion de l'erreur de connexion
                Toast.makeText(this@LoginActivity , "Login failed: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

 */