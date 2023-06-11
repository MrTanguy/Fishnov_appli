package com.example.fishnov.ui.pages.register

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        // Vérification si les inputs ne sont pas vides
        if (binding.editTextFirstname.text.toString().isEmpty()
            or binding.editTextLastname.text.toString().isEmpty()
            or binding.editTextEmail.text.toString().isEmpty()
            or binding.editTextPassword.text.toString().isEmpty()
            or binding.editTextConfirmPassword.text.toString().isEmpty()
            or binding.editTextTrawlerRegistration.text.toString().isEmpty()) {
            Toast.makeText(this,"Please fill up all the form", Toast.LENGTH_SHORT).show()

        // Vérification si le mot de passe et sa confimation match
        } else if (binding.editTextPassword.text.toString() != binding.editTextConfirmPassword.text.toString()) {
            Toast.makeText(this,"Password and confirm password aren't matching", Toast.LENGTH_SHORT).show()

        } else {
            val registerForm = RegisterForm(
                first_name = binding.editTextFirstname.text.toString(),
                last_name = binding.editTextLastname.text.toString(),
                email = binding.editTextEmail.text.toString(),
                password = binding.editTextPassword.text.toString(),
                type = "trawler",
                registration_trawler = binding.editTextTrawlerRegistration.text.toString()
            )
            val result = viewModel.callAPIregister(registerForm)

        }
    }
}