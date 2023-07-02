package com.example.fishnov.ui.pages.connected

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityConnectedBinding
import com.example.fishnov.ui.pages.addFishing.AddFishingActivity
import com.example.fishnov.ui.pages.login.LoginViewModel
import com.example.fishnov.ui.pages.profile.ProfileActivity
import com.example.fishnov.ui.pages.viewFishings.ViewFishingsActivity

class ConnectedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConnectedBinding
    private lateinit var viewModel: ConnectedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_connected)
        viewModel = ViewModelProvider(this)[ConnectedViewModel::class.java]
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ConnectedViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupViews()
    }

    private fun setupViews() {
        binding.profileButton.setOnClickListener {
            startActivity(Intent(this@ConnectedActivity, ProfileActivity::class.java))
        }

        binding.addFishingButton.setOnClickListener {
            startActivity(Intent(this@ConnectedActivity, AddFishingActivity::class.java))
        }

        binding.viewFishingsButton.setOnClickListener {
            startActivity(Intent(this@ConnectedActivity, ViewFishingsActivity::class.java))
        }
    }

}