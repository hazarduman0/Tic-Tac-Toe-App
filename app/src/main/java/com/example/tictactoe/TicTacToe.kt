package com.example.tictactoe

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class TicTacToe : AppCompatActivity() {

    lateinit var btn1: ImageButton
    lateinit var btn2: ImageButton
    lateinit var btn3: ImageButton
    lateinit var btn4: ImageButton
    lateinit var btn5: ImageButton
    lateinit var btn6: ImageButton
    lateinit var btn7: ImageButton
    lateinit var btn8: ImageButton
    lateinit var btn9: ImageButton

    lateinit var txt_board_x : TextView
    lateinit var txt_board_o : TextView

    lateinit var txt_score_x : TextView
    lateinit var txt_score_o : TextView

    lateinit var txt_winner : TextView

    lateinit var btn_restart : ImageButton
    lateinit var btn_menu : ImageButton

    var score_x = 0
    var score_o = 0

    var tappedTileCount: Int = 0

    enum class Turn {
        CROSS, OVAL
    }

    var canTab : Boolean = true
    var isEnd : Boolean = false

    val gameOver = "Game Over"

    private var currentTurn = Turn.CROSS

    private var boardList = mutableListOf<ImageButton>()
    private var filledTiles = mutableListOf<String?>(null, null, null, null, null, null, null, null, null)

    private fun initView() {
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)

        txt_board_x = findViewById(R.id.txt_board_x)
        txt_board_o = findViewById(R.id.txt_board_o)

        txt_score_x = findViewById(R.id.score_x)
        txt_score_o = findViewById(R.id.score_o)

        txt_winner = findViewById(R.id.txt_winner)

        btn_restart = findViewById(R.id.btn_restart)
        btn_menu = findViewById(R.id.btn_menu)

        btn_restart.setOnClickListener(resetListener)
        btn_menu.setOnClickListener(navigateToMainMenu)

    }

    private fun initBoard() {
        boardList.add(btn1)
        boardList.add(btn2)
        boardList.add(btn3)
        boardList.add(btn4)
        boardList.add(btn5)
        boardList.add(btn6)
        boardList.add(btn7)
        boardList.add(btn8)
        boardList.add(btn9)
    }

    private fun initListeners(){

        for ((index, btn) in boardList.withIndex()){
            btn.setOnClickListener {
                tabTile(index)
            }
        }
        if(isBotTurn()) makeMove()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        initView()
        initBoard()
        initListeners()

    }

    /*
    private fun isButtonEnable(bool: Boolean) {
        for (btn in boardList){
            btn.isEnabled = bool
        }
    } */

    private fun isFilled(index: Int) : Boolean = filledTiles[index] == "X" || filledTiles[index] == "O"

    private fun makeMove() {
        //isButtonEnable(false)
        canTab = false
        var randomElement = Random.nextInt(0, 9)

        while (true){
            if (isFilled(randomElement)) randomElement = Random.nextInt(0, 9)
            else break
        }

        fillTile(randomElement)
        /*
        Executors.newSingleThreadScheduledExecutor().schedule({

        }, 2, TimeUnit.SECONDS) */
        //isButtonEnable(true)
        canTab = true
    }

    private fun isBotTurn(): Boolean
        { return MainActivity.gameMode == MainActivity.Mode.SINGLE && PickSide.choosen!!.name != currentTurn.name }

    fun fillList(index: Int){
        filledTiles.removeAt(index)
        if (currentTurn == Turn.CROSS) filledTiles.add(index, "X") else filledTiles.add(index,"O")
    }

    fun tabTile(index: Int) {

        if (boardList[index] !is ImageButton) return

        if (isEnd) clearBoard()

        if (isFilled(index)) return

        if (!canTab) {
            return
        }

        if(!isBotTurn()) fillTile(index)
        if (isBotTurn() && !isEnd) makeMove()

    }

    private fun clearBoard(){
        txt_winner.text = ""
        currentTurn = Turn.CROSS
        tappedTileCount = 0
        isEnd = false
        for ((index, btn) in boardList.withIndex()) {
            btn.setBackgroundResource(R.drawable.icon_empty_50)
            filledTiles[index] = null
        }
        if (isBotTurn()) makeMove()
    }

    private fun resetScoreBoard(){
        score_x = 0
        score_o = 0
        txt_score_x.text = score_x.toString()
        txt_score_o.text = score_o.toString()
    }

    private fun fillTile(index: Int){

        if (currentTurn == Turn.CROSS) {
            boardList[index].setBackgroundResource(R.drawable.icon_x_50)
        } else {
            boardList[index].setBackgroundResource(R.drawable.icon_o_50)
        }
        fillList(index)
        tappedTileCount++
        checkIsOver(index)
    }

    private fun changeTurn() {
        if (currentTurn == Turn.CROSS) {
            currentTurn = Turn.OVAL
            txt_board_x.setTextColor(getColor(R.color.white))
            txt_board_o.setTextColor(getColor(R.color.nacho_cheese))
        } else {
            currentTurn = Turn.CROSS
            txt_board_o.setTextColor(getColor(R.color.white))
            txt_board_x.setTextColor(getColor(R.color.nacho_cheese))
        }
    }

    private fun checkIsOver(index: Int) {
        val s = if (currentTurn == Turn.CROSS) "X" else "O"

        if (match(0, s) && match(1, s) && match(2, s)) {
            theWinnerIs()
        } else if (match(3, s) && match(4, s) && match(5, s)) {
            theWinnerIs()
        } else if (match(6, s) && match(7, s) && match(8, s)) {
            theWinnerIs()
        } else if (match(0, s) && match(3, s) && match(6, s)) {
            theWinnerIs()
        } else if (match(1, s) && match(4, s) && match(7, s)) {
            theWinnerIs()
        } else if (match(2, s) && match(5, s) && match(8, s)) {
            theWinnerIs()
        } else if (match(0, s) && match(4, s) && match(8, s)) {
            theWinnerIs()
        } else if (match(2, s) && match(4, s) && match(6, s)) {
            theWinnerIs()
        } else if (tappedTileCount == 9) {
            draw()
        } else {
            changeTurn()
        }
    }

    private fun match(index: Int, s: String): Boolean = filledTiles[index] == s

    private fun changeScoreBoard(){
        if (currentTurn == Turn.CROSS) {
            score_x++
            txt_score_x.text = score_x.toString()
        } else {
            score_o++
            txt_score_o.text = score_o.toString()
        }
    }

    private fun theWinnerIs() {
        isEnd = true
        changeScoreBoard()
        val message = "The winner is ${currentTurn.name} !!"
        txt_winner.text = message
    }

    private fun draw() {
        isEnd = true
        val message = "Draw"
        txt_winner.text = message
    }


    val resetListener = View.OnClickListener {
        val message = "Are you sure you want to restart?"
        AlertDialog.Builder(this).setTitle(gameOver).setMessage(message).setPositiveButton(
            "Reset",
            DialogInterface.OnClickListener { dialogInterface, i -> resetScoreBoard()
            clearBoard()}).setNegativeButton("No", DialogInterface.OnClickListener{dialogInterface, i -> true } )
            .setCancelable(false).show()
    }

    val navigateToMainMenu = View.OnClickListener {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}