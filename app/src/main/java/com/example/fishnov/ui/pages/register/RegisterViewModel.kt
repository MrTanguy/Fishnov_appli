package com.example.fishnov.ui.pages.register

import androidx.lifecycle.ViewModel
import com.example.fishnov.data.classes.RegisterForm
import com.example.fishnov.data.repository.API
import kotlinx.coroutines.runBlocking

class RegisterViewModel : ViewModel() {

    val repository = API()

    fun callAPIregister(registerForm: RegisterForm) = runBlocking {
        return@runBlocking repository.register(registerForm)
    }

}