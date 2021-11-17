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
import com.mnzlabz.guessthenumber.ui.viewmodels.GTNViewModel
import com.mnzlabz.guessthenumber.utils.Notifier

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: GTNViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var fade: Animation
    private lateinit var bounce: Animation
    private lateinit var _context: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { _context = it.context }
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        sharedPreferences = _context.getSharedPreferences("GTN_PREFERENCES", Context.MODE_PRIVATE)

        viewModel.resetGTNModel()
        initializeAnimations(inflater)
        initializeListeners()
        initializeObservers()

        validateGTNPreferences()
        setGTNPreferences()

        return binding.root
    }

    private fun validateGTNPreferences() {
        val min = sharedPreferences.getInt(getString(R.string.gtn_limit_min), -1)
        val max = sharedPreferences.getInt(getString(R.string.gtn_limit_max), -1)

        if(min == -1 || max == -1) {
            sharedPreferences.edit().putInt(getString(R.string.gtn_limit_min), 1).commit()
            sharedPreferences.edit().putInt(getString(R.string.gtn_limit_max), 300).commit()
        }
    }

    private fun setGTNPreferences() {
        val min = sharedPreferences.getInt(getString(R.string.gtn_limit_min), -1)
        val max = sharedPreferences.getInt(getString(R.string.gtn_limit_max), -1)

        viewModel.setRange(min, max)
    }

    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        bounce = AnimationUtils.loadAnimation(inflater.context, R.anim.bounce)

        binding.homeCard.startAnimation(bounce)
        binding.buttonStart.startAnimation(fade)
        binding.buttonSettings.startAnimation(fade)
        binding.buttonAbout.startAnimation(fade)
    }

    private fun initializeObservers() {

    }

    private fun initializeListeners() {
        with(binding) {
            buttonStart.setOnClickListener {
                findNavController().navigate(R.id.action_home_to_guessingFragment)
            }

            buttonSettings.setOnClickListener {
                findNavController().navigate(R.id.action_home_to_settingsFragment)
            }

            buttonAbout.setOnClickListener {
                Notifier.notify(getString(R.string.home_about_developer), layoutInflater.context)
            }
        }
    }
}