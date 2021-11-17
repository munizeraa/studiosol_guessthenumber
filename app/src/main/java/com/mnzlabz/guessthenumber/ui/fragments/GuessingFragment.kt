package com.mnzlabz.guessthenumber.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mnzlabz.guessthenumber.R
import com.mnzlabz.guessthenumber.data.local.GuessEntity
import com.mnzlabz.guessthenumber.data.model.GTNModel
import com.mnzlabz.guessthenumber.databinding.FragmentGuessingBinding
import com.mnzlabz.guessthenumber.ui.viewmodels.GTNViewModel
import com.mnzlabz.guessthenumber.utils.Notifier
import com.mnzlabz.guessthenumber.utils.printSegmentByDigit

class GuessingFragment : Fragment() {
    private lateinit var binding: FragmentGuessingBinding
    private val viewModel: GTNViewModel by activityViewModels()
    private lateinit var fade: Animation
    private lateinit var _context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { _context = it.context }
        binding = FragmentGuessingBinding.inflate(inflater, container, false)

        initializeListeners()
        initializeObservers()
        initializeAnimations(inflater)
        resetDisplay(true)
        buildSegmentsStructure(0)
        binding.btnRetry.visibility = View.GONE

        return binding.root
    }

    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        binding.root.startAnimation(fade)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_history -> {
                this.findNavController().navigate(R.id.action_guessingFragment_to_historyFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeListeners() {
        with(binding) {
            btnConfirm.setOnClickListener {
                binding.btnConfirm.isClickable = false
                binding.progress.visibility = View.VISIBLE

                if(validateGuessInput()) {
                    viewModel.getRandomNumber()
                } else {
                    binding.progress.visibility = View.GONE
                    binding.btnConfirm.isClickable = true
                    Notifier.notify(getString(R.string.guess_mandatory_number), layoutInflater.context)
                }
            }

            btnRetry.setOnClickListener {
                guessInput.text?.clear()
                displayGuessResult.text = ""
                binding.btnConfirm.isClickable = true
                resetDisplay(true)

                btnRetry.visibility = View.GONE
            }
        }
    }

    private fun validateGuessInput(): Boolean = !binding.guessInput.text.isNullOrEmpty()

    private fun initializeObservers() {
        viewModel.gtnModel.observe(viewLifecycleOwner, Observer {
            it?.let{
                displayResult(it)
                resetDisplay()
                buildSegmentsStructure(it.value)

                binding.progress.visibility = View.GONE
                binding.btnRetry.visibility = View.VISIBLE
            }
        })
    }

    private fun resetDisplay(isStart: Boolean = false) {
        with(binding) {
            if(isStart) {
                secondDigit.root.visibility = View.GONE
                thirdDigit.root.visibility = View.GONE
                buildSegmentsStructure(0)
            }
        }
    }

    private fun buildSegmentsStructure(number: Int) {
        val digits = number.toString().map { it.toString() }

        digits.forEachIndexed {index, digit ->
            print(digit, index)
        }
    }

    private fun print(digit: String, displayIndex: Int) {
        with(binding) {
            when(displayIndex) {
                0 -> { firstDigit.root.printSegmentByDigit(digit) }
                1 -> {
                    secondDigit.root.visibility = View.VISIBLE
                    secondDigit.root.printSegmentByDigit(digit)
                }
                else -> {
                    thirdDigit.root.visibility = View.VISIBLE
                    thirdDigit.root.printSegmentByDigit(digit)
                }
            }
        }
    }

    private fun displayResult(gtnModel: GTNModel) {
        with(binding) {
            if(validateGuessInput()) {
                val userInput = guessInput.text.toString().toInt()

                gtnModel.value?.let { generatedNumber ->
                    val guess = GuessEntity(0, userInput, gtnModel.value, generatedNumber == userInput)
                    viewModel.insertGuess(guess)

                    when {
                        (!gtnModel.isSuccessful) -> { displayGuessResult.text = "Erro" }
                        (generatedNumber > userInput) -> { displayGuessResult.text = "O número gerado é maior que o palpite!" }
                        (generatedNumber < userInput) -> { displayGuessResult.text = "O número gerado é menor que o palpite!" }
                        else -> { displayGuessResult.text = "Acertou!" }
                    }
                }
            }
        }
    }
}