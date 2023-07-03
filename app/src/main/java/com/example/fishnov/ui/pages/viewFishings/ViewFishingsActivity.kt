package com.example.fishnov.ui.pages.viewFishings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.fishnov.R
import com.example.fishnov.databinding.ActivityViewFishingsBinding
import com.example.fishnov.ui.pages.addFishing.AddFishingActivity
import com.example.fishnov.ui.pages.profile.ProfileActivity
import org.json.JSONObject

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
        val result = viewModel.getAllFishings()
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        result.onSuccess { allFishings ->
            for (i in 0 until allFishings.length()) {
                val fishing = JSONObject(allFishings[i].toString())
                val fishingKeys = fishing.keys()

                while (fishingKeys.hasNext()) {
                    val key = fishingKeys.next()
                    val value = fishing.get(key).toString()

                    val textView = TextView(this)
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )

                    if (key == "date") {
                        textView.text = value
                        textView.textSize = 20F
                        textView.gravity = Gravity.CENTER_HORIZONTAL
                        layoutParams.setMargins(0, 50, 0, 0)
                    } else {
                        textView.gravity = Gravity.CENTER_HORIZONTAL
                        textView.text = "$key: $value kg"
                    }

                    textView.layoutParams = layoutParams
                    linearLayout.addView(textView)
                }
            }
        }

        result.onFailure {
            val textView = TextView(this)

            textView.text = "Nothing to display, you must add at least 1 fishing."
            textView.textSize = 20F
            textView.gravity = Gravity.CENTER_HORIZONTAL
            linearLayout.addView(textView)


        }
    }

}