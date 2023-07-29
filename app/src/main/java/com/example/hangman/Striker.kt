package com.example.hangman

import com.example.hangman.entities.Letter

object Striker {

    fun getStrikes(arrayList: ArrayList<Letter>, alphabetPosition: Int): Int {
        var i = 0
        arrayList.forEach {
            Alphabet.completeAlphabet[alphabetPosition].letters.forEach { alphabetLetter ->
                if (alphabetLetter.letter == it.letter) {
                    it.guessed = true
                    i++
                }
            }
        }
        return i
    }

}