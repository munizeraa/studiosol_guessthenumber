package com.mnzlabz.guessthenumber.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mnzlabz.guessthenumber.R
import com.mnzlabz.guessthenumber.databinding.FragmentHomeBinding
import com.mnzlabz.guessthenumber.ui.viewmodels.GTNViewModel
import com.mnzlabz.guessthenumber.utils.Notifier

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: GTNViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initializeListeners()
        initializeObservers()

        return binding.root
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
                Notifier.notify("Desenvolvedor: Philippe Muniz Gomes \nEmail: pmdm.sys@gmail.com", layoutInflater.context)
            }
        }
    }
}