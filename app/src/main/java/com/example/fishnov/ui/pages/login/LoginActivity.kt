package com.example.fishnov.ui.pages.login


import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.data.classes.LoginForm
import com.example.fishnov.databinding.ActivityLoginBinding
import com.example.fishnov.ui.pages.profile.ProfileActivity

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
            checkLogin()
        }
    }

    private fun checkLogin() {
        val loginForm = LoginForm(
            email = binding.editTextEmail.text.toString(),
            password = binding.editTextPassword.text.toString()
        )

        val result = viewModel.callAPIlogin(loginForm)

        result.onSuccess {  dataStore ->
            viewModel.saveToDataStoreRepository(dataStore.bearerToken, dataStore.userId)
            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
        }
        result.onFailure { error ->
            // Gestion de l'erreur de connexion
            Toast.makeText(this@LoginActivity, "Login failed: Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }
}
