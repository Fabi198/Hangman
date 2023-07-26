package com.example.hangman.twoPlawers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hangman.Alphabet.alphabetTyped
import com.example.hangman.Alphabet.completeAlphabet
import com.example.hangman.MainActivity
import com.example.hangman.R
import com.example.hangman.adapters.AlphabetAdapter
import com.example.hangman.adapters.WordAdapter
import com.example.hangman.databinding.FragmentTwoPlayersGameBinding
import com.example.hangman.entities.Letter


class TwoPlayersGame : Fragment(R.layout.fragment_two_players_game) {

    private lateinit var binding: FragmentTwoPlayersGameBinding
    private var conPlayer1 = 1
    private var conPlayer2 = 1
    private var lostAllLifesPlayer1 = false
    private var lostAllLifesPlayer2 = false
    private var currentPlayer = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTwoPlayersGameBinding.bind(view)

        setGamePlayer1(arguments?.getString("phrasePlayer2"), arguments?.getString("namePlayer1"))
        setGamePlayer2(arguments?.getString("phrasePlayer1"))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGamePlayer1(phrase: String?, name: String?) {
        if (phrase != null && name != null) {
            binding.tvPlayerOnGame.text = name
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
            with(binding.rvWordPlayer1) {
                setAdapter(adapter)
                val size: Double = arrayList.size.toDouble()
                if (size.toInt() in 1..10) {
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                } else if (size.toInt() in 11..20) {
                    val sC: Double = (size / 2)
                    layoutManager = if (size.toInt() % 2 == 0) {
                        GridLayoutManager(requireContext(), sC.toInt())
                    } else {
                        GridLayoutManager(requireContext(), ((sC + 0.5).toInt()))
                    }
                } else if (size.toInt() in 21..30) {
                    val sC = size / 3
                    layoutManager = if (size.toInt() % 3 == 0) {
                        GridLayoutManager(requireContext(), sC.toInt())
                    } else {
                        GridLayoutManager(requireContext(), ((sC + 0.5).toInt()))
                    }
                }
            }
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
                if (strike == 0) { lostLifePlayer1(arrayList, adapter) } else if (strike >= 3) { earnLifePlayer1() }
                var alreadyGuessed = true
                arrayList.forEach { if (!it.guessed) { alreadyGuessed = false } }
                if (alreadyGuessed && !lostAllLifesPlayer1) { showAlertDialog(getString(R.string.ganaste_jugador1), getString(R.string.felicitaciones_dialog)) }
                adapter.updateData(arrayList)
                adapter.notifyDataSetChanged()
            }
            binding.rvAlphabetPlayer1.adapter = alphabetAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGamePlayer2(phrase: String?) {
        if (phrase != null) {
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
            with(binding.rvWordPlayer2) {
                setAdapter(adapter)
                val size: Double = arrayList.size.toDouble()
                if (size.toInt() in 1..10) {
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                } else if (size.toInt() in 11..20) {
                    val sC: Double = (size / 2)
                    layoutManager = if (size.toInt() % 2 == 0) {
                        GridLayoutManager(requireContext(), sC.toInt())
                    } else {
                        GridLayoutManager(requireContext(), ((sC + 0.5).toInt()))
                    }
                } else if (size.toInt() in 21..30) {
                    val sC = size / 3
                    layoutManager = if (size.toInt() % 3 == 0) {
                        GridLayoutManager(requireContext(), sC.toInt())
                    } else {
                        GridLayoutManager(requireContext(), ((sC + 0.5).toInt()))
                    }
                }
            }
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
                if (strike == 0) { lostLifePlayer2(arrayList, adapter) } else if (strike >= 3) { earnLifePlayer2() }
                var alreadyGuessed = true
                arrayList.forEach { if (!it.guessed) { alreadyGuessed = false } }
                if (alreadyGuessed && !lostAllLifesPlayer2) { showAlertDialog(getString(R.string.ganaste_jugador2), getString(R.string.felicitaciones_dialog)) }
                adapter.updateData(arrayList)
                adapter.notifyDataSetChanged()
            }
            binding.rvAlphabetPlayer2.adapter = alphabetAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun lostLifePlayer1(arrayList: ArrayList<Letter>, adapter: WordAdapter) {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        when (conPlayer1) {
            1 -> {
                binding.ivStep2Player1.startAnimation(anim)
                binding.ivStep2Player1.visibility = View.VISIBLE
                conPlayer1++
                switchPlayer()
            }
            2 -> {
                binding.ivStep3Player1.startAnimation(anim)
                binding.ivStep3Player1.visibility = View.VISIBLE
                conPlayer1++
                switchPlayer()
            }
            3 -> {
                binding.ivStep4Player1.startAnimation(anim)
                binding.ivStep4Player1.visibility = View.VISIBLE
                conPlayer1++
                switchPlayer()
            }
            4 -> {
                binding.ivStep5Player1.startAnimation(anim)
                binding.ivStep5Player1.visibility = View.VISIBLE
                conPlayer1++
                switchPlayer()
            }
            5 -> {
                binding.ivStep6Player1.startAnimation(anim)
                binding.ivStep6Player1.visibility = View.VISIBLE
                conPlayer1++
                switchPlayer()
            }
            6 -> {
                binding.ivStep7Player1.startAnimation(anim)
                binding.ivStep7Player1.visibility = View.VISIBLE
                lostAllLifesPlayer1 = true
                arrayList.forEach {
                    it.guessed = true
                }
                adapter.updateData(arrayList)
                adapter.notifyDataSetChanged()
                showAlertDialog(getString(R.string.perdiste_jugador1), getString(R.string.sin_vidas_dialog))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun lostLifePlayer2(arrayList: ArrayList<Letter>, adapter: WordAdapter) {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        when (conPlayer2) {
            1 -> {
                binding.ivStep2Player2.startAnimation(anim)
                binding.ivStep2Player2.visibility = View.VISIBLE
                conPlayer2++
                switchPlayer()
            }
            2 -> {
                binding.ivStep3Player2.startAnimation(anim)
                binding.ivStep3Player2.visibility = View.VISIBLE
                conPlayer2++
                switchPlayer()
            }
            3 -> {
                binding.ivStep4Player2.startAnimation(anim)
                binding.ivStep4Player2.visibility = View.VISIBLE
                conPlayer2++
                switchPlayer()
            }
            4 -> {
                binding.ivStep5Player2.startAnimation(anim)
                binding.ivStep5Player2.visibility = View.VISIBLE
                conPlayer2++
                switchPlayer()
            }
            5 -> {
                binding.ivStep6Player2.startAnimation(anim)
                binding.ivStep6Player2.visibility = View.VISIBLE
                conPlayer2++
                switchPlayer()
            }
            6 -> {
                binding.ivStep7Player2.startAnimation(anim)
                binding.ivStep7Player2.visibility = View.VISIBLE
                lostAllLifesPlayer2 = true
                arrayList.forEach {
                    it.guessed = true
                }
                adapter.updateData(arrayList)
                adapter.notifyDataSetChanged()
                showAlertDialog(getString(R.string.perdiste_jugador2), getString(R.string.sin_vidas_dialog))
            }
        }
    }

    private fun earnLifePlayer1() {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.drop_to_down)
        when (conPlayer1) {
            2 -> {
                binding.ivStep2Player1.startAnimation(anim)
                binding.ivStep2Player1.visibility = View.INVISIBLE
                conPlayer1--
            }
            3 -> {
                binding.ivStep3Player1.startAnimation(anim)
                binding.ivStep3Player1.visibility = View.INVISIBLE
                conPlayer1--
            }
            4 -> {
                binding.ivStep4Player1.startAnimation(anim)
                binding.ivStep4Player1.visibility = View.INVISIBLE
                conPlayer1--
            }
            5 -> {
                binding.ivStep5Player1.startAnimation(anim)
                binding.ivStep5Player1.visibility = View.INVISIBLE
                conPlayer1--
            }
            6 -> {
                binding.ivStep6Player1.startAnimation(anim)
                binding.ivStep6Player1.visibility = View.INVISIBLE
                conPlayer1--
            }
        }
    }

    private fun earnLifePlayer2() {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.drop_to_down)
        when (conPlayer2) {
            2 -> {
                binding.ivStep2Player2.startAnimation(anim)
                binding.ivStep2Player2.visibility = View.INVISIBLE
                conPlayer2--
            }
            3 -> {
                binding.ivStep3Player2.startAnimation(anim)
                binding.ivStep3Player2.visibility = View.INVISIBLE
                conPlayer2--
            }
            4 -> {
                binding.ivStep4Player2.startAnimation(anim)
                binding.ivStep4Player2.visibility = View.INVISIBLE
                conPlayer2--
            }
            5 -> {
                binding.ivStep5Player2.startAnimation(anim)
                binding.ivStep5Player2.visibility = View.INVISIBLE
                conPlayer2--
            }
            6 -> {
                binding.ivStep6Player2.startAnimation(anim)
                binding.ivStep6Player2.visibility = View.INVISIBLE
                conPlayer2--
            }
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton(getString(R.string.reiniciar)) { _, _ -> startActivity(Intent(requireContext(), MainActivity::class.java)) }
        alertDialogBuilder.setOnDismissListener { startActivity(Intent(requireContext(), MainActivity::class.java)) }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun switchPlayer() {
        val namePlayer2 = arguments?.getString("namePlayer2")
        val namePlayer1 = arguments?.getString("namePlayer1")
        if (currentPlayer == 1) {
            binding.gamePlayer1.isClickable = false
            binding.ivChangePlayer.visibility = View.VISIBLE
            binding.ivChangePlayer.setOnClickListener {
                binding.gamePlayer1.visibility = View.GONE
                binding.gamePlayer2.visibility = View.VISIBLE
                currentPlayer = 2
                binding.tvPlayerOnGame.text = namePlayer2
                binding.ivChangePlayer.visibility = View.GONE
            }

        } else {
            binding.gamePlayer2.isClickable = false
            binding.ivChangePlayer.visibility = View.VISIBLE
            binding.ivChangePlayer.setOnClickListener {
                binding.gamePlayer2.visibility = View.GONE
                binding.gamePlayer1.visibility = View.VISIBLE
                currentPlayer = 1
                binding.tvPlayerOnGame.text = namePlayer1
                binding.ivChangePlayer.visibility = View.GONE
            }

        }
    }

}