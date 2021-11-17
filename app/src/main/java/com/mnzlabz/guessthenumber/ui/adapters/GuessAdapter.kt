package com.mnzlabz.guessthenumber.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mnzlabz.guessthenumber.data.local.GuessEntity
import com.mnzlabz.guessthenumber.databinding.GuessItemBinding

class GuessAdapter: RecyclerView.Adapter<GuessViewHolder>() {
    private val guesses = ArrayList<GuessEntity>()

    fun setItems(items: ArrayList<GuessEntity>) {
        this.guesses.clear()
        this.guesses.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuessViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GuessItemBinding.inflate(inflater, parent, false)

        return GuessViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GuessViewHolder, position: Int) {
        holder.bind(guesses[position])
    }

    override fun getItemCount(): Int = guesses.size
}

class GuessViewHolder(private val binding: GuessItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(guessEntity: GuessEntity) {
        binding.tvValueGuess.text = guessEntity.userGuessing.toString()
        binding.tvValueGeneratedNumber.text = guessEntity.generatedNumber.toString()
        binding.tvValueResult.text = if (guessEntity.userHasWon) "Sim" else "Não"
    }
}