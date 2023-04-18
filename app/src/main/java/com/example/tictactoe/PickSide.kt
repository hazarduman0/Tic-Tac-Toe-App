package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class PickSide : AppCompatActivity() {

    lateinit var imgBtnX : ImageButton
    lateinit var imgBtnO : ImageButton
    lateinit var btnContinue: Button

    enum class Choosen{
        OVAL, CROSS
    }

    companion object{
        var choosen: Choosen? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_side)

        imgBtnX = findViewById(R.id.imgBtnX)
        imgBtnO = findViewById(R.id.imgBtnO)
        btnContinue = findViewById(R.id.btnContinue)

        imgBtnX.setOnClickListener(imgBtnXListener)
        imgBtnO.setOnClickListener(imgBtnOListener)
        btnContinue.setOnClickListener(btnContinueListener)
    }

    val imgBtnXListener = View.OnClickListener {
        choosen = Choosen.CROSS
        imgBtnX.setBackgroundResource(R.drawable.selected_btn_x)
        imgBtnO.setBackgroundResource(R.drawable.unselected_button)
    }

    val imgBtnOListener = View.OnClickListener {
        choosen = Choosen.OVAL
        imgBtnO.setBackgroundResource(R.drawable.selected_btn_o)
        imgBtnX.setBackgroundResource(R.drawable.unselected_button)
    }

    val btnContinueListener = View.OnClickListener {
        if (choosen == null){
            Toast.makeText(this, "Please pick a side", Toast.LENGTH_SHORT).show()
            return@OnClickListener
        }
        val intent = Intent(this, TicTacToe::class.java)
        startActivity(intent)
        finish()
    }
}