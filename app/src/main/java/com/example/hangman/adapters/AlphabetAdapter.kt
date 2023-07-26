package com.example.hangman.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.R
import com.example.hangman.databinding.ItemLetterSpotBinding
import com.example.hangman.entities.Letter

class AlphabetAdapter(private val alphabet: ArrayList<Letter>, private val onClick: (Int) -> Unit): RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder>() {

    class AlphabetViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemLetterSpotBinding.bind(view)

        fun bind(l: Letter, onClick: (Int) -> Unit) {
            binding.letterSpot.visibility = View.VISIBLE
            binding.letter.visibility = View.VISIBLE
            binding.letterSpot.setCardBackgroundColor(Color.WHITE)
            binding.letterSpot.setOnClickListener {
                binding.letterSpot.setCardBackgroundColor(Color.MAGENTA)
                binding.letterSpot.isClickable = false
                onClick(adapterPosition)
            }
            binding.letter.text = l.letter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        return AlphabetViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_letter_spot, parent, false))
    }

    override fun getItemCount(): Int { return alphabet.size }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        holder.bind(alphabet[position], onClick)
    }
}