package com.example.hangman

import com.example.hangman.entities.AlphabetLetter
import com.example.hangman.entities.Letter

object Alphabet {

    val alphabetTyped = arrayListOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
        "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S",
        "T", "U", "V", "W", "X", "Y", "Z"
    )
    val completeAlphabet = arrayListOf(
        AlphabetLetter(
            arrayListOf(
                Letter("A"),
                Letter("Ä"),
                Letter("Á"),
                Letter("Å"),
                Letter("Â"),
                Letter("À"),
                Letter("Ã")
            )
        ),
        AlphabetLetter(arrayListOf(Letter("B"), Letter(("ß")))),
        AlphabetLetter(arrayListOf(Letter("C"), Letter("Ç"))),
        AlphabetLetter(arrayListOf(Letter("D"), Letter("Ð"))),
        AlphabetLetter(
            arrayListOf(
                Letter("E"),
                Letter("Ë"),
                Letter("É"),
                Letter(("Ê")),
                Letter("È")
            )
        ),
        AlphabetLetter(arrayListOf(Letter("F"))),
        AlphabetLetter(arrayListOf(Letter("G"))),
        AlphabetLetter(arrayListOf(Letter("H"))),
        AlphabetLetter(arrayListOf(Letter("I"), Letter("Ï"), Letter("Í"), Letter("Î"))),
        AlphabetLetter(arrayListOf(Letter("J"))),
        AlphabetLetter(arrayListOf(Letter("K"))),
        AlphabetLetter(arrayListOf(Letter("L"))),
        AlphabetLetter(arrayListOf(Letter("M"))),
        AlphabetLetter(arrayListOf(Letter("N"))),
        AlphabetLetter(arrayListOf(Letter("Ñ"))),
        AlphabetLetter(
            arrayListOf(
                Letter("O"),
                Letter("Ö"),
                Letter("Ó"),
                Letter("Ô"),
                Letter("Ò"),
                Letter("Õ")
            )
        ),
        AlphabetLetter(arrayListOf(Letter("P"))),
        AlphabetLetter(arrayListOf(Letter("Q"))),
        AlphabetLetter(arrayListOf(Letter("R"))),
        AlphabetLetter(arrayListOf(Letter("S"))),
        AlphabetLetter(arrayListOf(Letter("T"))),
        AlphabetLetter(
            arrayListOf(
                Letter("U"),
                Letter("Ü"),
                Letter("Ú"),
                Letter("Û"),
                Letter("Ù")
            )
        ),
        AlphabetLetter(arrayListOf(Letter("V"))),
        AlphabetLetter(arrayListOf(Letter("W"))),
        AlphabetLetter(arrayListOf(Letter("X"))),
        AlphabetLetter(arrayListOf(Letter("Y"), Letter("Ý"))),
        AlphabetLetter(arrayListOf(Letter("Z"))),
    )

}