package com.example.hangman.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hangman.R
import com.example.hangman.databinding.ItemLetterSpotBinding
import com.example.hangman.entities.Letter

class WordAdapter(private var word: ArrayList<Letter>): RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    fun updateData(newWord: ArrayList<Letter>) {
        word = newWord
    }

    class WordViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemLetterSpotBinding.bind(view)

        fun bind(l: Letter) {
            if (l.letter == " ") {
                binding.letter.visibility = View.INVISIBLE
                binding.letterSpot.visibility = View.INVISIBLE
            } else {
                binding.letter.visibility = if (l.guessed) View.VISIBLE else View.INVISIBLE
                binding.letterSpot.visibility = View.VISIBLE
                binding.letter.text = l.letter
            }

            binding.letterSpot.setCardBackgroundColor(Color.LTGRAY)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_letter_spot, parent, false))
    }

    override fun getItemCount(): Int { return word.size }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(word[position])
    }
}