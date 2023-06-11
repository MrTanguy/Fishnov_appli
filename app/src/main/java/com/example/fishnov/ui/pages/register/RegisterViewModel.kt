package com.example.fishnov.ui.pages.register

import androidx.lifecycle.ViewModel
import com.example.fishnov.data.repository.API
import kotlinx.coroutines.runBlocking

class RegisterViewModel : ViewModel() {

    val repository = API()

    fun callAPIregister(user: String) = runBlocking {
        return@runBlocking repository.register(user)
    }

}