package com.example.fishnov.data.repository

import android.util.Log
import com.example.fishnov.data.classes.LoginForm
import com.example.fishnov.data.classes.RegisterForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.IOException

class API () {

    private var client = OkHttpClient()
    private var url = "http://10.0.2.2:80/api"

    suspend fun register(registerForm: RegisterForm): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .build()

            val formBody = FormBody.Builder()
                .add("first_name", registerForm.first_name)
                .add("last_name", registerForm.last_name)
                .add("email", registerForm.email)
                .add("password", registerForm.password)
                .add("password_confirmation", registerForm.password)
                .add("type", registerForm.type)
                .add("registration_trawler", registerForm.registration_trawler)
                .build()

            val request: Request = Request.Builder()
                .url("$url/register")
                .headers(headers)
                .post(formBody)
                .build()

            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    return@withContext responseBody
                } else {
                    throw IOException(responseBody)
                }
            } catch (e: IOException) {
                throw IOException("$e")
            }
        }
    }


    suspend fun login(loginForm: LoginForm): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .build()

            val formBody = FormBody.Builder()
                .add("email", loginForm.email)
                .add("password", loginForm.password)
                .build()

            val request: Request = Request.Builder()
                .url("$url/login")
                .headers(headers)
                .post(formBody)
                .build()

            try {
                val response = client.newCall(request).execute()

                val responseBody = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    return@withContext responseBody
                } else {
                    throw IOException("Invalid credentials")
                }
            } catch (e: IOException) {
                throw IOException(e)
            }
        }
    }
}