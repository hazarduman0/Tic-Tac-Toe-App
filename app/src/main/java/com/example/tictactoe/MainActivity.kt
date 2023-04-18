package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.tictactoe.R.*

class MainActivity : AppCompatActivity() {


    lateinit var btnBot : Button
    lateinit var btnFriend : Button

    enum class Mode{
        SINGLE, FRIEND
    }

    companion object{
        var gameMode: Mode? = null
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        btnBot = findViewById(id.btnBot)
        btnFriend = findViewById(id.btnFriend)

        btnBot.setOnClickListener(navigateToPickSide)

        btnFriend.setOnClickListener(navigateToGame)

    }

    val navigateToPickSide = View.OnClickListener {
        val intent = Intent(this, PickSide::class.java)
        gameMode = Mode.SINGLE
        startActivity(intent)
        finish()
    }

    val navigateToGame = View.OnClickListener {
        val intent = Intent(this, TicTacToe::class.java)
        gameMode = Mode.FRIEND
        startActivity(intent)
        finish()
    }
}