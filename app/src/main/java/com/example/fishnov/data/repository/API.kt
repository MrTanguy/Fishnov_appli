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
import org.json.JSONObject
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

    suspend fun getUserInfo(token: String, id: Int): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", "Bearer $token")
                .build()

            val request: Request = Request.Builder()
                .url("$url/user/$id")
                .headers(headers)
                .get()
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

    suspend fun update_user_info(token: String, id: Int, form: JSONObject): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", "Bearer $token")
                .build()

            val formBody = FormBody.Builder()

            for (key in form.keys()) {
                formBody.add(key, form.get(key).toString())
            }

            val request: Request = Request.Builder()
                .url("$url/user/update/$id")
                .headers(headers)
                .post(formBody.build())
                .build()

            try {
                val response = client.newCall(request).execute()

                val responseBody = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    return@withContext responseBody
                } else {
                    throw IOException("Error")
                }
            } catch (e: IOException) {
                throw IOException(e)
            }
        }
    }

    suspend fun getCompanyInfo (id: String, token: String): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", "Bearer $token")
                .build()

            val request: Request = Request.Builder()
                .url("$url/company/$id")
                .headers(headers)
                .get()
                .build()

            try {
                val response = client.newCall(request).execute()

                val responseBody = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    return@withContext responseBody
                } else {
                    throw IOException("Error")
                }
            } catch (e: IOException) {
                throw IOException(e)
            }
        }
    }

    suspend fun joinCompany(bearer_token: String, form: JSONObject): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", "Bearer $bearer_token")
                .build()

            val formBody = FormBody.Builder()

            for (key in form.keys()) {
                formBody.add(key, form.get(key).toString())
            }

            val request: Request = Request.Builder()
                .url("$url/company/join")
                .headers(headers)
                .post(formBody.build())
                .build()

            try {
                val response = client.newCall(request).execute()

                val responseBody = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    return@withContext responseBody
                } else {
                    throw IOException("Error")
                }
            } catch (e: IOException) {
                throw IOException(e)
            }
        }
    }

    suspend fun addFishing (bearer_token: String, form: JSONObject, id: Int): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", "Bearer $bearer_token")
                .build()

            val formBody = FormBody.Builder()

            for (key in form.keys()) {
                formBody.add(key, form.get(key).toString())
            }

            val request: Request = Request.Builder()
                .url("$url/fishing/add/$id")
                .headers(headers)
                .post(formBody.build())
                .build()

            try {
                val response = client.newCall(request).execute()

                val responseBody = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    return@withContext responseBody
                } else {
                    throw IOException("Error")
                }
            } catch (e: IOException) {
                throw IOException(e)
            }
        }
    }

    suspend fun getAllFishings (bearer_token: String, id: Int): String {
        return withContext(Dispatchers.IO) {
            val headers = Headers.Builder()
                .add("Content-Type", "application/json")
                .add("Accept", "application/json")
                .add("Authorization", "Bearer $bearer_token")
                .build()

            val request: Request = Request.Builder()
                .url("$url/fishings/$id")
                .headers(headers)
                .get()
                .build()

            try {
                val response = client.newCall(request).execute()

                val responseBody = response.body()?.string() ?: ""

                if (response.isSuccessful) {
                    return@withContext responseBody
                } else {
                    throw IOException("Error")
                }
            } catch (e: IOException) {
                throw IOException(e)
            }
        }
    }

}
