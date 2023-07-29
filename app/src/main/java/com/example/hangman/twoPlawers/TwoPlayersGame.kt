package com.example.hangman.twoPlawers

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
import com.example.hangman.LayoutManagerConverter.setCustomLayoutManager
import com.example.hangman.MainActivity
import com.example.hangman.R
import com.example.hangman.Striker.getStrikes
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
        val arrayPlayer1 = arrayListOf(
            binding.ivStep1Player1,
            binding.ivStep2Player1,
            binding.ivStep3Player1,
            binding.ivStep4Player1,
            binding.ivStep5Player1,
            binding.ivStep6Player1,
            binding.ivStep7Player1
        )
        val arrayPlayer2 = arrayListOf(
            binding.ivStep1Player2,
            binding.ivStep2Player2,
            binding.ivStep3Player2,
            binding.ivStep4Player2,
            binding.ivStep5Player2,
            binding.ivStep6Player2,
            binding.ivStep7Player2
        )

        setGamePlayer1(arguments?.getBoolean("gameMethod"), arguments?.getString("phrasePlayer2"), arguments?.getString("namePlayer1"), arrayPlayer1)
        setGamePlayer2(arguments?.getBoolean("gameMethod"), arguments?.getString("phrasePlayer1"), arrayPlayer2)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setGamePlayer1(gameMethod: Boolean?, phrase: String?, name: String?, arrayPlayer1: ArrayList<ImageView>) {
        if (gameMethod != null && phrase != null && name != null) {
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
            binding.rvWordPlayer1.adapter = adapter
            binding.rvWordPlayer1.layoutManager = setCustomLayoutManager(requireContext(), arrayList.size.toDouble())
            val alphabet = ArrayList<Letter>()
            alphabetTyped.forEach { alphabet.add(Letter(it)) }
            val alphabetAdapter = AlphabetAdapter(alphabet) { alphabetPosition ->
                val strike = getStrikes(arrayList, alphabetPosition)
                if (strike == 0) {
                    lostLife(arrayPlayer1[conPlayer1], arrayList, adapter)
                } else if (strike > 0) {
                    if (strike >= 3) { earnLife(arrayPlayer1[conPlayer1-1]) }
                    if (gameMethod) { switchPlayer(true) }
                }
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
    private fun setGamePlayer2(gameMethod: Boolean?, phrase: String?, arrayPlayer2: ArrayList<ImageView>) {
        if (gameMethod != null && phrase != null) {
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
            binding.rvWordPlayer2.adapter = adapter
            binding.rvWordPlayer2.layoutManager = setCustomLayoutManager(requireContext(), arrayList.size.toDouble())
            val alphabet = ArrayList<Letter>()
            alphabetTyped.forEach { alphabet.add(Letter(it)) }
            val alphabetAdapter = AlphabetAdapter(alphabet) { alphabetPosition ->
                val strike = getStrikes(arrayList, alphabetPosition)
                if (strike == 0) {
                    lostLife(arrayPlayer2[conPlayer2], arrayList, adapter)
                } else if (strike > 0) {
                    if (strike >= 3) { earnLife(arrayPlayer2[conPlayer2-1]) }
                    if (gameMethod) { switchPlayer(true) }
                }
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
    private fun lostLife(iv: ImageView, arrayList: ArrayList<Letter>, adapter: WordAdapter) {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        when (currentPlayer) {
            1 -> {
                when (conPlayer1) {
                    in 1..5 -> {
                        iv.startAnimation(anim)
                        iv.visibility = View.VISIBLE
                        conPlayer1++
                        switchPlayer(false)
                    }
                    6 -> {
                        iv.startAnimation(anim)
                        iv.visibility = View.VISIBLE
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
            2 -> {
                when (conPlayer2) {
                    in 1..5 -> {
                        iv.startAnimation(anim)
                        iv.visibility = View.VISIBLE
                        conPlayer2++
                        switchPlayer(false)
                    }
                    6 -> {
                        iv.startAnimation(anim)
                        iv.visibility = View.VISIBLE
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
        }
    }

    private fun earnLife(iv: ImageView) {
        val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.drop_to_down)
        when (currentPlayer) {
            1 -> {
                when (conPlayer1) {
                    in 2..6 -> {
                        iv.startAnimation(anim)
                        iv.visibility = View.VISIBLE
                        conPlayer1--
                    }
                }
            }
            2 -> {
                when (conPlayer2) {
                    in 2..6 -> {
                        iv.startAnimation(anim)
                        iv.visibility = View.VISIBLE
                        conPlayer2--
                    }
                }
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

    private fun switchPlayer(answer: Boolean) {
        val namePlayer2 = arguments?.getString("namePlayer2")
        val namePlayer1 = arguments?.getString("namePlayer1")
        if (answer) {
            binding.ivChangePlayer.setImageResource(R.drawable.hangman_change_player_sign_correct_answer)
        } else {
            binding.ivChangePlayer.setImageResource(R.drawable.hangman_change_player_sign_incorrect_answer)
        }
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