package com.example.fishnov.ui.pages.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import com.example.fishnov.R
import com.example.fishnov.data.classes.RegisterForm
import com.example.fishnov.databinding.ActivityRegisterBinding
import com.example.fishnov.ui.pages.connected.ConnectedActivity

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

            result.onSuccess { dataStore ->
                viewModel.saveToDataStoreRepository(dataStore.bearerToken, dataStore.userId)
                val intent = Intent(this@RegisterActivity, ConnectedActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            result.onFailure { error ->
                var errorMessage = "Error in "
                if (error.message?.contains("The email must be a valid email address.") == true) {
                    errorMessage += "invalid email "
                }
                if (error.message?.contains("The email has already been taken.") == true) {
                    errorMessage += "email alreasy used "
                }
                if (error.message?.contains("password") == true) {
                    errorMessage += "password (8 characters minimum, min, maj, number)"
                }
                Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}