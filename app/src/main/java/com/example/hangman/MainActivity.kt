package com.example.hangman

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.hangman.databinding.ActivityMainBinding
import com.example.hangman.onePlayer.OnePlayerGame
import com.example.hangman.twoPlawers.TwoPlayersSet

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val doubleClickTimeDelta = 300 // Intervalo de tiempo en milisegundos para el doble clic
    private var lastClickTime: Long = 0 // Variable para almacenar el tiempo del último clic en el botón "back"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivOnePlayer.setOnClickListener {
            val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.on_click_button)
            anim.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        val bundle = Bundle()
                        bundle.putInt("idContainer", binding.fragmentContainer.id)
                        val fragment = OnePlayerGame()
                        fragment.arguments = bundle
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.fragmentContainer.id, fragment, "OnePlayerGame")
                            .addToBackStack("OnePlayerGame")
                            .commit()
                        allGone()
                        binding.fragmentContainer.visibility = View.VISIBLE
                    }, 30)
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            binding.ivOnePlayer.startAnimation(anim)
        }

        binding.ivTwoPlayer.setOnClickListener {
            val anim: Animation = AnimationUtils.loadAnimation(this, R.anim.on_click_button)
            anim.setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        val bundle = Bundle()
                        bundle.putInt("idContainer", binding.fragmentContainer.id)
                        val fragment = TwoPlayersSet()
                        fragment.arguments = bundle
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.fragmentContainer.id, fragment, "TwoPlayersSet")
                            .addToBackStack("TwoPlayersSet")
                            .commit()
                        allGone()
                        binding.fragmentContainer.visibility = View.VISIBLE
                    }, 30)
                }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
            binding.ivTwoPlayer.startAnimation(anim)
        }

    }

    private fun allGone() {
        binding.ivTitle.visibility = View.GONE
        binding.ivOnePlayer.visibility = View.GONE
        binding.ivTwoPlayer.visibility = View.GONE
        binding.ivCompleteBody.visibility = View.GONE
    }

    private fun allVisible() {
        binding.ivTitle.visibility = View.VISIBLE
        binding.ivOnePlayer.visibility = View.VISIBLE
        binding.ivTwoPlayer.visibility = View.VISIBLE
        binding.ivCompleteBody.visibility = View.VISIBLE
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < doubleClickTimeDelta) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                supportFragmentManager.popBackStack()
                allVisible()
            } else {
                lastClickTime = currentTime
                val prefs = getSharedPreferences("MY PREF", MODE_PRIVATE)
                val isToastShown = prefs.getBoolean("isToastShown", false)
                if (!isToastShown) {
                    Toast.makeText(this, "Presiona 2 veces para volver al inicio", Toast.LENGTH_SHORT).show()
                    val editor = prefs.edit()
                    editor.putBoolean("isToastShown", true)
                    editor.apply()
                }
                supportFragmentManager.popBackStack()
            }
        } else if (supportFragmentManager.backStackEntryCount == 1) {
            supportFragmentManager.popBackStack()
            allVisible()
        } else {
            finish()
        }
    }


}