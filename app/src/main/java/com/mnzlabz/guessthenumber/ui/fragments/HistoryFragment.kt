package com.mnzlabz.guessthenumber.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mnzlabz.guessthenumber.R
import com.mnzlabz.guessthenumber.data.local.GuessEntity
import com.mnzlabz.guessthenumber.databinding.FragmentHistoryBinding
import com.mnzlabz.guessthenumber.ui.adapters.GuessAdapter
import com.mnzlabz.guessthenumber.ui.viewmodels.GTNViewModel
import com.mnzlabz.guessthenumber.utils.Notifier

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: GuessAdapter
    private val viewModel: GTNViewModel by activityViewModels()
    private lateinit var fade: Animation
    private lateinit var _context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_history -> {
                confirmDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.let { _context = it.context }
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setRecyclerView()
        viewModel.getAllGuesses()
        initializeObservers()
        initializeAnimations(inflater)
        viewModel.resetGTNModel()
        return binding.root
    }

    private fun initializeAnimations(inflater: LayoutInflater) {
        fade = AnimationUtils.loadAnimation(inflater.context, R.anim.fade_in)
        binding.rvGuesses.startAnimation(fade)
    }

    private fun setRecyclerView() {
        adapter = GuessAdapter()
        adapter.setItems(arrayListOf())
        binding.rvGuesses.adapter = adapter
        binding.rvGuesses.layoutManager = LinearLayoutManager(this.context)
    }

    private fun initializeObservers() {
        viewModel.guesses.observe(viewLifecycleOwner, Observer {
            it?.let {
                val guesses = arrayListOf<GuessEntity>()
                guesses.addAll(it)
                setAdapter(guesses)
            }
        })
    }

    private fun confirmDelete() {
        MaterialAlertDialogBuilder(layoutInflater.context)
            .setTitle(getString(R.string.history_confirmation_delete))
            .setPositiveButton(layoutInflater.context.getString(R.string.yes)) { dialog, which ->
                viewModel.guesses.value?.let {
                    if(it.isEmpty()) {
                        Notifier.notify(getString(R.string.history_empty), layoutInflater.context)
                    } else {
                        viewModel.deleteAllGuesses()
                        setAdapter(arrayListOf())
                    }
                }
            }
            .setNegativeButton(layoutInflater.context.getString(R.string.no)) { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setAdapter(guesses: ArrayList<GuessEntity>) {
        adapter.apply { setItems(guesses) }
    }
}