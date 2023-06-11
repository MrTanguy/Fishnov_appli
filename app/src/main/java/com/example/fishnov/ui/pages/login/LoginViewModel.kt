package com.example.fishnov.ui.pages.login

import androidx.lifecycle.ViewModel
import com.example.fishnov.data.classes.LoginForm
import com.example.fishnov.data.repository.API
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {

    val repository = API()

    fun callAPIlogin(loginForm: LoginForm) = runBlocking {
        return@runBlocking repository.login(loginForm)
    }

}