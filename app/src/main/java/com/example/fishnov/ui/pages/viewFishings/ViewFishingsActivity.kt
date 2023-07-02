package com.example.fishnov.ui.pages.viewFishings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityViewFishingsBinding
import com.example.fishnov.ui.pages.addFishing.AddFishingActivity
import com.example.fishnov.ui.pages.profile.ProfileActivity

class ViewFishingsActivity: AppCompatActivity() {

    lateinit var binding: ActivityViewFishingsBinding
    lateinit var viewModel: ViewFishingsViewModel

    public override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_fishings)
        viewModel = ViewModelProvider(this)[ViewFishingsViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_fishings)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupViews()
    }

    private fun setupViews() {

        viewModel.getAllFishings()

    }
}