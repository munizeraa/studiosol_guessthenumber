package com.mnzlabz.guessthenumber.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mnzlabz.guessthenumber.R
import com.mnzlabz.guessthenumber.data.model.GTNModel
import com.mnzlabz.guessthenumber.databinding.FragmentGuessingBinding
import com.mnzlabz.guessthenumber.ui.viewmodels.GTNViewModel
import com.mnzlabz.guessthenumber.utils.DisplayMapper
import com.mnzlabz.guessthenumber.utils.Notifier

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
//        binding.toolbarGuessing.inflateMenu(R.menu.menu)
        initializeListeners()
        initializeObservers()

        buildDisplaySegments(507890002)
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
                // UNCOMMENT THIS AFTER ALL DONE
//                binding.btnConfirm.isClickable = false
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
                displayGuessNumber.text = ""
                displayGuessResult.text = ""
                binding.btnConfirm.isClickable = true

                btnRetry.visibility = View.GONE
            }
        }
    }

    private fun validateGuessInput(): Boolean = !binding.guessInput.text.isNullOrEmpty()

    private fun initializeObservers() {
        viewModel.gtnModel.observe(viewLifecycleOwner, Observer {
            //buildSegmentDisplay(it)
            displayResult(it)

            binding.progress.visibility = View.GONE
            binding.btnRetry.visibility = View.VISIBLE
        })
    }

    private fun buildDisplaySegments(number: Int) {
        val digits = number.toString().map { it.toString() }
        var segmentDisplay = mutableListOf<List<String>>()

        digits.forEach { digit ->
            segmentDisplay.add(DisplayMapper.getSegmentsFromDigit(digit))
        }

        segmentDisplay.forEach { digit ->
            Log.i("SEGMENTS", digit.joinToString { it })
        }
        //binding.displayGuessNumber.text = it.value?.toString() ?: it.statusCode.toString()
    }

    private fun displayResult(gtnModel: GTNModel) {
        with(binding) {
            val userInput = guessInput.text.toString().toInt()

            gtnModel.value?.let { generatedNumber ->
                when {
                    (!gtnModel.isSuccessful) -> { displayGuessResult.text = "Erro" }
                    (generatedNumber > userInput) -> { displayGuessResult.text = "O número gerado é maior que o palpite!" }
                    (generatedNumber < userInput) -> { displayGuessResult.text = "O número gerado é menor que o palpite!" }
                    (generatedNumber == userInput) -> { displayGuessResult.text = "Acertou!" }
                }
            }
        }
    }
}