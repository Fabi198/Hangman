package com.example.hangman

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object LayoutManagerConverter {

    fun setCustomLayoutManager(context: Context, size: Double): RecyclerView.LayoutManager {
        lateinit var layout: RecyclerView.LayoutManager
        if (size.toInt() in 1..10) {
            layout = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        } else if (size.toInt() in 11..20) {
            val sC: Double = (size / 2)
            layout = if (size.toInt() % 2 == 0) {
                GridLayoutManager(context, sC.toInt())
            } else {
                GridLayoutManager(context, ((sC + 0.5).toInt()))
            }
        } else if (size.toInt() in 21..30) {
            val sC = size / 3
            layout = if (size.toInt() % 3 == 0) {
                GridLayoutManager(context, sC.toInt())
            } else {
                GridLayoutManager(context, ((sC + 0.5).toInt()))
            }
        }
        return layout
    }
}