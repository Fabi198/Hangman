package com.example.hangman.onePlayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.hangman.Alphabet.alphabetTyped
import com.example.hangman.Alphabet.completeAlphabet
import com.example.hangman.LayoutManagerConverter.setCustomLayoutManager
import com.example.hangman.MainActivity
import com.example.hangman.Phrases.randomPhrases
import com.example.hangman.R
import com.example.hangman.adapters.AlphabetAdapter
import com.example.hangman.adapters.WordAdapter
import com.example.hangman.databinding.FragmentOnePlayerGameBinding
import com.example.hangman.entities.Letter


class OnePlayerGame : Fragment(R.layout.fragment_one_player_game) {

    private lateinit var binding: FragmentOnePlayerGameBinding
    private var conPlayer1 = 1
    private var lostAllLifes = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnePlayerGameBinding.bind(view)
        val array = arrayListOf(
            binding.ivStep1,
            binding.ivStep2,
            binding.ivStep3,
            binding.ivStep4,
            binding.ivStep5,
            binding.ivStep6,
            binding.ivStep7
        )

        setGamePlayer1(array)


    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGamePlayer1(array: ArrayList<ImageView>) {
        val phrase = randomPhrases[(Math.random() * randomPhrases.size).toInt()]
        val arrayList = ArrayList<Letter>()
        for (char in phrase.uppercase()) {
            if (char == ' ') {
                arrayList.add(Letter(char.toString(), true))
            } else {
                arrayList.add(Letter(char.toString()))
            }
        }
        val adapterAnswer = ArrayList<Letter>(arrayList)
        val adapter = WordAdapter(adapterAnswer)
        binding.rvWord.adapter = adapter
        binding.rvWord.layoutManager = setCustomLayoutManager(requireContext(), arrayList.size.toDouble())

        val alphabet = ArrayList<Letter>()
        alphabetTyped.forEach { alphabet.add(Letter(it)) }
        val alphabetAdapter = AlphabetAdapter(alphabet) { alphabetPosition ->
            var strike = 0
            arrayList.forEach {
                completeAlphabet[alphabetPosition].letters.forEach { alphabetLetter ->
                    if (alphabetLetter.letter == it.letter) {
                        it.guessed = true
                        strike++
                    }
                }
            }
            if (strike == 0) {
                lostLife(array[conPlayer1], arrayList, adapter)
            } else if (strike >= 3) {
                earnLife(array[conPlayer1-1])
            }
            var alreadyGuessed = true
            arrayList.forEach {
                if (!it.guessed) {
                    alreadyGuessed = false
                }
            }
            if (alreadyGuessed && !lostAllLifes) {
                showAlertDialog(
                    getString(R.string.ganaste_jugador1),
                    getString(R.string.felicitaciones_dialog)
                )
            }
            adapter.updateData(arrayList)
            adapter.notifyDataSetChanged()
        }
        binding.rvAlphabet.adapter = alphabetAdapter
    }

    private fun earnLife(iv: ImageView) {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.drop_to_down)
        when (conPlayer1) {
            in 2..6 -> {
                iv.startAnimation(anim)
                iv.visibility = View.INVISIBLE
                conPlayer1--
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun lostLife(iv: ImageView, arrayList: ArrayList<Letter>, adapter: WordAdapter) {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        when (conPlayer1) {
            in 1..5 -> {
                iv.startAnimation(anim)
                iv.visibility = View.VISIBLE
                conPlayer1++
            }
            6 -> {
                iv.startAnimation(anim)
                iv.visibility = View.VISIBLE
                lostAllLifes = true
                arrayList.forEach {
                    it.guessed = true
                }
                adapter.updateData(arrayList)
                adapter.notifyDataSetChanged()
                showAlertDialog(
                    getString(R.string.perdiste_title),
                    getString(R.string.sin_vidas_dialog)
                )
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(getString(R.string.reiniciar)) { _, _ ->
            startActivity(
                Intent(requireContext(), MainActivity::class.java)
            )
        }
        alertDialogBuilder.setOnDismissListener {
            startActivity(
                Intent(
                    requireContext(),
                    MainActivity::class.java
                )
            )
        }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}