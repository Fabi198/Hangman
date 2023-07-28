package com.example.hangman.twoPlawers

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hangman.Phrases.randomPhrases
import com.example.hangman.R
import com.example.hangman.databinding.FragmentTwoPlayersSetBinding


class TwoPlayersSet : Fragment(R.layout.fragment_two_players_set) {

    private lateinit var binding: FragmentTwoPlayersSetBinding
    private var conSetPhrase = 0
    private lateinit var phrasePlayer1: String
    private lateinit var phrasePlayer2: String
    private lateinit var namePlayer1: String
    private lateinit var namePlayer2: String
    private var phrasePlayer1Error = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTwoPlayersSetBinding.bind(view)

        setCheckBox()
        setPlayer1Phrase()
    }

    private fun setCheckBox() {
        binding.cbOneTurnPerPlayer.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbKeepWhileWinningPlayer.isChecked = false
            }
        }
        binding.cbKeepWhileWinningPlayer.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.cbOneTurnPerPlayer.isChecked = false
            }
        }
    }

    private fun setPlayer1Phrase() {
        binding.btnRollDice.setOnClickListener { binding.tvEditPhrasePlayer1.setText(randomPhrases[(Math.random() * randomPhrases.size).toInt()]) }
        binding.tvEditPhrasePlayer1.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (keyEvent != null && keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) {
                if (binding.tvEditPhrasePlayer1.text?.isNotEmpty() == true) {
                    binding.tvInputPhrasePlayer1.focusable = View.NOT_FOCUSABLE
                    binding.tvInputPlayerName.focusable = View.FOCUSABLE
                } else {
                    showPhraseError(getString(R.string.la_frase_no_puede_estar_vacia))
                }
                return@OnEditorActionListener true
            }
            false
        })

        binding.tvEditPhrasePlayer1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val text = it.toString()
                    if (text.isNotEmpty()) {
                        val lastChar = text[text.length - 1]
                        if (!(Character.isLowerCase(lastChar) || Character.isUpperCase(lastChar) || Character.isWhitespace(
                                lastChar
                            ))
                        ) {
                            showPhraseError(getString(R.string.caracter_invalido_error))
                        } else {
                            hidePhraseError()
                        }
                    } else {
                        hidePhraseError()
                    }
                }
            }
        })

        binding.btnSetPhrase.setOnClickListener {
            when (conSetPhrase) {
                0 -> if (!phrasePlayer1Error) {
                    if (binding.tvEditPhrasePlayer1.text.toString().isNotEmpty()) {
                        phrasePlayer1 = binding.tvEditPhrasePlayer1.text.toString()
                        namePlayer1 = binding.tvEditPlayerName.text.toString().ifEmpty { getString(R.string.jugador_1) }
                        binding.tvEditPlayerName.setText("")
                        binding.tvInputPlayerName.setHint(R.string.jugador_2_escribe_tu_nombre)
                        binding.tvEditPhrasePlayer1.setText("")
                        binding.tvInputPhrasePlayer1.setHint(R.string.jugador_2_escriba_la_frase_a_adivinar_por_jugador_1)
                        binding.tvInputPlayerName.focusable = View.NOT_FOCUSABLE
                        binding.tvInputPhrasePlayer1.requestFocus()
                        conSetPhrase++
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.la_frase_no_puede_estar_vacia), Toast.LENGTH_SHORT).show()
                    }
                }
                1 -> if (!phrasePlayer1Error) {
                    if (binding.cbOneTurnPerPlayer.isChecked || binding.cbKeepWhileWinningPlayer.isChecked) {
                        if (binding.tvEditPhrasePlayer1.text.toString().isNotEmpty()) {
                            hideKeyboard()
                            phrasePlayer2 = binding.tvEditPhrasePlayer1.text.toString()
                            namePlayer2 = binding.tvEditPlayerName.text.toString().ifEmpty { getString(R.string.jugador_2) }
                            binding.tvEditPlayerName.setText("")
                            binding.tvEditPhrasePlayer1.setText("")
                            conSetPhrase++
                            setGameReady(phrasePlayer1, phrasePlayer2, namePlayer1, namePlayer2, getGameMethod())
                        } else {
                            Toast.makeText(requireContext(), getString(R.string.la_frase_no_puede_estar_vacia), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), getString(R.string.debes_elegir_un_metodo_de_juego), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getGameMethod(): Boolean { return binding.cbOneTurnPerPlayer.isChecked }

    private fun setGameReady(
        phrasePlayer1: String,
        phrasePlayer2: String,
        namePlayer1: String,
        namePlayer2: String,
        gameMethod: Boolean
    ) {
        allGone()
        val idContainer = arguments?.getInt("idContainer")
        if (idContainer != null) {
            val bundle = Bundle()
            bundle.putString("phrasePlayer1", phrasePlayer1)
            bundle.putString("phrasePlayer2", phrasePlayer2)
            bundle.putString("namePlayer1", namePlayer1)
            bundle.putString("namePlayer2", namePlayer2)
            bundle.putBoolean("gameMethod", gameMethod)
            val fragment = TwoPlayersGame()
            fragment.arguments = bundle
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(idContainer, fragment, "TwoPlayersGame")
                .addToBackStack("TwoPlayersGame")
                .commit()
        }

    }

    private fun allGone() {
        binding.tvInputPhrasePlayer1.visibility = View.GONE
        binding.tvInputPlayerName.visibility = View.GONE
        binding.btnRollDice.visibility = View.GONE
        binding.btnSetPhrase.visibility = View.GONE
        binding.clOneTurnPerPlayer.visibility = View.GONE
        binding.clKeepWhileWinningPlayer.visibility = View.GONE
    }

    private fun showPhraseError(errorMsg: String) {
        binding.tvInputPhrasePlayer1.error = errorMsg
        phrasePlayer1Error = true
    }

    private fun hidePhraseError() {
        binding.tvInputPhrasePlayer1.error = null
        phrasePlayer1Error = false
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.dlTwoPlayersSet.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        conSetPhrase = 0
    }

}