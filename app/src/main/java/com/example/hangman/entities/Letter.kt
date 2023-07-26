package com.example.hangman.entities

data class Letter (
    val letter: String = "",
    var guessed: Boolean = false
)
