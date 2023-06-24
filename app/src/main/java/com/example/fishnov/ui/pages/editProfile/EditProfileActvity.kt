package com.example.fishnov.ui.pages.editProfile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityProfileEditBinding
import com.example.fishnov.ui.pages.profile.ProfileActivity
import org.json.JSONObject

class EditProfileActvity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileEditBinding
    private lateinit var viewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[EditProfileViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_edit)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupViews()
    }

    private fun setupViews() {
        val result = viewModel.getUserInfo()
        val jsonObject = JSONObject(result)
        viewModel.firstName = jsonObject["first_name"].toString()
        viewModel.lastName = jsonObject["last_name"].toString()
        viewModel.registrationTrawler = jsonObject["registration_trawler"].toString()

        binding.saveButton.setOnClickListener() {
            checkSave()
        }
    }

    private fun checkSave() {

        val form = JSONObject()

        if (viewModel.firstName != binding.editTextFirstname.text.toString() && binding.editTextFirstname.text.toString() != "") {
            form.put("first_name", binding.editTextFirstname.text.toString())
        }
        if (viewModel.lastName != binding.editTextLastname.text.toString() && binding.editTextLastname.text.toString() != "") {
            form.put("last_name", binding.editTextLastname.text.toString())
        }
        if (viewModel.registrationTrawler != binding.editTextTrawlerRegistration.text.toString() && binding.editTextTrawlerRegistration.text.toString() != "") {
            form.put("registration_trawler", binding.editTextTrawlerRegistration.text.toString())
        }
        if (binding.editTextPassword.text.toString() != "" && binding.editTextPassword.text.toString() == binding.editTextConfirmPassword.text.toString()) {
            form.put("password", binding.editTextPassword.text.toString())
            form.put("password_confirmation", binding.editTextConfirmPassword.text.toString())
        }
        val result = viewModel.updateUserInfo(form)

        result.onSuccess { dataStore ->
            viewModel.saveToDataStoreRepository(dataStore.bearerToken)
            Toast.makeText(this@EditProfileActvity, "Update saved !", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@EditProfileActvity, ProfileActivity::class.java))
        }
        result.onFailure { error ->
            Toast.makeText(this@EditProfileActvity, error.message, Toast.LENGTH_SHORT).show()
        }
    }

}