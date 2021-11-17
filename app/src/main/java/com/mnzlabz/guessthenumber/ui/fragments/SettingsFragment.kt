package com.mnzlabz.guessthenumber.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mnzlabz.guessthenumber.R
import com.mnzlabz.guessthenumber.databinding.FragmentHomeBinding
import com.mnzlabz.guessthenumber.databinding.FragmentSettingsBinding
import com.mnzlabz.guessthenumber.ui.viewmodels.GTNViewModel
import com.mnzlabz.guessthenumber.utils.Notifier

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: GTNViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var fade: Animation
    private lateinit var _context: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { _context = it.context }
        binding = FragmentSettingsBinding.inflate(inflater, container,  false)

        sharedPreferences = _context.getSharedPreferences("GTN_PREFERENCES", Context.MODE_PRIVATE)

        initializeAnimations(inflater)
        initializeListeners()
        initializeParams()
        return binding.root
    }


    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        binding.settingsCard.startAnimation(fade)
    }

    private fun initializeListeners() {
        with(binding) {
            btnEdit.setOnClickListener {

                if(formIsValid()) {
                    val min = tvMin.text.toString().toInt()
                    val max = tvMax.text.toString().toInt()

                    viewModel.setRange(min, max)
                    savePreferences(min, max)

                    Notifier.notify(getString(R.string.settings_saved), layoutInflater.context)
                } else {
                    Notifier.notify(getString(R.string.settings_validation_message), layoutInflater.context)
                }
            }
        }
    }

    private fun savePreferences(min: Int, max: Int) {
        sharedPreferences.edit().putInt(getString(R.string.gtn_limit_min), min).commit()
        sharedPreferences.edit().putInt(getString(R.string.gtn_limit_max), max).commit()
    }

    private fun formIsValid(): Boolean {
        return with(binding) {
            !tvMin.text.isNullOrEmpty() && !tvMax.text.isNullOrEmpty() && (tvMax.text.toString() != tvMin.text.toString())
        }
    }


    private fun initializeParams() {
        with(binding) {
            tvMin.setText(viewModel.minRange.value.toString())
            tvMax.setText(viewModel.maxRange.value.toString())
        }
    }
}