package com.mnzlabz.guessthenumber.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mnzlabz.guessthenumber.R
import com.mnzlabz.guessthenumber.data.model.GTNModel
import com.mnzlabz.guessthenumber.databinding.FragmentGuessingBinding
import com.mnzlabz.guessthenumber.ui.viewmodels.GTNViewModel
import com.mnzlabz.guessthenumber.utils.Notifier
import com.mnzlabz.guessthenumber.utils.printSegmentByDigit

class GuessingFragment : Fragment() {
    private lateinit var binding: FragmentGuessingBinding
    private val viewModel: GTNViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuessingBinding.inflate(inflater, container, false)
        initializeListeners()
        initializeObservers()

        resetDisplay(true)
        buildSegmentsStructure(0)
        binding.btnRetry.visibility = View.GONE
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    //TODO
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_font_size -> {
                // TODO: Implement this
                true
            }
            R.id.action_change_palette -> {
                // TODO: Implement this
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
                    Notifier.notify("O campo de palpite é obrigatório!", layoutInflater.context)
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
            displayResult(it)
            resetDisplay()
            buildSegmentsStructure(it.value)

            binding.progress.visibility = View.GONE
            binding.btnRetry.visibility = View.VISIBLE
        })
    }

    private fun resetDisplay(isStart: Boolean = false) {
        with(binding) {
//            firstDigit.root.allViews.forEach { it.alpha = 0.1F }
//            secondDigit.root.allViews.forEach { it.alpha = 0.1F }
//            thirdDigit.root.allViews.forEach { it.alpha = 0.1F }

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
            val userInput = guessInput.text.toString().toInt()

            gtnModel.value?.let { generatedNumber ->
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